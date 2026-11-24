import 'dotenv/config';
import mongoose from 'mongoose';
import bcrypt from 'bcryptjs';
import { User, Subject, Question, Competition } from './models.js';
import { sampleQuestions, sampleSubjects } from './sampleQuestions.js';

await mongoose.connect(process.env.MONGODB_URI || 'mongodb://127.0.0.1:27017/quizarena');

await Promise.all([User.deleteMany({}), Subject.deleteMany({}), Question.deleteMany({}), Competition.deleteMany({})]);

const admin = await User.create({
  name: process.env.ADMIN_NAME || 'QuizArena Admin',
  email: (process.env.ADMIN_EMAIL || 'admin@quizarena.com').toLowerCase(),
  password: await bcrypt.hash(process.env.ADMIN_PASSWORD || 'Admin@123', 12),
  role: 'admin'
});

const student = await User.create({
  name: 'Demo Student', email: 'student@quizarena.com',
  password: await bcrypt.hash('Student@123', 12),
  role: 'student', points: 240, xp: 340
});

const subjects = await Subject.insertMany(sampleSubjects);
const subjectsByName = new Map(subjects.map(subject => [subject.name, subject._id]));
const questions = await Question.insertMany(sampleQuestions.map(question => ({
  subject: subjectsByName.get(question.subject),
  topic: question.topic,
  statement: question.statement,
  options: question.options.map((text, index) => ({ text, isCorrect: index === question.answer })),
  difficulty: question.difficulty,
  explanation: question.explanation,
  marks: 1,
  negativeMarks: 0
})));

const java = subjectsByName.get('Java Programming');
const python = subjectsByName.get('Python Programming');
const questionsFor = subjectId => questions.filter(question => String(question.subject) === String(subjectId));

await Competition.insertMany([
  {
    title: 'Java Sprint Challenge',
    subject: java, description: 'Test your Java fundamentals in a fast-paced timed challenge.',
    code: 'JAVA25', questions: questionsFor(java).slice(0, 6), startAt: new Date(Date.now() - 3600000),
    endAt: new Date(Date.now() + 7 * 86400000), durationMinutes: 20, difficulty: 'Medium',
    participants: [student._id]
  },
  {
    title: 'Python Fundamentals Cup',
    subject: python, description: 'A beginner-friendly competition covering Python essentials.',
    code: 'PYTHON1', questions: questionsFor(python).slice(0, 6), startAt: new Date(Date.now() + 86400000),
    endAt: new Date(Date.now() + 3 * 86400000), durationMinutes: 15, difficulty: 'Easy',
    participants: []
  }
]);

console.log(`Seed complete: ${sampleQuestions.length} sample questions. Admin: ${admin.email}, Student: ${student.email}`);
await mongoose.disconnect();
