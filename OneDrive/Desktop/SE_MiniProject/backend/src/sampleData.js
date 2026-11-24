import { Question, Subject } from './models.js';
import { sampleQuestions, sampleSubjects } from './sampleQuestions.js';

export async function ensureSampleQuestionBank() {
  const subjects = new Map();

  for (const sampleSubject of sampleSubjects) {
    const subject = await Subject.findOneAndUpdate(
      { name: sampleSubject.name },
      { $setOnInsert: sampleSubject },
      { upsert: true, returnDocument: 'after', setDefaultsOnInsert: true }
    );
    subjects.set(sampleSubject.name, subject._id);
  }

  const operations = sampleQuestions.map(question => ({
    updateOne: {
      filter: { subject: subjects.get(question.subject), statement: question.statement },
      update: {
        $setOnInsert: {
          subject: subjects.get(question.subject),
          topic: question.topic,
          statement: question.statement,
          options: question.options.map((text, index) => ({ text, isCorrect: index === question.answer })),
          difficulty: question.difficulty,
          explanation: question.explanation,
          marks: 1,
          negativeMarks: 0
        }
      },
      upsert: true
    }
  }));

  await Question.bulkWrite(operations, { ordered: true });
}
