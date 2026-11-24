import 'dotenv/config';
import express from 'express';
import cors from 'cors';
import mongoose from 'mongoose';
import bcrypt from 'bcryptjs';
import jwt from 'jsonwebtoken';
import crypto from 'crypto';
import nodemailer from 'nodemailer';
import { User, Subject, Question, Competition, Attempt, Bookmark, LearningMaterial, Announcement, Certificate, DailyChallenge, DailyAttempt, SuspiciousEvent } from './models.js';
import { auth, adminOnly } from './middleware.js';
import { ensureSampleQuestionBank } from './sampleData.js';

const app = express();
const allowedOrigins = new Set([
  process.env.CLIENT_URL || 'http://localhost:5173',
  'http://localhost:5173',
  'http://127.0.0.1:5173'
]);
for (const origin of (process.env.CLIENT_URLS || '').split(',').map(value => value.trim()).filter(Boolean)) {
  allowedOrigins.add(origin);
}
app.use(cors({
  origin(origin, callback) {
    if (!origin || allowedOrigins.has(origin)) return callback(null, true);
    return callback(new Error('Origin is not allowed by CORS'));
  },
  credentials: true
}));
app.use(express.json());

const signToken = (user) => jwt.sign(
  { id: user._id, role: user.role },
  process.env.JWT_SECRET,
  { expiresIn: process.env.JWT_EXPIRES_IN || '7d' }
);

const competitionStatus = (competition) => {
  if (competition.status === 'Draft') return 'Draft';
  if (competition.status === 'Completed') return 'Completed';
  if (!competition.published) return 'Draft';
  const now = Date.now();
  if (now < new Date(competition.startAt).getTime()) return 'Upcoming';
  if (now > new Date(competition.endAt).getTime()) return 'Completed';
  return 'Live';
};

app.get('/api/health', (_req, res) => res.json({ status: 'ok' }));

app.post('/api/auth/register', async (req, res) => {
  try {
    const { name, email, password, confirmPassword } = req.body;
    if (!name || !email || !password) return res.status(400).json({ message: 'All fields are required' });
    if (password !== confirmPassword) return res.status(400).json({ message: 'Passwords do not match' });
    if (password.length < 8) return res.status(400).json({ message: 'Password must be at least 8 characters' });
    if (await User.findOne({ email: email.toLowerCase() })) return res.status(409).json({ message: 'Email is already registered' });
    const user = await User.create({ name, email, password: await bcrypt.hash(password, 12) });
    res.status(201).json({ message: 'Registration successful' });
  } catch (error) {
    res.status(500).json({ message: 'Registration failed', error: error.message });
  }
});

app.post('/api/auth/login', async (req, res) => {
  const { email, password } = req.body;
  const user = await User.findOne({ email: email?.toLowerCase() });
  if (!user || !(await bcrypt.compare(password || '', user.password))) return res.status(401).json({ message: 'Invalid email or password' });
  if (!user.active) return res.status(403).json({ message: 'Your account has been deactivated' });
  res.json({ token: signToken(user), user: { id: user._id, name: user.name, email: user.email, role: user.role, points: user.points } });
});

app.get('/api/auth/me', auth, (req, res) => res.json({ user: req.user }));
app.put('/api/auth/profile', auth, async (req, res) => {
  const { name, avatar } = req.body;
  const user = await User.findByIdAndUpdate(req.user._id, { name, avatar }, { new: true, runValidators: true }).select('-password');
  res.json({ user });
});
app.put('/api/auth/change-password', auth, async (req, res) => {
  const { currentPassword, newPassword } = req.body;
  const user = await User.findById(req.user._id);
  if (!(await bcrypt.compare(currentPassword || '', user.password))) return res.status(400).json({ message: 'Current password is incorrect' });
  if (!newPassword || newPassword.length < 8) return res.status(400).json({ message: 'New password must be at least 8 characters' });
  user.password = await bcrypt.hash(newPassword, 12);
  await user.save();
  res.json({ message: 'Password changed successfully' });
});

app.post('/api/auth/forgot-password', async (req, res) => {
  const user = await User.findOne({ email: req.body.email?.toLowerCase() });
  if (user) {
    const token = crypto.randomBytes(32).toString('hex');
    user.resetPasswordTokenHash = crypto.createHash('sha256').update(token).digest('hex');
    user.resetPasswordExpires = new Date(Date.now() + 30 * 60 * 1000);
    await user.save();
    if (process.env.NODE_ENV === 'development') {
      return res.json({ message: 'If that account exists, reset instructions are available.', resetToken: token });
    }
    if (process.env.SMTP_HOST && process.env.SMTP_USER && process.env.SMTP_PASS) {
      try {
        const transporter = nodemailer.createTransport({
          host: process.env.SMTP_HOST,
          port: Number(process.env.SMTP_PORT || 587),
          secure: process.env.SMTP_SECURE === 'true',
          auth: { user: process.env.SMTP_USER, pass: process.env.SMTP_PASS }
        });
        const resetUrl = `${process.env.CLIENT_URL || 'http://localhost:5173'}/reset-password/${token}`;
        await transporter.sendMail({
          from: process.env.SMTP_FROM || process.env.SMTP_USER,
          to: user.email,
          subject: 'Reset your QuizArena password',
          text: `Reset your password within 30 minutes: ${resetUrl}`,
          html: `<h2>QuizArena Password Reset</h2><p>Use this secure link within 30 minutes:</p><p><a href="${resetUrl}">Reset Password</a></p>`
        });
      } catch (error) {
        console.error('Password reset email could not be sent:', error.message);
      }
    }
  }
  res.json({ message: 'If that account exists, reset instructions have been sent.' });
});

app.post('/api/auth/reset-password', async (req, res) => {
  const { token, password } = req.body;
  if (!token || !password || password.length < 8) return res.status(400).json({ message: 'Valid reset token and password are required' });
  const tokenHash = crypto.createHash('sha256').update(token).digest('hex');
  const user = await User.findOne({ resetPasswordTokenHash: tokenHash, resetPasswordExpires: { $gt: new Date() } });
  if (!user) return res.status(400).json({ message: 'Reset token is invalid or expired' });
  user.password = await bcrypt.hash(password, 12);
  user.resetPasswordTokenHash = undefined;
  user.resetPasswordExpires = undefined;
  await user.save();
  res.json({ message: 'Password reset successfully' });
});

app.get('/api/public/home', async (_req, res) => {
  const [subjects, competitions, attempts] = await Promise.all([
    Subject.find({ active: true }).limit(12),
    Competition.find({ published: true }).populate('subject', 'name').sort({ startAt: 1 }).limit(6),
    Attempt.find({ status: 'submitted' }).populate('user', 'name avatar').sort({ score: -1 }).limit(5)
  ]);
  const leaderboard = attempts.reduce((map, attempt) => {
    if (attempt.user) map.set(String(attempt.user._id), { id: attempt.user._id, name: attempt.user.name, score: attempt.score, competitions: (map.get(String(attempt.user._id))?.competitions || 0) + 1 });
    return map;
  }, new Map());
  res.json({
    subjects,
    competitions: competitions.map(c => {
      const { code, ...competition } = c.toObject();
      return { ...competition, status: competitionStatus(c) };
    }),
    leaderboard: [...leaderboard.values()].sort((a, b) => b.score - a.score).slice(0, 5)
  });
});

app.get('/api/subjects', auth, async (req, res) => {
  const filter = req.query.search ? { name: { $regex: req.query.search, $options: 'i' } } : {};
  if (req.user.role !== 'admin') filter.active = true;
  const subjects = await Subject.find(filter).sort({ name: 1 });
  res.json({ subjects });
});

app.get('/api/competitions', auth, async (req, res) => {
  const competitions = await Competition.find({ published: true }).populate('subject', 'name').sort({ startAt: 1 });
  res.json({ competitions: competitions.map(c => {
    const { code, ...competition } = c.toObject();
    return { ...competition, status: competitionStatus(c), participantCount: c.participants.length };
  }) });
});

async function joinCompetition(req, res, competition) {
  if (!competition) return res.status(404).json({ message: 'Competition not found' });
  if (!competition.published) return res.status(400).json({ message: 'This competition is not published' });
  if (competitionStatus(competition) === 'Completed') return res.status(400).json({ message: 'This competition has ended' });
  if (String(req.body.code || '').trim().toUpperCase() !== competition.code) return res.status(400).json({ message: 'Invalid competition code' });
  if (competition.maxParticipants > 0 && competition.participants.length >= competition.maxParticipants
    && !competition.participants.some(id => String(id) === String(req.user._id))) {
    return res.status(400).json({ message: 'This competition has reached its participant limit' });
  }
  if (!competition.participants.includes(req.user._id)) competition.participants.push(req.user._id);
  await competition.save();
  res.json({ message: 'Joined successfully', competition: { id: competition._id, title: competition.title, status: competitionStatus(competition) } });
}

app.post('/api/competitions/join-by-code', auth, async (req, res) => {
  const code = String(req.body.code || '').trim().toUpperCase();
  if (!code) return res.status(400).json({ message: 'Test code is required' });
  const competition = await Competition.findOne({ code });
  return joinCompetition(req, res, competition);
});

app.post('/api/competitions/:id/join', auth, async (req, res) => {
  const competition = await Competition.findById(req.params.id);
  return joinCompetition(req, res, competition);
});

app.post('/api/competitions/:id/start', auth, async (req, res) => {
  const competition = await Competition.findById(req.params.id).populate('questions');
  if (!competition) return res.status(404).json({ message: 'Competition not found' });
  if (!competition.published) return res.status(400).json({ message: 'Competition is not published' });
  if (competitionStatus(competition) !== 'Live') return res.status(400).json({ message: 'Competition is not currently live' });
  if (!competition.questions.length) return res.status(400).json({ message: 'This competition has no questions yet' });
  if (!competition.participants.some(id => String(id) === String(req.user._id))) return res.status(403).json({ message: 'Join the competition before starting' });

  let attempt = await Attempt.findOne({ user: req.user._id, competition: competition._id });
  if (attempt?.status === 'submitted') return res.status(400).json({ message: 'You have already submitted this competition' });
  if (!attempt) {
    attempt = await Attempt.create({
      user: req.user._id, competition: competition._id,
      startedAt: new Date(), deadlineAt: new Date(Date.now() + competition.durationMinutes * 60000),
      answers: [], maxScore: competition.questions.reduce((sum, q) => sum + (q.marks || 1), 0)
    });
  }
  const now = Date.now();
  if (now >= attempt.deadlineAt.getTime()) {
    return res.status(400).json({ message: 'Attempt deadline has passed' });
  }
  let questions = competition.questions;
  if (competition.randomizeQuestions) questions = [...questions].sort(() => Math.random() - 0.5);
  const publicQuestions = questions.map(q => ({
    _id: q._id, statement: q.statement,
    options: (competition.randomizeOptions
      ? q.options.map((option, originalIndex) => ({ text: option.text, originalIndex })).sort(() => Math.random() - 0.5)
      : q.options.map((option, originalIndex) => ({ text: option.text, originalIndex }))),
    marks: q.marks
  }));
  res.json({ attemptId: attempt._id, deadlineAt: attempt.deadlineAt, questions: publicQuestions });
});

app.put('/api/attempts/:id/progress', auth, async (req, res) => {
  const attempt = await Attempt.findOne({ _id: req.params.id, user: req.user._id, status: 'in-progress' });
  if (!attempt) return res.status(404).json({ message: 'Active attempt not found' });
  if (new Date() >= attempt.deadlineAt) return res.status(400).json({ message: 'Attempt deadline has passed' });
  attempt.answers = req.body.answers || attempt.answers;
  attempt.warningCount = Number(req.body.warningCount || 0);
  await attempt.save();
  res.json({ message: 'Progress saved' });
});

app.post('/api/attempts/:id/submit', auth, async (req, res) => {
  const attempt = await Attempt.findOne({ _id: req.params.id, user: req.user._id }).populate('competition');
  if (!attempt) return res.status(404).json({ message: 'Attempt not found' });
  if (attempt.status === 'submitted') return res.status(409).json({ message: 'Attempt already submitted' });

  const competition = await Competition.findById(attempt.competition._id).populate('questions');
  const deadlineReached = new Date() >= attempt.deadlineAt;
  if (deadlineReached && req.body.forceAutoSubmit !== true && req.body.manual !== true) {
    // The server accepts the first timeout submission, but rejects a later manual resubmission.
  }
  if (new Date() >= attempt.deadlineAt && req.body.forceAutoSubmit !== true && req.body.manual === true) {
    return res.status(400).json({ message: 'The attempt deadline has passed' });
  }
  let score = 0, correct = 0, incorrect = 0;
  const answerMap = new Map((req.body.answers || attempt.answers).map(a => [String(a.question), a]));
  for (const question of competition.questions) {
    const answer = answerMap.get(String(question._id));
    if (!answer || answer.selectedOption === null || answer.selectedOption === undefined) continue;
    if (!Number.isInteger(answer.selectedOption) || answer.selectedOption < 0 || answer.selectedOption >= question.options.length) {
      return res.status(400).json({ message: 'A submitted answer option is invalid' });
    }
    if (question.options[answer.selectedOption]?.isCorrect) {
      score += question.marks || 1;
      correct++;
    } else {
      score -= competition.negativeMarking ? (question.negativeMarks || 0) : 0;
      incorrect++;
    }
  }
  const unattempted = competition.questions.length - correct - incorrect;
  attempt.answers = req.body.answers || attempt.answers;
  attempt.score = score;
  attempt.correct = correct;
  attempt.incorrect = incorrect;
  attempt.unattempted = unattempted;
  attempt.accuracy = Math.round(((correct / (correct + incorrect || 1)) * 100) || 0);
  attempt.timeTakenMinutes = Math.max(1, Math.round((new Date() - attempt.startedAt) / 60000));
  attempt.maxScore = competition.questions.reduce((sum, q) => sum + (q.marks || 1), 0);
  attempt.status = 'submitted';
  attempt.submittedAt = new Date();
  attempt.warningCount = Number(req.body.warningCount || attempt.warningCount || 0);
  await attempt.save();

  const user = await User.findById(req.user._id);
  user.points = (user.points || 0) + Math.max(0, score) + (competition.participants.includes(user._id) ? 10 : 0);
  user.xp = (user.xp || 0) + 25 + correct * 5;
  await user.save();
  res.json({ message: 'Submitted successfully', result: {
    score, maxScore: attempt.maxScore, correct, incorrect, unattempted,
    accuracy: Math.round(((correct / (correct + incorrect || 1)) * 100) || 0),
    timeTakenMinutes: Math.max(1, Math.round((attempt.submittedAt - attempt.startedAt) / 60000))
  }});
});

app.get('/api/attempts/:id/details', auth, async (req, res) => {
  const attempt = await Attempt.findOne({ _id: req.params.id, user: req.user._id, status: 'submitted' }).populate({ path: 'competition', populate: { path: 'questions', populate: { path: 'subject', select: 'name' } } });
  if (!attempt) return res.status(404).json({ message: 'Submitted attempt not found' });
  const answerMap = new Map(attempt.answers.map(answer => [String(answer.question), answer]));
  const questions = (attempt.competition.questions || []).map(question => {
    const answer = answerMap.get(String(question._id));
    const correctIndex = question.options.findIndex(option => option.isCorrect);
    return {
      _id: question._id,
      statement: question.statement,
      selectedOption: answer?.selectedOption ?? null,
      correctOption: correctIndex,
      explanation: question.explanation || '',
      isCorrect: answer?.selectedOption === correctIndex
    };
  });
  res.json({ attempt, questions });
});

app.get('/api/results', auth, async (req, res) => {
  const filter = req.user.role === 'admin' ? {} : { user: req.user._id };
  const results = await Attempt.find({ ...filter, status: 'submitted' })
    .populate('user', 'name email points').populate({ path: 'competition', populate: { path: 'subject', select: 'name' } })
    .sort({ submittedAt: -1 });
  res.json({ results });
});

app.get('/api/leaderboard', auth, async (req, res) => {
  const users = await User.find({ role: 'student', active: true }).select('name avatar points xp').sort({ points: -1, xp: -1 });
  let attemptFilter = { status: 'submitted' };
  if (req.query.period === 'weekly') attemptFilter.submittedAt = { $gte: new Date(Date.now() - 7 * 86400000) };
  if (req.query.period === 'monthly') attemptFilter.submittedAt = { $gte: new Date(Date.now() - 30 * 86400000) };
  if (req.query.subject) {
    const subject = await Subject.findById(req.query.subject);
    if (subject) {
      const competitions = await Competition.find({ subject: subject._id }).select('_id');
      attemptFilter.competition = { $in: competitions.map(item => item._id) };
    }
  }
  const entries = await Promise.all(users.map(async (user, index) => {
    const attempts = await Attempt.find({ ...attemptFilter, user: user._id });
    const max = attempts.reduce((sum, a) => sum + a.maxScore, 0);
    const scored = attempts.reduce((sum, a) => sum + a.score, 0);
    return { rank: index + 1, id: user._id, name: user.name, points: user.points, competitions: attempts.length, averageScore: max ? Math.round(scored / max * 100) : 0 };
  }));
  res.json({ leaderboard: entries });
});

app.get('/api/practice/questions', auth, async (req, res) => {
  const filter = {};
  if (req.query.subject) filter.subject = req.query.subject;
  if (req.query.difficulty) filter.difficulty = req.query.difficulty;
  const questions = await Question.find(filter).populate('subject', 'name').limit(50);
  res.json({ questions });
});

app.post('/api/practice/bookmarks', auth, async (req, res) => {
  await Bookmark.findOneAndUpdate({ user: req.user._id, question: req.body.questionId }, { user: req.user._id, question: req.body.questionId }, { upsert: true });
  res.status(201).json({ message: 'Question bookmarked' });
});
app.get('/api/practice/bookmarks', auth, async (req, res) => {
  const bookmarks = await Bookmark.find({ user: req.user._id }).populate({ path: 'question', populate: { path: 'subject', select: 'name' } });
  res.json({ bookmarks });
});

app.get('/api/dashboard', auth, async (req, res) => {
  const attempts = await Attempt.find({ user: req.user._id, status: 'submitted' }).populate({ path: 'competition', populate: { path: 'subject', select: 'name' } }).sort({ submittedAt: -1 });
  const maxScore = attempts.reduce((sum, a) => sum + a.maxScore, 0);
  const score = attempts.reduce((sum, a) => sum + a.score, 0);
  const competitions = await Competition.find({ published: true }).populate('subject', 'name').sort({ startAt: 1 }).limit(5);
  const rankedUsers = await User.find({ role: 'student', active: true }).select('_id points').sort({ points: -1, xp: -1, name: 1 });
  const rank = rankedUsers.findIndex(user => String(user._id) === String(req.user._id)) + 1;
  const xp = req.user.xp || 0;
  const level = Math.floor(xp / 500) + 1;
  const learning = await LearningMaterial.find({ published: true }).populate('subject', 'name').sort({ updatedAt: -1 }).limit(5);
  const subjectProgress = await Promise.all(attempts.reduce((subjects, attempt) => {
    const subject = attempt.competition?.subject?.name;
    if (subject && !subjects.includes(subject)) subjects.push(subject);
    return subjects;
  }, []).map(async subjectName => {
    const subjectAttempts = attempts.filter(attempt => attempt.competition?.subject?.name === subjectName);
    const subjectMax = subjectAttempts.reduce((sum, attempt) => sum + attempt.maxScore, 0);
    const subjectScore = subjectAttempts.reduce((sum, attempt) => sum + attempt.score, 0);
    return { subject: subjectName, attempts: subjectAttempts.length, progress: subjectMax ? Math.max(0, Math.round(subjectScore / subjectMax * 100)) : 0 };
  }));
  res.json({
    stats: {
      attempted: attempts.length,
      averageScore: maxScore ? Math.round(score / maxScore * 100) : 0,
      points: req.user.points,
      accuracy: maxScore ? Math.round(score / maxScore * 100) : 0,
      xp,
      level,
      rank: rank || null,
      xpToNextLevel: 500 - (xp % 500),
      competitions: attempts.length
    },
    recentAttempts: attempts.slice(0, 5),
    upcomingCompetitions: competitions.map(c => ({ ...c.toObject(), status: competitionStatus(c) })),
    subjectProgress,
    recommendedLearning: learning,
    achievements: req.user.achievements || []
  });
});

app.get('/api/learning', auth, async (req, res) => {
  const filter = { published: true };
  if (req.query.subject) filter.subject = req.query.subject;
  const materials = await LearningMaterial.find(filter).populate('subject', 'name').sort({ updatedAt: -1 });
  res.json({ materials });
});

app.get('/api/announcements', auth, async (_req, res) => {
  res.json({ announcements: await Announcement.find({ published: true }).sort({ createdAt: -1 }).limit(20) });
});

app.post('/api/admin/learning', auth, adminOnly, async (req, res) => {
  const { subject, topic, title, content, difficulty, published } = req.body;
  if (!subject || !topic?.trim() || !title?.trim() || !content?.trim()) return res.status(400).json({ message: 'Subject, topic, title, and content are required' });
  res.status(201).json({ material: await LearningMaterial.create({ subject, topic, title, content, difficulty, published }) });
});
app.get('/api/admin/learning', auth, adminOnly, async (_req, res) => {
  res.json({ materials: await LearningMaterial.find().populate('subject', 'name').sort({ createdAt: -1 }) });
});
app.put('/api/admin/learning/:id', auth, adminOnly, async (req, res) => {
  res.json({ material: await LearningMaterial.findByIdAndUpdate(req.params.id, req.body, { new: true, runValidators: true }) });
});
app.delete('/api/admin/learning/:id', auth, adminOnly, async (req, res) => {
  await LearningMaterial.findByIdAndDelete(req.params.id);
  res.json({ message: 'Learning material deleted' });
});

app.get('/api/admin/announcements', auth, adminOnly, async (_req, res) => {
  res.json({ announcements: await Announcement.find().sort({ createdAt: -1 }) });
});
app.post('/api/admin/announcements', auth, adminOnly, async (req, res) => {
  const { title, message, published, type } = req.body;
  if (!title?.trim() || !message?.trim()) return res.status(400).json({ message: 'Title and message are required' });
  res.status(201).json({ announcement: await Announcement.create({ title, message, published, type }) });
});
app.put('/api/admin/announcements/:id', auth, adminOnly, async (req, res) => {
  res.json({ announcement: await Announcement.findByIdAndUpdate(req.params.id, req.body, { new: true, runValidators: true }) });
});
app.delete('/api/admin/announcements/:id', auth, adminOnly, async (req, res) => {
  await Announcement.findByIdAndDelete(req.params.id);
  res.json({ message: 'Announcement deleted' });
});

app.get('/api/daily-challenge', auth, async (req, res) => {
  const dateKey = new Intl.DateTimeFormat('en-CA', { timeZone: process.env.APP_TIMEZONE || 'Asia/Kolkata' }).format(new Date());
  let challenge = await DailyChallenge.findOne({ dateKey }).populate('questions');
  if (!challenge) {
    const questions = await Question.find().limit(5);
    if (!questions.length) return res.json({ challenge: null, attempted: false });
    challenge = await DailyChallenge.create({ dateKey, questions: questions.map(question => question._id), durationMinutes: 10 });
    challenge = await DailyChallenge.findById(challenge._id).populate('questions');
  }
  const existing = await DailyAttempt.findOne({ user: req.user._id, dateKey });
  res.json({
    challenge: {
      id: challenge._id,
      dateKey,
      durationMinutes: challenge.durationMinutes,
      questions: challenge.questions.map(question => ({ _id: question._id, statement: question.statement, options: question.options.map(option => ({ text: option.text })) }))
    },
    attempted: Boolean(existing),
    result: existing ? { score: existing.score, correct: existing.correct } : null
  });
});

app.post('/api/daily-challenge/:id/submit', auth, async (req, res) => {
  const dateKey = new Intl.DateTimeFormat('en-CA', { timeZone: process.env.APP_TIMEZONE || 'Asia/Kolkata' }).format(new Date());
  const existing = await DailyAttempt.findOne({ user: req.user._id, dateKey });
  if (existing) return res.status(409).json({ message: 'Daily challenge already submitted today' });
  const challenge = await DailyChallenge.findOne({ _id: req.params.id, dateKey }).populate('questions');
  if (!challenge) return res.status(400).json({ message: 'Daily challenge is unavailable' });
  let score = 0, correct = 0;
  const answers = Array.isArray(req.body.answers) ? req.body.answers : [];
  for (const question of challenge.questions) {
    const answer = answers.find(item => String(item.question) === String(question._id));
    if (answer && Number.isInteger(answer.selectedOption) && question.options[answer.selectedOption]?.isCorrect) {
      score++;
      correct++;
    }
  }
  const attempt = await DailyAttempt.create({ user: req.user._id, dateKey, challenge: challenge._id, answers, score, correct, submittedAt: new Date(), xpAwarded: 20 + correct * 5 });
  const user = await User.findById(req.user._id);
  user.xp = (user.xp || 0) + attempt.xpAwarded;
  user.points = (user.points || 0) + attempt.xpAwarded;
  user.level = Math.floor(user.xp / 500) + 1;
  const yesterdayKey = new Intl.DateTimeFormat('en-CA', { timeZone: process.env.APP_TIMEZONE || 'Asia/Kolkata' }).format(new Date(Date.now() - 86400000));
  const yesterdayAttempt = await DailyAttempt.findOne({ user: req.user._id, dateKey: yesterdayKey });
  user.streak = yesterdayAttempt ? (user.streak || 0) + 1 : 1;
  const achievements = new Set(user.achievements || []);
  if (correct === challenge.questions.length) achievements.add('Perfect Score');
  achievements.add('Daily Challenger');
  user.achievements = [...achievements];
  await user.save();
  res.json({ message: 'Daily challenge submitted', result: { score, correct, total: challenge.questions.length, xpAwarded: attempt.xpAwarded } });
});

app.post('/api/attempts/:id/suspicious-event', auth, async (req, res) => {
  const attempt = await Attempt.findOne({ _id: req.params.id, user: req.user._id });
  if (!attempt) return res.status(404).json({ message: 'Attempt not found' });
  if (!['tab-switch', 'fullscreen-exit'].includes(req.body.type)) return res.status(400).json({ message: 'Invalid event type' });
  await SuspiciousEvent.create({ attempt: attempt._id, user: req.user._id, type: req.body.type, timestamp: new Date() });
  attempt.warningCount = (attempt.warningCount || 0) + 1;
  await attempt.save();
  res.status(201).json({ message: 'Suspicious event recorded', warningCount: attempt.warningCount });
});

app.get('/api/certificates', auth, async (req, res) => {
  res.json({ certificates: await Certificate.find({ user: req.user._id }).populate('competition', 'title').populate('user', 'name') });
});
app.post('/api/certificates/generate/:attemptId', auth, async (req, res) => {
  const attempt = await Attempt.findOne({ _id: req.params.attemptId, user: req.user._id, status: 'submitted' }).populate('competition');
  if (!attempt) return res.status(404).json({ message: 'Eligible submitted attempt not found' });
  const competition = await Competition.findById(attempt.competition._id);
  const accuracy = attempt.maxScore ? attempt.score / attempt.maxScore : 0;
  if (accuracy < 0.6) return res.status(400).json({ message: 'Certificate eligibility requires at least 60 percent marks' });
  const existing = await Certificate.findOne({ user: req.user._id, competition: competition._id });
  if (existing) return res.json({ certificate: existing });
  const certificate = await Certificate.create({
    user: req.user._id, competition: competition._id, attempt: attempt._id,
    certificateId: `QA-${crypto.randomBytes(5).toString('hex').toUpperCase()}`,
    achievement: 'Certificate of Achievement', score: attempt.score
  });
  res.status(201).json({ certificate });
});
app.get('/api/certificates/verify/:certificateId', async (req, res) => {
  const certificate = await Certificate.findOne({ certificateId: req.params.certificateId }).populate('user', 'name').populate('competition', 'title');
  if (!certificate) return res.status(404).json({ message: 'Certificate not found' });
  res.json({ valid: true, studentName: certificate.user.name, competitionName: certificate.competition.title, achievement: certificate.achievement, issueDate: certificate.issuedAt, certificateId: certificate.certificateId });
});

app.get('/api/admin/stats', auth, adminOnly, async (_req, res) => {
  const [students, subjects, questions, competitions, attempts, participants] = await Promise.all([
    User.countDocuments({ role: 'student' }), Subject.countDocuments(), Question.countDocuments(),
    Competition.countDocuments(), Attempt.countDocuments(), Attempt.countDocuments({ status: 'submitted' })
  ]);
  const submitted = await Attempt.find({ status: 'submitted' }).populate('competition');
  const maxScore = submitted.reduce((sum, attempt) => sum + attempt.maxScore, 0);
  const totalScore = submitted.reduce((sum, attempt) => sum + Math.max(0, attempt.score), 0);
  const popular = await Attempt.aggregate([
    { $match: { status: 'submitted' } },
    { $group: { _id: '$competition', attempts: { $sum: 1 } } },
    { $sort: { attempts: -1 } }, { $limit: 5 }
  ]);
  res.json({
    students, subjects, questions, competitions, attempts, participants,
    averagePerformance: maxScore ? Math.round(totalScore / maxScore * 100) : 0,
    popularCompetitions: popular,
    recentActivity: submitted.slice(-5).reverse()
  });
});

app.post('/api/admin/subjects', auth, adminOnly, async (req, res) => {
  const { name, description, topics, icon, active } = req.body;
  if (!name?.trim()) return res.status(400).json({ message: 'Subject name is required' });
  if (await Subject.findOne({ name: name.trim() })) return res.status(409).json({ message: 'A subject with this name already exists' });
  res.status(201).json({ subject: await Subject.create({ name: name.trim(), description, topics: Array.isArray(topics) ? topics : [], icon, active }) });
});
app.put('/api/admin/subjects/:id', auth, adminOnly, async (req, res) => {
  const subject = await Subject.findById(req.params.id);
  if (!subject) return res.status(404).json({ message: 'Subject not found' });
  if (req.body.name && req.body.name.trim() !== subject.name) {
    if (await Subject.findOne({ name: req.body.name.trim(), _id: { $ne: subject._id } })) return res.status(409).json({ message: 'A subject with this name already exists' });
  }
  const updates = {
    ...(req.body.name ? { name: req.body.name.trim() } : {}),
    ...(req.body.description !== undefined ? { description: req.body.description } : {}),
    ...(req.body.topics !== undefined ? { topics: Array.isArray(req.body.topics) ? req.body.topics : [] } : {}),
    ...(req.body.icon !== undefined ? { icon: req.body.icon } : {}),
    ...(req.body.active !== undefined ? { active: Boolean(req.body.active) } : {})
  };
  res.json({ subject: await Subject.findByIdAndUpdate(req.params.id, updates, { new: true, runValidators: true }) });
});
app.delete('/api/admin/subjects/:id', auth, adminOnly, async (req, res) => {
  const [questionCount, materialCount, competitionCount] = await Promise.all([
    Question.countDocuments({ subject: req.params.id }),
    LearningMaterial.countDocuments({ subject: req.params.id }),
    Competition.countDocuments({ subject: req.params.id })
  ]);
  if (questionCount || materialCount || competitionCount) {
    return res.status(409).json({ message: 'Subject cannot be deleted while questions, learning materials, or competitions depend on it. Archive it instead.' });
  }
  await Subject.findByIdAndDelete(req.params.id);
  res.json({ message: 'Subject deleted' });
});

app.get('/api/admin/users', auth, adminOnly, async (_req, res) => {
  res.json({ users: await User.find().select('-password').sort({ createdAt: -1 }) });
});
app.put('/api/admin/users/:id/status', auth, adminOnly, async (req, res) => {
  const user = await User.findByIdAndUpdate(req.params.id, { active: req.body.active }, { new: true }).select('-password');
  res.json({ user });
});

app.get('/api/admin/questions', auth, adminOnly, async (_req, res) => {
  res.json({ questions: await Question.find().populate('subject', 'name').sort({ createdAt: -1 }) });
});
app.post('/api/admin/questions', auth, adminOnly, async (req, res) => {
  const { subject, statement, options, difficulty, explanation, marks, negativeMarks } = req.body;
  if (!subject || !statement?.trim()) return res.status(400).json({ message: 'Subject and question statement are required' });
  if (!Array.isArray(options) || options.length !== 4) return res.status(400).json({ message: 'Exactly four answer options are required' });
  if (options.some(option => !option.text?.trim())) return res.status(400).json({ message: 'Every answer option must have text' });
  if (options.filter(option => option.isCorrect === true).length !== 1) return res.status(400).json({ message: 'Exactly one answer option must be correct' });
  if (Number(marks) < 0 || Number(negativeMarks) < 0) return res.status(400).json({ message: 'Marks cannot be negative' });
  res.status(201).json({ question: await Question.create({ subject, statement, options, difficulty, explanation, marks, negativeMarks }) });
});
app.put('/api/admin/questions/:id', auth, adminOnly, async (req, res) => {
  const { subject, statement, options, difficulty, explanation, marks, negativeMarks } = req.body;
  if (!subject || !statement?.trim()) return res.status(400).json({ message: 'Subject and question statement are required' });
  if (!Array.isArray(options) || options.length !== 4) return res.status(400).json({ message: 'Exactly four answer options are required' });
  if (options.some(option => !option.text?.trim())) return res.status(400).json({ message: 'Every answer option must have text' });
  if (options.filter(option => option.isCorrect === true).length !== 1) return res.status(400).json({ message: 'Exactly one answer option must be correct' });
  if (Number(marks) < 0 || Number(negativeMarks) < 0) return res.status(400).json({ message: 'Marks cannot be negative' });
  res.json({ question: await Question.findByIdAndUpdate(req.params.id, { subject, statement, options, difficulty, explanation, marks, negativeMarks }, { new: true, runValidators: true }) });
});
app.delete('/api/admin/questions/:id', auth, adminOnly, async (req, res) => {
  await Question.findByIdAndDelete(req.params.id);
  res.json({ message: 'Question deleted' });
});

app.get('/api/admin/competitions', auth, adminOnly, async (_req, res) => {
  res.json({ competitions: await Competition.find().populate('subject', 'name').populate('questions').sort({ startAt: -1 }) });
});
app.post('/api/admin/competitions/:id/activate', auth, adminOnly, async (req, res) => {
  const competition = await Competition.findById(req.params.id);
  if (!competition) return res.status(404).json({ message: 'Competition not found' });
  if (!competition.questions.length) return res.status(400).json({ message: 'Add at least one question before starting this test' });

  const now = new Date();
  competition.startAt = now;
  if (competition.endAt <= now) {
    competition.endAt = new Date(now.getTime() + competition.durationMinutes * 60000);
  }
  competition.status = 'Live';
  competition.published = true;
  await competition.save();
  res.json({ competition: { id: competition._id, title: competition.title, code: competition.code, startAt: competition.startAt, endAt: competition.endAt } });
});
app.get('/api/admin/competitions/:id/participants', auth, adminOnly, async (req, res) => {
  const competition = await Competition.findById(req.params.id).populate('participants', 'name email createdAt active');
  if (!competition) return res.status(404).json({ message: 'Competition not found' });
  const attempts = await Attempt.find({ competition: competition._id })
    .populate('user', 'name email createdAt')
    .sort({ submittedAt: -1, startedAt: -1 });
  const participantIds = new Set(attempts.map(attempt => String(attempt.user?._id)));
  const rows = competition.participants.map(participant => {
    const attempt = attempts.find(item => String(item.user?._id) === String(participant._id));
    return {
      id: participant._id,
      name: participant.name,
      email: participant.email,
      registeredAt: participant.createdAt,
      attemptStatus: attempt ? attempt.status : 'Not started',
      score: attempt?.status === 'submitted' ? attempt.score : null,
      maxScore: attempt?.status === 'submitted' ? attempt.maxScore : null,
      submittedAt: attempt?.submittedAt || null
    };
  });
  for (const attempt of attempts) {
    if (!participantIds.has(String(attempt.user?._id)) && attempt.user) {
      rows.push({
        id: attempt.user._id, name: attempt.user.name, email: attempt.user.email,
        registeredAt: attempt.user.createdAt, attemptStatus: attempt.status,
        score: attempt.status === 'submitted' ? attempt.score : null,
        maxScore: attempt.status === 'submitted' ? attempt.maxScore : null,
        submittedAt: attempt.submittedAt || null
      });
    }
  }
  if (req.query.format === 'csv') {
    const escapeCsv = value => `"${String(value ?? '').replace(/"/g, '""')}"`;
    const header = 'Name,Email,Attempt Status,Score,Maximum Score,Submitted At\n';
    const body = rows.map(row => [row.name, row.email, row.attemptStatus, row.score ?? '', row.maxScore ?? '', row.submittedAt ? new Date(row.submittedAt).toISOString() : ''].map(escapeCsv).join(',')).join('\n');
    res.setHeader('Content-Type', 'text/csv');
    res.setHeader('Content-Disposition', `attachment; filename="${(competition.title || 'competition').replace(/[^a-z0-9]/gi, '-')}-participants.csv"`);
    return res.send(header + body);
  }
  res.json({ competition: { id: competition._id, title: competition.title }, participants: rows });
});
app.post('/api/admin/competitions', auth, adminOnly, async (req, res) => {
  const { title, subject, description, startAt, endAt, durationMinutes, difficulty, status, published, negativeMarking, randomizeQuestions, randomizeOptions, maxParticipants, totalMarks, questions } = req.body;
  if (!title?.trim() || !subject || !startAt || !endAt || !durationMinutes) return res.status(400).json({ message: 'Title, subject, schedule, and duration are required' });
  if (new Date(endAt) <= new Date(startAt)) return res.status(400).json({ message: 'End date must be after start date' });
  if (Number(durationMinutes) < 1) return res.status(400).json({ message: 'Duration must be at least 1 minute' });
  if (Number(maxParticipants) < 0) return res.status(400).json({ message: 'Maximum participants cannot be negative' });
  const body = { title, subject, description, startAt, endAt, durationMinutes, difficulty, status, published, negativeMarking, randomizeQuestions, randomizeOptions, maxParticipants, totalMarks, questions: questions || [],
    code: (req.body.code || crypto.randomBytes(4).toString('hex')).trim().toUpperCase() };
  res.status(201).json({ competition: await Competition.create(body) });
});
app.put('/api/admin/competitions/:id', auth, adminOnly, async (req, res) => {
  const { title, subject, description, startAt, endAt, durationMinutes, difficulty, status, published, negativeMarking, randomizeQuestions, randomizeOptions, maxParticipants, totalMarks, questions } = req.body;
  if (!title?.trim() || !subject || !startAt || !endAt || !durationMinutes) return res.status(400).json({ message: 'Title, subject, schedule, and duration are required' });
  if (new Date(endAt) <= new Date(startAt)) return res.status(400).json({ message: 'End date must be after start date' });
  if (Number(durationMinutes) < 1) return res.status(400).json({ message: 'Duration must be at least 1 minute' });
  const updates = { title, subject, description, startAt, endAt, durationMinutes, difficulty, status, published, negativeMarking, randomizeQuestions, randomizeOptions, maxParticipants, totalMarks, questions };
  res.json({ competition: await Competition.findByIdAndUpdate(req.params.id, updates, { new: true, runValidators: true }) });
});
app.delete('/api/admin/competitions/:id', auth, adminOnly, async (req, res) => {
  await Competition.findByIdAndDelete(req.params.id);
  res.json({ message: 'Competition deleted' });
});

app.use((err, _req, res, _next) => {
  console.error(err);
  res.status(500).json({ message: 'Unexpected server error' });
});

const port = process.env.PORT || 5000;
export { app };
if (process.env.NODE_ENV !== 'test') {
  mongoose.connect(process.env.MONGODB_URI || 'mongodb://127.0.0.1:27017/quizarena')
    .then(async () => {
      await ensureSampleQuestionBank();
      app.listen(port, () => console.log(`QuizArena API running on port ${port}`));
    })
    .catch(error => {
      console.error('MongoDB connection failed:', error.message);
      process.exit(1);
    });
}
