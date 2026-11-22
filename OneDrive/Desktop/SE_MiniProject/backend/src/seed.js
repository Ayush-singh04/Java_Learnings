import 'dotenv/config';
import mongoose from 'mongoose';
import bcrypt from 'bcryptjs';
import { User, Subject, Question, Competition } from './models.js';

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

const subjectNames = [
  ['Java Programming', 'Core Java, OOP, collections, and JVM concepts.', 'Coffee'],
  ['Python Programming', 'Python syntax, libraries, automation, and problem solving.', 'Code2'],
  ['Data Structures and Algorithms', 'Arrays, trees, graphs, complexity, and algorithms.', 'Network'],
  ['Database Management Systems', 'Relational databases, normalization, transactions, and SQL.', 'Database'],
  ['Web Development', 'HTML, CSS, JavaScript, APIs, and modern frameworks.', 'Globe2'],
  ['Operating Systems', 'Processes, memory, scheduling, and file systems.', 'Monitor'],
  ['Computer Networks', 'Protocols, networking layers, routing, and security.', 'Router'],
  ['Aptitude and Logical Reasoning', 'Quantitative aptitude and logical puzzles.', 'BrainCircuit']
];
const subjects = await Subject.insertMany(subjectNames.map(([name, description, icon]) => ({
  name, description, icon,
  topics: ['Fundamentals', 'Intermediate Concepts', 'Advanced Practice']
})));

const java = subjects[0];
const questions = await Question.insertMany([
  {
    subject: java._id, topic: 'Object-Oriented Programming',
    statement: 'Which keyword is used to inherit a class in Java?',
    options: [{ text: 'implements', isCorrect: false }, { text: 'extends', isCorrect: true }, { text: 'inherits', isCorrect: false }, { text: 'super', isCorrect: false }],
    difficulty: 'Easy', explanation: 'The extends keyword creates an inheritance relationship between classes.', marks: 2
  },
  {
    subject: java._id, topic: 'Collections',
    statement: 'Which Java collection does not allow duplicate elements?',
    options: [{ text: 'ArrayList', isCorrect: false }, { text: 'LinkedList', isCorrect: false }, { text: 'HashSet', isCorrect: true }, { text: 'Vector', isCorrect: false }],
    difficulty: 'Medium', explanation: 'HashSet is a Set implementation and prevents duplicate elements.', marks: 2
  },
  {
    subject: java._id, topic: 'Fundamentals',
    statement: 'What is the size of an int in Java?',
    options: [{ text: '16 bits', isCorrect: false }, { text: '32 bits', isCorrect: true }, { text: '64 bits', isCorrect: false }, { text: 'Depends on platform', isCorrect: false }],
    difficulty: 'Easy', explanation: 'Java primitive int values are always 32 bits.', marks: 1
  },
  {
    subject: subjects[1]._id, topic: 'Fundamentals',
    statement: 'Which keyword begins a function definition in Python?',
    options: [{ text: 'function', isCorrect: false }, { text: 'def', isCorrect: true }, { text: 'fun', isCorrect: false }, { text: 'define', isCorrect: false }],
    difficulty: 'Easy', explanation: 'Python uses the def keyword to define functions.', marks: 1
  }
]);

await Competition.insertMany([
  {
    title: 'Java Sprint Challenge',
    subject: java._id, description: 'Test your Java fundamentals in a fast-paced timed challenge.',
    code: 'JAVA25', questions: questions.slice(0, 3), startAt: new Date(Date.now() - 3600000),
    endAt: new Date(Date.now() + 7 * 86400000), durationMinutes: 20, difficulty: 'Medium',
    participants: [student._id]
  },
  {
    title: 'Python Fundamentals Cup',
    subject: subjects[1]._id, description: 'A beginner-friendly competition covering Python essentials.',
    code: 'PYTHON1', questions: [questions[3]], startAt: new Date(Date.now() + 86400000),
    endAt: new Date(Date.now() + 3 * 86400000), durationMinutes: 15, difficulty: 'Easy',
    participants: []
  }
]);

console.log(`Seed complete. Admin: ${admin.email}, Student: ${student.email}`);
await mongoose.disconnect();
