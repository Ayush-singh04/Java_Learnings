import mongoose from 'mongoose';

const userSchema = new mongoose.Schema({
  name: { type: String, required: true, trim: true },
  email: { type: String, required: true, unique: true, lowercase: true, trim: true },
  password: { type: String, required: true },
  role: { type: String, enum: ['student', 'admin'], default: 'student' },
  avatar: String,
  points: { type: Number, default: 0 },
  xp: { type: Number, default: 0 },
  active: { type: Boolean, default: true },
  streak: { type: Number, default: 0 },
  achievements: [{ type: String }]
  ,resetPasswordTokenHash: String
  ,resetPasswordExpires: Date
  ,level: { type: Number, default: 1 }
}, { timestamps: true });

const subjectSchema = new mongoose.Schema({
  name: { type: String, required: true, unique: true },
  description: String,
  icon: { type: String, default: 'BookOpen' },
  topics: [String],
  active: { type: Boolean, default: true }
}, { timestamps: true });

const questionSchema = new mongoose.Schema({
  subject: { type: mongoose.Schema.Types.ObjectId, ref: 'Subject', required: true },
  topic: String,
  statement: { type: String, required: true },
  options: [{ text: String, isCorrect: Boolean }],
  difficulty: { type: String, enum: ['Easy', 'Medium', 'Hard'], default: 'Medium' },
  explanation: String,
  marks: { type: Number, default: 1 },
  negativeMarks: { type: Number, default: 0 }
}, { timestamps: true });

const competitionSchema = new mongoose.Schema({
  title: { type: String, required: true },
  subject: { type: mongoose.Schema.Types.ObjectId, ref: 'Subject', required: true },
  description: String,
  code: { type: String, required: true, unique: true, uppercase: true },
  questions: [{ type: mongoose.Schema.Types.ObjectId, ref: 'Question' }],
  startAt: { type: Date, required: true },
  endAt: { type: Date, required: true },
  durationMinutes: { type: Number, required: true },
  difficulty: { type: String, enum: ['Easy', 'Medium', 'Hard'], default: 'Medium' },
  status: { type: String, enum: ['Draft', 'Upcoming', 'Live', 'Completed'], default: 'Upcoming' },
  totalMarks: { type: Number, default: 0 },
  maxParticipants: { type: Number, default: 0 },
  published: { type: Boolean, default: true },
  randomizeQuestions: { type: Boolean, default: false },
  randomizeOptions: { type: Boolean, default: false },
  negativeMarking: { type: Boolean, default: false },
  violationAutoSubmit: { type: Boolean, default: false },
  participants: [{ type: mongoose.Schema.Types.ObjectId, ref: 'User' }]
}, { timestamps: true });

const attemptSchema = new mongoose.Schema({
  user: { type: mongoose.Schema.Types.ObjectId, ref: 'User', required: true },
  competition: { type: mongoose.Schema.Types.ObjectId, ref: 'Competition', required: true },
  answers: [{ question: mongoose.Schema.Types.ObjectId, selectedOption: Number, marked: Boolean }],
  startedAt: Date,
  submittedAt: Date,
  deadlineAt: Date,
  score: { type: Number, default: 0 },
  maxScore: { type: Number, default: 0 },
  correct: { type: Number, default: 0 },
  incorrect: { type: Number, default: 0 },
  unattempted: { type: Number, default: 0 },
  accuracy: { type: Number, default: 0 },
  timeTakenMinutes: { type: Number, default: 0 },
  warningCount: { type: Number, default: 0 },
  status: { type: String, enum: ['in-progress', 'submitted'], default: 'in-progress' }
}, { timestamps: true });

attemptSchema.index({ user: 1, competition: 1 }, { unique: true });

const bookmarkSchema = new mongoose.Schema({
  user: { type: mongoose.Schema.Types.ObjectId, ref: 'User', required: true },
  question: { type: mongoose.Schema.Types.ObjectId, ref: 'Question', required: true }
}, { timestamps: true });
bookmarkSchema.index({ user: 1, question: 1 }, { unique: true });

const learningMaterialSchema = new mongoose.Schema({
  subject: { type: mongoose.Schema.Types.ObjectId, ref: 'Subject', required: true },
  topic: { type: String, required: true, trim: true },
  title: { type: String, required: true, trim: true },
  content: { type: String, required: true },
  difficulty: { type: String, enum: ['Easy', 'Medium', 'Hard'], default: 'Easy' },
  published: { type: Boolean, default: true }
}, { timestamps: true });

const announcementSchema = new mongoose.Schema({
  title: { type: String, required: true, trim: true },
  message: { type: String, required: true },
  published: { type: Boolean, default: true },
  type: { type: String, enum: ['General', 'Competition', 'Result', 'Achievement'], default: 'General' }
}, { timestamps: true });

const certificateSchema = new mongoose.Schema({
  user: { type: mongoose.Schema.Types.ObjectId, ref: 'User', required: true },
  competition: { type: mongoose.Schema.Types.ObjectId, ref: 'Competition', required: true },
  attempt: { type: mongoose.Schema.Types.ObjectId, ref: 'Attempt', required: true },
  certificateId: { type: String, required: true, unique: true },
  achievement: { type: String, default: 'Participation Certificate' },
  score: Number,
  issuedAt: { type: Date, default: Date.now }
}, { timestamps: true });
certificateSchema.index({ user: 1, competition: 1 }, { unique: true });

const dailyChallengeSchema = new mongoose.Schema({
  dateKey: { type: String, required: true, unique: true },
  questions: [{ type: mongoose.Schema.Types.ObjectId, ref: 'Question' }],
  durationMinutes: { type: Number, default: 10 }
}, { timestamps: true });

const dailyAttemptSchema = new mongoose.Schema({
  user: { type: mongoose.Schema.Types.ObjectId, ref: 'User', required: true },
  dateKey: { type: String, required: true },
  challenge: { type: mongoose.Schema.Types.ObjectId, ref: 'DailyChallenge', required: true },
  answers: [{ question: mongoose.Schema.Types.ObjectId, selectedOption: Number }],
  score: { type: Number, default: 0 },
  correct: { type: Number, default: 0 },
  submittedAt: Date,
  xpAwarded: { type: Number, default: 0 }
}, { timestamps: true });
dailyAttemptSchema.index({ user: 1, dateKey: 1 }, { unique: true });

const suspiciousEventSchema = new mongoose.Schema({
  attempt: { type: mongoose.Schema.Types.ObjectId, ref: 'Attempt', required: true },
  user: { type: mongoose.Schema.Types.ObjectId, ref: 'User', required: true },
  type: { type: String, required: true },
  timestamp: { type: Date, default: Date.now }
}, { timestamps: true });

export const User = mongoose.model('User', userSchema);
export const Subject = mongoose.model('Subject', subjectSchema);
export const Question = mongoose.model('Question', questionSchema);
export const Competition = mongoose.model('Competition', competitionSchema);
export const Attempt = mongoose.model('Attempt', attemptSchema);
export const Bookmark = mongoose.model('Bookmark', bookmarkSchema);
export const LearningMaterial = mongoose.model('LearningMaterial', learningMaterialSchema);
export const Announcement = mongoose.model('Announcement', announcementSchema);
export const Certificate = mongoose.model('Certificate', certificateSchema);
export const DailyChallenge = mongoose.model('DailyChallenge', dailyChallengeSchema);
export const DailyAttempt = mongoose.model('DailyAttempt', dailyAttemptSchema);
export const SuspiciousEvent = mongoose.model('SuspiciousEvent', suspiciousEventSchema);
