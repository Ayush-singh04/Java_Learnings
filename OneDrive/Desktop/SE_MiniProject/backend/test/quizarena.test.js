import { beforeAll, afterAll, beforeEach, describe, it, expect } from 'vitest';
import { MongoMemoryServer } from 'mongodb-memory-server';
import mongoose from 'mongoose';
import bcrypt from 'bcryptjs';
import request from 'supertest';
import { app } from '../src/server.js';
import { User, Subject, Question, Competition, Attempt, DailyAttempt, Certificate, DailyChallenge } from '../src/models.js';
import { ensureSampleQuestionBank } from '../src/sampleData.js';
import { sampleSubjects } from '../src/sampleQuestions.js';

let mongo;
let admin;
let student;
let subject;
let adminToken;
let studentToken;

beforeAll(async () => {
  mongo = await MongoMemoryServer.create();
  await mongoose.connect(mongo.getUri());
});

afterAll(async () => {
  await mongoose.disconnect();
  await mongo.stop();
});

beforeEach(async () => {
  await Promise.all([
    User.deleteMany({}), Subject.deleteMany({}), Question.deleteMany({}),
    Competition.deleteMany({}), Attempt.deleteMany({}), DailyAttempt.deleteMany({}),
    Certificate.deleteMany({})
  ]);
  subject = await Subject.create({ name: 'Java', description: 'Java test subject', topics: ['OOP'], active: true });
  admin = await User.create({ name: 'Test Admin', email: 'admin@test.com', password: await bcrypt.hash('Admin@123', 4), role: 'admin' });
  student = await User.create({ name: 'Test Student', email: 'student@test.com', password: await bcrypt.hash('Student@123', 4), role: 'student' });
  const adminLogin = await request(app).post('/api/auth/login').send({ email: 'admin@test.com', password: 'Admin@123' });
  const studentLogin = await request(app).post('/api/auth/login').send({ email: 'student@test.com', password: 'Student@123' });
  adminToken = adminLogin.body.token;
  studentToken = studentLogin.body.token;
});

describe('authentication and authorization', () => {
  it('registers and prevents duplicate email registration', async () => {
    const first = await request(app).post('/api/auth/register').send({ name: 'New Student', email: 'new@test.com', password: 'Password@123', confirmPassword: 'Password@123' });
    const duplicate = await request(app).post('/api/auth/register').send({ name: 'New Student', email: 'new@test.com', password: 'Password@123', confirmPassword: 'Password@123' });
    expect(first.status).toBe(201);
    expect(duplicate.status).toBe(409);
  });

  it('rejects protected admin routes for students', async () => {
    const response = await request(app).get('/api/admin/users').set('Authorization', `Bearer ${studentToken}`);
    expect(response.status).toBe(403);
  });

  it('returns reset token only in development', async () => {
    process.env.NODE_ENV = 'development';
    const response = await request(app).post('/api/auth/forgot-password').send({ email: 'student@test.com' });
    expect(response.status).toBe(200);
    expect(response.body.resetToken).toBeTypeOf('string');
    const reset = await request(app).post('/api/auth/reset-password').send({ token: response.body.resetToken, password: 'NewPassword@123' });
    expect(reset.status).toBe(200);
    const reused = await request(app).post('/api/auth/reset-password').send({ token: response.body.resetToken, password: 'AnotherPassword@123' });
    expect(reused.status).toBe(400);
  });
});

describe('competition and question management', () => {
  it('creates a shared sample question bank without duplicating questions on repeat runs', async () => {
    await ensureSampleQuestionBank();
    await ensureSampleQuestionBank();
    expect(await Question.countDocuments()).toBe(50);
    expect(await Subject.countDocuments({ name: { $in: sampleSubjects.map(item => item.name) } })).toBe(8);
    const bank = await request(app).get('/api/admin/questions').set('Authorization', `Bearer ${adminToken}`);
    const practice = await request(app).get('/api/practice/questions').set('Authorization', `Bearer ${studentToken}`);
    expect(bank.body.questions).toHaveLength(50);
    expect(practice.body.questions).toHaveLength(50);
  });

  it('allows an administrator to create an MCQ and competition', async () => {
    const question = await request(app).post('/api/admin/questions').set('Authorization', `Bearer ${adminToken}`).send({
      subject: subject._id, topic: 'Basics', statement: 'Which keyword creates a subclass?',
      options: [{ text: 'extends', isCorrect: true }, { text: 'implements', isCorrect: false }, { text: 'inherit', isCorrect: false }, { text: 'super', isCorrect: false }],
      difficulty: 'Easy', marks: 2, negativeMarks: 1, explanation: 'extends creates inheritance'
    });
    expect(question.status).toBe(201);
    const now = new Date();
    const competition = await request(app).post('/api/admin/competitions').set('Authorization', `Bearer ${adminToken}`).send({
      title: 'Test Competition', subject: subject._id, description: 'Test',
      startAt: new Date(now.getTime() - 60000).toISOString(), endAt: new Date(now.getTime() + 3600000).toISOString(),
      durationMinutes: 30, difficulty: 'Easy', status: 'Live', published: true,
      negativeMarking: true, questions: [question.body.question._id], totalMarks: 2
    });
    expect(competition.status).toBe(201);
  });

  it('rejects a question without exactly one correct option', async () => {
    const response = await request(app).post('/api/admin/questions').set('Authorization', `Bearer ${adminToken}`).send({
      subject: subject._id, statement: 'Invalid question',
      options: [{ text: 'A', isCorrect: false }, { text: 'B', isCorrect: false }, { text: 'C', isCorrect: false }, { text: 'D', isCorrect: false }],
      marks: 1
    });
    expect(response.status).toBe(400);
  });
});

describe('quiz security and grading', () => {
  it('lets an admin start a scheduled test and a student join and submit it with the shared code', async () => {
    const question = await Question.create({
      subject: subject._id, statement: 'What is 2 + 2?',
      options: [{ text: '3', isCorrect: false }, { text: '4', isCorrect: true }, { text: '5', isCorrect: false }, { text: '6', isCorrect: false }],
      marks: 1
    });
    const competition = await Competition.create({
      title: 'Ready to Start', subject: subject._id, code: 'START1',
      questions: [question._id], startAt: new Date(Date.now() + 3600000),
      endAt: new Date(Date.now() + 7200000), durationMinutes: 20,
      published: false, status: 'Upcoming'
    });

    const activated = await request(app).post(`/api/admin/competitions/${competition._id}/activate`).set('Authorization', `Bearer ${adminToken}`);
    expect(activated.status).toBe(200);
    expect(activated.body.competition.code).toBe('START1');

    const listed = await request(app).get('/api/competitions').set('Authorization', `Bearer ${studentToken}`);
    expect(listed.body.competitions[0]).not.toHaveProperty('code');
    const joined = await request(app).post('/api/competitions/join-by-code').set('Authorization', `Bearer ${studentToken}`).send({ code: ' start1 ' });
    expect(joined.status).toBe(200);
    const started = await request(app).post(`/api/competitions/${competition._id}/start`).set('Authorization', `Bearer ${studentToken}`).send({});
    expect(started.status).toBe(200);
    const submitted = await request(app).post(`/api/attempts/${started.body.attemptId}/submit`).set('Authorization', `Bearer ${studentToken}`).send({
      answers: [{ question: question._id, selectedOption: 1 }], manual: true
    });
    expect(submitted.status).toBe(200);
    expect(submitted.body.result).toMatchObject({ score: 1, correct: 1, unattempted: 0 });
  });

  it('does not expose correct answers and prevents cross-user attempt access', async () => {
    const question = await Question.create({ subject: subject._id, statement: 'A test', options: [{ text: 'Right', isCorrect: true }, { text: 'Wrong', isCorrect: false }, { text: 'No', isCorrect: false }, { text: 'No', isCorrect: false }], marks: 2, negativeMarks: 1 });
    const competition = await Competition.create({ title: 'Live Test', subject: subject._id, code: 'TEST1', questions: [question._id], startAt: new Date(Date.now() - 60000), endAt: new Date(Date.now() + 3600000), durationMinutes: 30, published: true, negativeMarking: true });
    await request(app).post(`/api/competitions/${competition._id}/join`).set('Authorization', `Bearer ${studentToken}`).send({ code: 'TEST1' });
    const started = await request(app).post(`/api/competitions/${competition._id}/start`).set('Authorization', `Bearer ${studentToken}`).send({});
    expect(started.status).toBe(200);
    expect(JSON.stringify(started.body.questions)).not.toContain('"isCorrect"');
    const otherAttempt = await request(app).put(`/api/attempts/${started.body.attemptId}/progress`).set('Authorization', `Bearer ${adminToken}`).send({ answers: [] });
    expect(otherAttempt.status).toBe(404);
  });

  it('applies negative marking and prevents duplicate submissions', async () => {
    const question = await Question.create({ subject: subject._id, statement: 'A test', options: [{ text: 'Right', isCorrect: true }, { text: 'Wrong', isCorrect: false }, { text: 'No', isCorrect: false }, { text: 'No', isCorrect: false }], marks: 2, negativeMarks: 1 });
    const competition = await Competition.create({ title: 'Grading Test', subject: subject._id, code: 'GRADE1', questions: [question._id], startAt: new Date(Date.now() - 60000), endAt: new Date(Date.now() + 3600000), durationMinutes: 30, published: true, negativeMarking: true });
    await request(app).post(`/api/competitions/${competition._id}/join`).set('Authorization', `Bearer ${studentToken}`).send({ code: 'GRADE1' });
    const started = await request(app).post(`/api/competitions/${competition._id}/start`).set('Authorization', `Bearer ${studentToken}`).send({});
    const answers = [{ question: question._id, selectedOption: 1 }];
    const submitted = await request(app).post(`/api/attempts/${started.body.attemptId}/submit`).set('Authorization', `Bearer ${studentToken}`).send({ answers, manual: true });
    const duplicate = await request(app).post(`/api/attempts/${started.body.attemptId}/submit`).set('Authorization', `Bearer ${studentToken}`).send({ answers, manual: true });
    expect(submitted.status).toBe(200);
    expect(submitted.body.result.score).toBe(-1);
    expect(duplicate.status).toBe(409);
  });
});

describe('daily challenge and certificates', () => {
  it('allows one daily submission and prevents duplicate XP awards', async () => {
    await Question.create({ subject: subject._id, statement: 'Daily question', options: [{ text: 'A', isCorrect: true }, { text: 'B', isCorrect: false }, { text: 'C', isCorrect: false }, { text: 'D', isCorrect: false }], marks: 1 });
    const firstChallenge = await request(app).get('/api/daily-challenge').set('Authorization', `Bearer ${studentToken}`);
    expect(firstChallenge.status).toBe(200);
    expect(firstChallenge.body.challenge).not.toBeNull();
    const questionId = firstChallenge.body.challenge.questions[0]._id;
    const submitted = await request(app).post(`/api/daily-challenge/${firstChallenge.body.challenge.id}/submit`).set('Authorization', `Bearer ${studentToken}`).send({ answers: [{ question: questionId, selectedOption: 0 }] });
    const duplicate = await request(app).post(`/api/daily-challenge/${firstChallenge.body.challenge.id}/submit`).set('Authorization', `Bearer ${studentToken}`).send({ answers: [{ question: questionId, selectedOption: 0 }] });
    expect(submitted.status).toBe(200);
    expect(duplicate.status).toBe(409);
  });

  it('rejects certificate issuance below eligibility and prevents duplicates', async () => {
    const question = await Question.create({ subject: subject._id, statement: 'Certificate test', options: [{ text: 'A', isCorrect: true }, { text: 'B', isCorrect: false }, { text: 'C', isCorrect: false }, { text: 'D', isCorrect: false }], marks: 1 });
    const competition = await Competition.create({ title: 'Certificate Competition', subject: subject._id, code: 'CERT1', questions: [question._id], startAt: new Date(Date.now() - 60000), endAt: new Date(Date.now() + 3600000), durationMinutes: 30, published: true });
    const attempt = await Attempt.create({ user: student._id, competition: competition._id, startedAt: new Date(), submittedAt: new Date(), deadlineAt: new Date(Date.now() + 3600000), status: 'submitted', score: 1, maxScore: 1, correct: 1, incorrect: 0, unattempted: 0, accuracy: 100 });
    const issued = await request(app).post(`/api/certificates/generate/${attempt._id}`).set('Authorization', `Bearer ${studentToken}`).send({});
    expect(issued.status).toBe(201);
    const duplicate = await request(app).post(`/api/certificates/generate/${attempt._id}`).set('Authorization', `Bearer ${studentToken}`).send({});
    expect(duplicate.status).toBe(200);
  });

  it('rejects certificate issuance below 60 percent', async () => {
    const question = await Question.create({ subject: subject._id, statement: 'Low score', options: [{ text: 'A', isCorrect: true }, { text: 'B', isCorrect: false }, { text: 'C', isCorrect: false }, { text: 'D', isCorrect: false }], marks: 1 });
    const competition = await Competition.create({ title: 'Low Score Competition', subject: subject._id, code: 'LOW1', questions: [question._id], startAt: new Date(Date.now() - 60000), endAt: new Date(Date.now() + 3600000), durationMinutes: 30, published: true });
    const attempt = await Attempt.create({ user: student._id, competition: competition._id, startedAt: new Date(), submittedAt: new Date(), deadlineAt: new Date(Date.now() + 3600000), status: 'submitted', score: 0, maxScore: 2, correct: 0, incorrect: 1, unattempted: 0, accuracy: 0 });
    const response = await request(app).post(`/api/certificates/generate/${attempt._id}`).set('Authorization', `Bearer ${studentToken}`).send({});
    expect(response.status).toBe(400);
  });
});
