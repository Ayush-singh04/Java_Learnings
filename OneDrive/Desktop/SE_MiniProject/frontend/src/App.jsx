import { BrowserRouter, Route, Routes, useNavigate, useParams } from 'react-router-dom';
import { useCallback, useEffect, useRef, useState } from 'react';
import axios from 'axios';
import { Toaster, toast } from 'react-hot-toast';
import {
  Activity, Award, BarChart3, Bell, BookOpen, BrainCircuit, ChevronRight, Code2,
  Crown, Database, LayoutDashboard, LogOut, Menu, Moon, Settings,
  Sun, Trophy, Users, X
} from 'lucide-react';
import { Area, AreaChart, ResponsiveContainer, Tooltip, XAxis, YAxis } from 'recharts';
import { sampleQuestions, sampleSubjects } from './sampleQuestions.js';

const api = axios.create({ baseURL: import.meta.env.VITE_API_URL || 'http://localhost:5000/api' });

const DEMO_MODE = import.meta.env.VITE_DEMO_MODE === 'true';
const demoKey = 'quizarena_demo_state';

function getDemoState() {
  try {
    const defaults = {
      users: [
        { id: 'demo-student', name: 'Demo Student', email: 'student@quizarena.com', role: 'student', active: true, points: 240, xp: 340, createdAt: new Date().toISOString() },
        { id: 'demo-admin', name: 'QuizArena Admin', email: 'admin@quizarena.com', role: 'admin', active: true, points: 0, xp: 0, createdAt: new Date().toISOString() }
      ],
      subjects: sampleSubjects.map((subject, index) => ({ ...subject, _id: `sample-subject-${index + 1}`, active: true })),
      questions: sampleQuestions.map((question, index) => ({
        ...question,
        _id: `sample-question-${index + 1}`,
        subject: { _id: `sample-subject-${sampleSubjects.findIndex(subject => subject.name === question.subject) + 1}`, name: question.subject },
        options: question.options.map((text, optionIndex) => ({ text, isCorrect: optionIndex === question.answer })),
        marks: 1,
        negativeMarks: 0
      })),
      competitions: [
        { _id: 'java-sprint', title: 'Java Sprint Challenge', subject: { _id: 'sample-subject-1', name: 'Java Programming' }, description: 'Test your Java fundamentals in a timed challenge.', code: 'JAVA25', questions: sampleQuestions.map((question, index) => question.subject === 'Java Programming' ? `sample-question-${index + 1}` : null).filter(Boolean), participants: ['demo-student'], startAt: new Date(Date.now() - 3600000).toISOString(), endAt: new Date(Date.now() + 7 * 86400000).toISOString(), durationMinutes: 20, status: 'Live', published: true, participantCount: 1, difficulty: 'Medium', negativeMarking: false, maxParticipants: 100 },
        { _id: 'python-cup', title: 'Python Fundamentals Cup', subject: { _id: 'sample-subject-2', name: 'Python Programming' }, description: 'A beginner-friendly Python challenge.', code: 'PYTHON1', questions: sampleQuestions.map((question, index) => question.subject === 'Python Programming' ? `sample-question-${index + 1}` : null).filter(Boolean), participants: [], startAt: new Date(Date.now() + 86400000).toISOString(), endAt: new Date(Date.now() + 3 * 86400000).toISOString(), durationMinutes: 15, status: 'Upcoming', published: true, participantCount: 0, difficulty: 'Easy', negativeMarking: false, maxParticipants: 100 }
      ],
      attempts: [],
      materials: [],
      announcements: [],
      certificates: [],
      bookmarks: []
    };
    const saved = JSON.parse(localStorage.getItem(demoKey));
    if (!saved) return defaults;
    return {
      ...defaults,
      ...saved,
      subjects: [...(saved.subjects || []), ...defaults.subjects.filter(subject => !(saved.subjects || []).some(item => item.name === subject.name))],
      questions: [...(saved.questions || []), ...defaults.questions.filter(question => !(saved.questions || []).some(item => item.statement === question.statement))],
      competitions: saved.competitions || defaults.competitions
    };
  } catch {
    localStorage.removeItem(demoKey);
    return {
      users: [], subjects: sampleSubjects.map((subject, index) => ({ ...subject, _id: `sample-subject-${index + 1}`, active: true })),
      questions: sampleQuestions.map((question, index) => ({
        ...question,
        _id: `sample-question-${index + 1}`,
        subject: { _id: `sample-subject-${sampleSubjects.findIndex(subject => subject.name === question.subject) + 1}`, name: question.subject },
        options: question.options.map((text, optionIndex) => ({ text, isCorrect: optionIndex === question.answer })),
        marks: 1,
        negativeMarks: 0
      })),
      competitions: [],
      attempts: [], materials: [], announcements: [], certificates: [], bookmarks: []
    };
  }
}

function saveDemoState(state) {
  localStorage.setItem(demoKey, JSON.stringify(state));
}

if (DEMO_MODE) {
  api.defaults.adapter = async config => {
    const error = new Error('Using local demo data');
    error.config = config;
    throw error;
  };
  api.interceptors.response.use(
    response => response,
    async error => {
      const config = error.config || {};
      const url = String(config.url || '');
      const method = String(config.method || 'get').toLowerCase();
      const state = getDemoState();
      const payload = typeof config.data === 'string' ? JSON.parse(config.data || '{}') : (config.data || {});
      const currentUser = JSON.parse(localStorage.getItem('quizarena_user') || '{}');

      if (method === 'post' && url.endsWith('/auth/login')) {
        const user = state.users.find(item => item.email.toLowerCase() === String(payload.email || '').toLowerCase());
        if (!user || !['Admin@123', 'Student@123'].includes(payload.password)) throw { response: { data: { message: 'Use the demo account credentials shown below.' } } };
        if (!user.active) throw { response: { data: { message: 'This demo account is inactive.' } } };
        localStorage.setItem('quizarena_token', 'demo-token');
        localStorage.setItem('quizarena_user', JSON.stringify({ id: user.id, name: user.name, email: user.email, role: user.role, points: user.points }));
        return { data: { token: 'demo-token', user: { id: user.id, name: user.name, email: user.email, role: user.role, points: user.points } } };
      }
      if (method === 'post' && url.endsWith('/auth/register')) {
        if (!payload.name || !payload.email || payload.password !== payload.confirmPassword) throw { response: { data: { message: 'Complete all fields and ensure passwords match.' } } };
        if (payload.password.length < 8) throw { response: { data: { message: 'Password must be at least 8 characters.' } } };
        if (state.users.some(user => user.email.toLowerCase() === payload.email.toLowerCase())) throw { response: { data: { message: 'Email is already registered.' } } };
        state.users.push({ id: `demo-${Date.now()}`, name: payload.name, email: payload.email.toLowerCase(), role: 'student', active: true, points: 0, xp: 0, createdAt: new Date().toISOString() });
        saveDemoState(state);
        return { data: { message: 'Registration successful' } };
      }
      if (method === 'post' && url.endsWith('/auth/forgot-password')) return { data: { message: 'Demo reset links are not required. Use the demo login or reset locally.' } };
      if (method === 'post' && url.endsWith('/auth/reset-password')) return { data: { message: 'Demo password reset is handled locally.' } };

      if (method === 'put' && url.endsWith('/auth/profile')) {
        const stateUser = state.users.find(user => user.email === currentUser.email);
        if (stateUser) stateUser.name = payload.name || stateUser.name;
        saveDemoState(state);
        return { data: { user: { ...currentUser, name: payload.name || currentUser.name } } };
      }
      if (method === 'put' && url.endsWith('/auth/change-password')) return { data: { message: 'Password changed in demo mode.' } };

      if (url.endsWith('/public/home')) return { data: {
        subjects: state.subjects,
        competitions: state.competitions.map(competition => {
          const publicCompetition = { ...competition };
          delete publicCompetition.code;
          return publicCompetition;
        }),
        leaderboard: [{ id: 'demo-1', name: 'Demo Champion', score: 920, competitions: 8 }, { id: 'demo-2', name: 'Demo Scholar', score: 860, competitions: 7 }, { id: 'demo-3', name: 'Demo Learner', score: 810, competitions: 6 }]
      } };
      if (url.endsWith('/subjects')) return { data: { subjects: state.subjects.filter(subject => subject.active !== false) } };
      if (url.endsWith('/practice/questions')) {
        const questions = state.questions.filter(question =>
          (!config.params?.subject || String(question.subject?._id || question.subject) === String(config.params.subject))
          && (!config.params?.difficulty || question.difficulty === config.params.difficulty)
        );
        return { data: { questions: questions.slice(0, 50) } };
      }
      if (url.endsWith('/competitions') && !url.includes('/admin/') && method === 'get') return { data: { competitions: state.competitions.map(competition => {
        const publicCompetition = { ...competition, participantCount: competition.participants?.length || 0 };
        delete publicCompetition.code;
        return publicCompetition;
      }) } };
      if (url.endsWith('/leaderboard')) return { data: { leaderboard: state.users.filter(user => user.role === 'student').map((user, index) => ({ rank: index + 1, id: user.id, name: user.name, points: user.points, competitions: state.attempts.filter(attempt => attempt.userId === user.id).length, averageScore: 75 })) } };
      if (url.endsWith('/results')) return { data: { results: state.attempts.filter(attempt => currentUser.role === 'admin' || attempt.userId === currentUser.id).map(attempt => ({ ...attempt, competition: { title: attempt.title } })) } };
      if (url.endsWith('/dashboard')) return { data: {
        stats: { attempted: state.attempts.length, averageScore: 75, points: currentUser.points || 240, accuracy: 75, xp: currentUser.role === 'student' ? 340 : 0, level: 2, rank: 1, competitions: state.attempts.length },
        recentAttempts: state.attempts.slice(-5).reverse(),
        upcomingCompetitions: state.competitions.filter(item => item.status !== 'Completed').slice(0, 5),
        subjectProgress: [{ subject: 'Java Programming', attempts: state.attempts.length, progress: 75 }],
        recommendedLearning: [],
        achievements: ['Demo Learner']
      } };
      if (url.endsWith('/admin/stats')) return { data: { students: state.users.filter(user => user.role === 'student').length, subjects: state.subjects.length, questions: state.questions.length, competitions: state.competitions.length, attempts: state.attempts.length, participants: state.attempts.length, averagePerformance: 75 } };
      if (url.endsWith('/admin/users')) return { data: { users: state.users } };
      if (url.includes('/admin/competitions/') && url.endsWith('/participants')) {
        const competitionId = url.split('/').filter(Boolean).at(-2);
        const competition = state.competitions.find(item => item._id === competitionId);
        if (!competition) throw { response: { data: { message: 'Competition not found' } } };
        const participants = [...new Set([...(competition.participants || []), ...state.attempts.filter(attempt => attempt.competitionId === competitionId).map(attempt => attempt.userId)])]
          .map(id => {
            const user = state.users.find(item => item.id === id);
            const attempt = state.attempts.find(item => item.userId === id && item.competitionId === competitionId);
            return user ? { id, name: user.name, email: user.email, attemptStatus: attempt?.status || 'Not started', score: attempt?.score ?? null, maxScore: attempt?.maxScore ?? null, submittedAt: attempt?.submittedAt || null } : null;
          }).filter(Boolean);
        return { data: { competition: { id: competitionId, title: competition.title }, participants } };
      }
      if (url.endsWith('/admin/questions') && method === 'get') return { data: { questions: state.questions } };
      if (url.endsWith('/admin/competitions') && method === 'get') return { data: { competitions: state.competitions } };
      if (url.endsWith('/admin/learning')) return { data: { materials: state.materials } };
      if (url.endsWith('/admin/announcements')) return { data: { announcements: state.announcements } };
      if (url.endsWith('/learning')) return { data: { materials: state.materials } };
      if (url.endsWith('/announcements')) return { data: { announcements: state.announcements } };
      if (url.endsWith('/daily-challenge')) return { data: { challenge: { id: 'daily-demo', dateKey: new Date().toISOString().slice(0, 10), durationMinutes: 10, questions: state.questions.slice(0, 5).map(question => ({ _id: question._id, statement: question.statement, options: question.options.map(option => ({ text: option.text })) })) }, attempted: false, result: null } };
      if (url.endsWith('/certificates')) return { data: { certificates: state.certificates } };
      if (url.endsWith('/auth/me')) return { data: { user: currentUser } };

      if (method === 'post' && url.endsWith('/admin/questions')) {
        const subject = state.subjects.find(item => item._id === payload.subject);
        const question = { ...payload, _id: `demo-question-${Date.now()}`, subject: subject ? { _id: subject._id, name: subject.name } : payload.subject, createdAt: new Date().toISOString() };
        state.questions.unshift(question);
        saveDemoState(state);
        return { data: { question } };
      }
      if (method === 'put' && url.includes('/admin/questions/')) {
        const questionId = url.split('/').pop();
        const index = state.questions.findIndex(question => question._id === questionId);
        if (index < 0) throw { response: { data: { message: 'Question not found' } } };
        const subject = state.subjects.find(item => item._id === payload.subject);
        state.questions[index] = { ...state.questions[index], ...payload, subject: subject ? { _id: subject._id, name: subject.name } : payload.subject };
        saveDemoState(state);
        return { data: { question: state.questions[index] } };
      }
      if (method === 'delete' && url.includes('/admin/questions/')) {
        const questionId = url.split('/').pop();
        state.questions = state.questions.filter(question => question._id !== questionId);
        saveDemoState(state);
        return { data: { message: 'Question deleted' } };
      }
      if (method === 'post' && url.endsWith('/admin/competitions')) {
        const subject = state.subjects.find(item => item._id === payload.subject);
        const competition = {
          ...payload,
          _id: `demo-competition-${Date.now()}`,
          code: payload.code || Math.random().toString(36).slice(2, 8).toUpperCase(),
          subject: subject ? { _id: subject._id, name: subject.name } : payload.subject,
          questions: payload.questions || [],
          participants: [],
          status: new Date(payload.startAt) <= new Date() ? 'Live' : 'Upcoming',
          createdAt: new Date().toISOString()
        };
        state.competitions.unshift(competition);
        saveDemoState(state);
        return { data: { competition } };
      }
      if (method === 'put' && url.includes('/admin/competitions/')) {
        const competitionId = url.split('/').pop();
        const index = state.competitions.findIndex(item => item._id === competitionId);
        if (index < 0) throw { response: { data: { message: 'Competition not found' } } };
        const subject = state.subjects.find(item => item._id === payload.subject);
        state.competitions[index] = { ...state.competitions[index], ...payload, subject: subject ? { _id: subject._id, name: subject.name } : payload.subject };
        saveDemoState(state);
        return { data: { competition: state.competitions[index] } };
      }
      if (method === 'delete' && url.includes('/admin/competitions/')) {
        const competitionId = url.split('/').pop();
        state.competitions = state.competitions.filter(item => item._id !== competitionId);
        saveDemoState(state);
        return { data: { message: 'Competition deleted' } };
      }
      if (method === 'post' && url.includes('/admin/competitions/') && url.endsWith('/activate')) {
        const competitionId = url.split('/').filter(Boolean).at(-2);
        const competition = state.competitions.find(item => item._id === competitionId);
        if (!competition) throw { response: { data: { message: 'Competition not found' } } };
        if (!competition.questions.length) throw { response: { data: { message: 'Add at least one question before starting this test' } } };
        competition.startAt = new Date().toISOString();
        if (new Date(competition.endAt) <= new Date()) competition.endAt = new Date(Date.now() + competition.durationMinutes * 60000).toISOString();
        competition.status = 'Live';
        competition.published = true;
        saveDemoState(state);
        return { data: { competition } };
      }
      if (method === 'post' && url.includes('/competitions/') && url.endsWith('/join')) {
        const competitionId = url.split('/').filter(Boolean).at(-2);
        const competition = state.competitions.find(item => item._id === competitionId);
        if (!competition || String(payload.code || '').trim().toUpperCase() !== competition.code) throw { response: { data: { message: 'Invalid competition code' } } };
        competition.participants = competition.participants || [];
        if (!competition.participants.includes(currentUser.id)) competition.participants.push(currentUser.id);
        saveDemoState(state);
        return { data: { message: 'Joined successfully', competition: { id: competition._id, title: competition.title, status: competition.status } } };
      }
      if (method === 'post' && url.endsWith('/competitions/join-by-code')) {
        const competition = state.competitions.find(item => item.code === String(payload.code || '').trim().toUpperCase());
        if (!competition) throw { response: { data: { message: 'Invalid competition code' } } };
        competition.participants = competition.participants || [];
        if (!competition.participants.includes(currentUser.id)) competition.participants.push(currentUser.id);
        saveDemoState(state);
        return { data: { message: 'Joined successfully', competition: { id: competition._id, title: competition.title, status: competition.status } } };
      }
      if (method === 'post' && url.includes('/competitions/') && url.endsWith('/start')) {
        const id = url.split('/').filter(Boolean).at(-2);
        const competition = state.competitions.find(item => item._id === id);
        if (!competition) throw { response: { data: { message: 'Competition not found' } } };
        if (!(competition.participants || []).includes(currentUser.id)) throw { response: { data: { message: 'Join the competition before starting' } } };
        const questions = state.questions.filter(question => (competition.questions || []).includes(question._id));
        if (!questions.length) throw { response: { data: { message: 'This competition has no questions yet' } } };
        let attempt = state.attempts.find(item => item.userId === currentUser.id && item.competitionId === id && item.status === 'in-progress');
        if (!attempt) {
          attempt = { _id: `demo-attempt-${Date.now()}`, userId: currentUser.id, competitionId: id, title: competition.title, answers: [], status: 'in-progress', startedAt: new Date().toISOString(), deadlineAt: new Date(Date.now() + competition.durationMinutes * 60000).toISOString() };
          state.attempts.push(attempt);
          saveDemoState(state);
        }
        return { data: { attemptId: attempt._id, deadlineAt: attempt.deadlineAt, questions: questions.map(question => ({ _id: question._id, statement: question.statement, marks: question.marks, options: question.options.map((option, optionIndex) => ({ text: option.text, originalIndex: optionIndex })) })) } };
      }
      if (method === 'put' && url.includes('/attempts/') && url.endsWith('/progress')) {
        const attemptId = url.split('/').filter(Boolean).at(-2);
        const attempt = state.attempts.find(item => item._id === attemptId && item.userId === currentUser.id && item.status === 'in-progress');
        if (!attempt) throw { response: { data: { message: 'Active attempt not found' } } };
        attempt.answers = payload.answers || attempt.answers;
        saveDemoState(state);
        return { data: { message: 'Progress saved' } };
      }
      if (method === 'post' && url.includes('/attempts/') && url.endsWith('/suspicious-event')) return { data: { message: 'Event recorded' } };
      if (method === 'post' && url.includes('/attempts/') && url.endsWith('/submit')) {
        let score = 0, correct = 0, incorrect = 0;
        const attemptId = url.split('/').filter(Boolean).at(-2);
        const attempt = state.attempts.find(item => item._id === attemptId && item.userId === currentUser.id);
        if (!attempt || attempt.status === 'submitted') throw { response: { data: { message: 'Attempt is unavailable or already submitted' } } };
        const competition = state.competitions.find(item => item._id === attempt.competitionId);
        const questions = state.questions.filter(question => (competition?.questions || []).includes(question._id));
        const answers = Array.isArray(payload.answers) ? payload.answers : attempt.answers || [];
        for (const question of questions) {
          const answer = answers.find(item => item?.question === question._id);
          if (!answer || answer.selectedOption === null || answer.selectedOption === undefined) continue;
          if (question.options[answer.selectedOption]?.isCorrect) { score += question.marks || 1; correct++; }
          else incorrect++;
        }
        const maxScore = questions.reduce((sum, question) => sum + (question.marks || 1), 0);
        const unattempted = questions.length - correct - incorrect;
        Object.assign(attempt, {
          score, maxScore, correct, incorrect, unattempted,
          accuracy: Math.round(correct / Math.max(1, correct + incorrect) * 100),
          submittedAt: new Date().toISOString(),
          status: 'submitted'
        });
        const student = state.users.find(user => user.id === currentUser.id);
        if (student) {
          student.points = (student.points || 0) + score;
          student.xp = (student.xp || 0) + 25 + correct * 5;
        }
        saveDemoState(state);
        return { data: { message: 'Submitted successfully', result: { score, maxScore, correct, incorrect, unattempted, accuracy: attempt.accuracy, timeTakenMinutes: Math.max(1, Math.round((Date.now() - new Date(attempt.startedAt)) / 60000)) } } };
      }

      return Promise.reject(error);
    }
  );
}

function App() {
  const [theme, setTheme] = useState(localStorage.getItem('theme') || 'light');
  useEffect(() => {
    document.documentElement.dataset.theme = theme;
    localStorage.setItem('theme', theme);
  }, [theme]);
  return (
    <BrowserRouter>
      <Toaster position="top-right" />
      <Routes>
        <Route path="/" element={<Landing theme={theme} setTheme={setTheme} />} />
        <Route path="/login" element={<AuthPage mode="login" />} />
        <Route path="/register" element={<AuthPage mode="register" />} />
        <Route path="/forgot-password" element={<AuthPage mode="forgot" />} />
        <Route path="/reset-password/:token" element={<AuthPage mode="reset" />} />
        <Route path="/dashboard" element={<Dashboard theme={theme} setTheme={setTheme} />} />
        <Route path="/competitions" element={<Dashboard theme={theme} setTheme={setTheme} page="competitions" />} />
        <Route path="/practice" element={<Dashboard theme={theme} setTheme={setTheme} page="practice" />} />
        <Route path="/learn" element={<Dashboard theme={theme} setTheme={setTheme} page="learning" />} />
        <Route path="/daily-challenge" element={<Dashboard theme={theme} setTheme={setTheme} page="daily" />} />
        <Route path="/certificates" element={<Dashboard theme={theme} setTheme={setTheme} page="certificates" />} />
        <Route path="/leaderboard" element={<Dashboard theme={theme} setTheme={setTheme} page="leaderboard" />} />
        <Route path="/results" element={<Dashboard theme={theme} setTheme={setTheme} page="results" />} />
        <Route path="/profile" element={<Dashboard theme={theme} setTheme={setTheme} page="profile" />} />
        <Route path="/admin" element={<Dashboard theme={theme} setTheme={setTheme} page="admin" />} />
        <Route path="/admin/competitions" element={<Dashboard theme={theme} setTheme={setTheme} page="admin-competitions" />} />
        <Route path="/admin/competitions/:id/participants" element={<Dashboard theme={theme} setTheme={setTheme} page="admin-participants" />} />
        <Route path="/admin/questions" element={<Dashboard theme={theme} setTheme={setTheme} page="admin-questions" />} />
        <Route path="/admin/results" element={<Dashboard theme={theme} setTheme={setTheme} page="admin-results" />} />
        <Route path="/admin/users" element={<Dashboard theme={theme} setTheme={setTheme} page="admin-users" />} />
        <Route path="/admin/subjects" element={<Dashboard theme={theme} setTheme={setTheme} page="admin-subjects" />} />
        <Route path="/admin/learning" element={<Dashboard theme={theme} setTheme={setTheme} page="admin-learning" />} />
        <Route path="/admin/announcements" element={<Dashboard theme={theme} setTheme={setTheme} page="admin-announcements" />} />
        <Route path="/quiz/:id" element={<QuizPage />} />
      </Routes>
    </BrowserRouter>
  );
}

function tokenHeaders() {
  return { Authorization: `Bearer ${localStorage.getItem('quizarena_token')}` };
}

function Landing({ theme, setTheme }) {
  const navigate = useNavigate();
  const [home, setHome] = useState({ subjects: [], competitions: [], leaderboard: [] });
  const [mobileOpen, setMobileOpen] = useState(false);
  useEffect(() => {
    if (DEMO_MODE) {
      const demo = getDemoState();
      setHome({
        subjects: demo.subjects,
        competitions: demo.competitions,
        leaderboard: [{ id: 'demo-1', name: 'Demo Champion', score: 920, competitions: 8 }, { id: 'demo-2', name: 'Demo Scholar', score: 860, competitions: 7 }]
      });
      return;
    }
    api.get('/public/home').then(r => setHome(r.data)).catch(() => {});
  }, []);
  const subjectCatalog = [
    { name: 'Java', fullName: 'Java Programming', description: 'Object-oriented programming, collections, and JVM fundamentals.' },
    { name: 'Python', fullName: 'Python Programming', description: 'Syntax, data types, automation, and problem-solving.' },
    { name: 'DSA', fullName: 'Data Structures and Algorithms', description: 'Arrays, trees, graphs, complexity, and algorithms.' },
    { name: 'DBMS', fullName: 'Database Management Systems', description: 'Design, normalization, transactions, and relational databases.' },
    { name: 'SQL', fullName: 'Structured Query Language', description: 'Queries, joins, indexing, views, and database operations.' },
    { name: 'Operating Systems', fullName: 'Operating Systems', description: 'Processes, scheduling, memory, and file systems.' },
    { name: 'Computer Networks', fullName: 'Computer Networks', description: 'Protocols, network layers, routing, and security.' },
    { name: 'Web Development', fullName: 'Web Development', description: 'HTML, CSS, JavaScript, APIs, and modern applications.' },
    { name: 'Aptitude', fullName: 'Aptitude and Reasoning', description: 'Quantitative aptitude, logic, and analytical puzzles.' },
    { name: 'Mathematics', fullName: 'Mathematics', description: 'Core concepts, calculations, and exam-focused practice.' }
  ];
  const openPractice = () => {
    navigate(localStorage.getItem('quizarena_token') ? '/practice' : '/login');
  };
  const demoLeaderboard = [
    { id: 'demo-1', name: 'Sample Student A', score: 920, competitions: 8 },
    { id: 'demo-2', name: 'Sample Student B', score: 860, competitions: 7 },
    { id: 'demo-3', name: 'Sample Student C', score: 810, competitions: 6 }
  ];
  const leaderboard = home.leaderboard?.length ? home.leaderboard : demoLeaderboard;
  const isDemoLeaderboard = !home.leaderboard?.length;
  const upcomingCompetitions = home.competitions.filter(c => c.status !== 'Completed');
  const team = [
    ['Ayush Singh', 'Frontend Design & Development'],
    ['Himanshu Ojha', 'Backend Development'],
    ['Md. Sahabuddin', 'Documentation'],
    ['Ashutosh Kumar', 'Database Management'],
    ['Manish Kumar', 'Testing / QA']
  ];
  const closeMobileNav = () => setMobileOpen(false);
  return (
    <div className="landing">
      <nav className="nav shell">
        <button className="brand" onClick={() => navigate('/')}><span className="brand-mark"><BrainCircuit size={22} /></span>QuizArena</button>
        <button className="icon-btn mobile-menu-button" aria-label="Open navigation" aria-expanded={mobileOpen} onClick={() => setMobileOpen(!mobileOpen)}>{mobileOpen ? <X size={18} /> : <Menu size={18} />}</button>
        <div className={`nav-links ${mobileOpen ? 'open' : ''}`}>
          <a href="#subjects" onClick={closeMobileNav}>Subjects</a>
          <a href="#competitions" onClick={closeMobileNav}>Competitions</a>
          <a href="#leaderboard" onClick={closeMobileNav}>Leaderboard</a>
          <a href="#how-it-works" onClick={closeMobileNav}>How it works</a>
          <button onClick={() => { closeMobileNav(); navigate('/login'); }} className="btn-ghost">Login</button>
          <button onClick={() => navigate('/register')} className="btn-primary">Get started</button>
          <button className="icon-btn" aria-label={theme === 'light' ? 'Switch to dark mode' : 'Switch to light mode'} onClick={() => setTheme(theme === 'light' ? 'dark' : 'light')}>{theme === 'light' ? <Moon size={18} /> : <Sun size={18} />}</button>
        </div>
      </nav>
      <header className="hero shell">
        <div className="hero-copy">
          <div className="eyebrow"><span /> The arena for curious minds</div>
          <h1>Challenge your knowledge.<br /><em>Compete. Win.</em></h1>
          <p className="hero-tagline">Learn. Compete. Achieve.</p>
          <p>Learn, practice, and compete in timed quizzes across subjects that matter. Track every improvement and climb the leaderboard.</p>
          <div className="hero-actions">
            <button className="btn-primary btn-lg" onClick={() => navigate('/register')}>Start learning <ChevronRight size={18} /></button>
            <button className="btn-secondary btn-lg" onClick={() => document.querySelector('#competitions')?.scrollIntoView({ behavior: 'smooth' })}>Explore competitions</button>
          </div>
          <div className="social-proof"><div className="avatars"><b>A</b><b>R</b><b>K</b></div><span><strong>Growing community</strong><small>Practice. Improve. Repeat.</small></span></div>
        </div>
        <div className="hero-visual">
          <div className="orbit orbit-one" /><div className="orbit orbit-two" />
          <div className="quiz-card"><div className="quiz-card-head"><span>Daily challenge</span><b>01:42</b></div><h3>Which data structure follows LIFO?</h3><div className="answer selected">A. Stack</div><div className="answer">B. Queue</div><div className="answer">C. Tree</div></div>
          <div className="floating-score"><Trophy size={20} /><span><strong>+120 XP</strong><small>Excellent answer</small></span></div>
        </div>
      </header>
      <section className="subjects shell" id="subjects">
        <div className="section-heading"><div><span>EXPLORE SUBJECTS</span><h2>Choose your learning path</h2></div><button className="text-btn" onClick={() => navigate('/login')}>View all subjects <ChevronRight size={16} /></button></div>
        <div className="subject-grid">{subjectCatalog.map((subject, i) => <article className="subject-card" key={subject.fullName}><div className={`subject-icon tone-${i % 4}`}><Code2 size={24} /></div><h3>{subject.name}</h3><p>{subject.description}</p><button onClick={openPractice}>Start practice <ChevronRight size={15} /></button></article>)}</div>
      </section>
      <section className="competitions-section" id="competitions"><div className="shell">
        <div className="section-heading"><div><span>LIVE CHALLENGES</span><h2>Upcoming competitions</h2></div></div>
        {upcomingCompetitions.length ? <div className="competition-grid">{upcomingCompetitions.slice(0, 6).map(c => <article className="competition-card" key={c._id}><div className={`status ${c.status?.toLowerCase()}`}>{c.status}</div><h3>{c.title}</h3><p>{c.subject?.name}</p><div className="competition-meta"><span>{new Date(c.startAt).toLocaleString([], { dateStyle: 'medium', timeStyle: 'short' })}</span><span>{c.durationMinutes} min</span></div><button onClick={() => navigate(localStorage.getItem('quizarena_token') ? '/competitions' : '/login')}>View competition</button></article>)}</div> : <div className="empty-card">No upcoming competitions are currently published. Check back after an administrator schedules a new competition.</div>}
      </div></section>
      <section className="leaderboard-preview shell" id="leaderboard">
        <div className="section-heading"><div><span>TOP PERFORMERS</span><h2>Leaderboard preview</h2></div><button className="text-btn" onClick={() => navigate(localStorage.getItem('quizarena_token') ? '/leaderboard' : '/login')}>View leaderboard <ChevronRight size={16} /></button></div>
        {isDemoLeaderboard && <p className="data-note">Demo preview — live rankings appear after submitted competition results are available.</p>}
        <div className="leaderboard-preview-grid">{leaderboard.slice(0, 5).map((entry, index) => <article className="leaderboard-preview-card" key={entry.id || entry.name}><div className={`rank-badge rank-${index + 1}`}>{index + 1}</div><div><h3>{entry.name}</h3><p>{entry.competitions || 0} competitions</p></div><strong>{entry.score || entry.points || 0} pts</strong></article>)}</div>
      </section>
      <section className="how-section" id="how-it-works"><div className="shell">
        <div className="section-heading centered"><div><span>GET STARTED</span><h2>How it works</h2></div></div>
        <div className="how-grid">
          {[['1', 'Create an account', 'Register and sign in to build your learner profile.'], ['2', 'Choose a subject', 'Explore subjects and practice at your own pace.'], ['3', 'Join a competition', 'Use the published competition code to participate.'], ['4', 'Complete the timed quiz', 'Answer questions and submit before the deadline.'], ['5', 'Review and improve', 'Check results, accuracy, and leaderboard progress.']].map(([number, title, copy]) => <article className="how-card" key={number}><span>{number}</span><h3>{title}</h3><p>{copy}</p></article>)}
        </div>
      </div></section>
      <section className="team-section shell" id="team">
        <div className="section-heading"><div><span>PROJECT TEAM</span><h2>Team QuizArena</h2></div></div>
        <div className="team-grid">{team.map(([name, role], index) => <article className="team-card" key={name}><div className="team-avatar">{name.split(' ').map(part => part[0]).slice(0, 2).join('')}</div><h3>{name}</h3><p>{role}</p><span>Team member {index + 1}</span></article>)}</div>
      </section>
      <footer className="footer">
        <div className="shell footer-inner">
          <div className="footer-brand"><div className="brand"><span className="brand-mark"><BrainCircuit size={22} /></span>QuizArena</div><p>Learn. Compete. Achieve.</p></div>
          <div className="footer-links"><h3>Explore</h3><a href="#subjects">Subjects</a><a href="#competitions">Competitions</a><a href="#leaderboard">Leaderboard</a><a href="#how-it-works">How it works</a></div>
          <div className="footer-links"><h3>Account</h3><button onClick={() => navigate('/login')}>Login</button><button onClick={() => navigate('/register')}>Register</button><a href="#team">Team QuizArena</a></div>
          <div className="footer-links"><h3>Project</h3><p>Software Engineering Mini Project</p><p>React · Express · MongoDB</p></div>
        </div>
        <div className="shell footer-bottom"><span>© 2026 QuizArena</span><span>Designed and developed by Team QuizArena</span></div>
      </footer>
    </div>
  );
}

function AuthPage({ mode }) {
  const navigate = useNavigate();
  const { token: resetToken } = useParams();
  const [form, setForm] = useState({ name: '', email: '', password: '', confirmPassword: '' });
  const [loading, setLoading] = useState(false);
  const submit = async e => {
    e.preventEvent?.();
    e.preventDefault();
    setLoading(true);
    try {
      if (DEMO_MODE && mode === 'login') {
        const demo = getDemoState();
        const user = demo.users.find(item => item.email.toLowerCase() === form.email.toLowerCase());
        const valid = user && ((user.role === 'admin' && form.password === 'Admin@123') || (user.role === 'student' && form.password === 'Student@123'));
        if (!valid) throw { response: { data: { message: 'Use the demo credentials shown below.' } } };
        localStorage.setItem('quizarena_token', 'demo-token');
        localStorage.setItem('quizarena_user', JSON.stringify({ id: user.id, name: user.name, email: user.email, role: user.role, points: user.points }));
        toast.success(`Welcome back, ${user.name}`);
        navigate(user.role === 'admin' ? '/admin' : '/dashboard');
      } else if (DEMO_MODE && mode === 'register') {
        if (!form.name || !form.email || !form.password || form.password !== form.confirmPassword) throw { response: { data: { message: 'Complete all fields and ensure passwords match.' } } };
        if (form.password.length < 8) throw { response: { data: { message: 'Password must be at least 8 characters.' } } };
        const demo = getDemoState();
        if (demo.users.some(user => user.email.toLowerCase() === form.email.toLowerCase())) throw { response: { data: { message: 'Email is already registered.' } } };
        demo.users.push({ id: `demo-${Date.now()}`, name: form.name, email: form.email.toLowerCase(), role: 'student', active: true, points: 0, xp: 0, createdAt: new Date().toISOString() });
        saveDemoState(demo);
        toast.success('Account created successfully');
        navigate('/login');
      } else if (DEMO_MODE && mode === 'forgot') {
        toast.success('Demo reset complete. Use the demo password shown on the login page.');
      } else if (DEMO_MODE && mode === 'reset') {
        toast.success('Demo password reset complete.');
        navigate('/login');
      } else if (mode === 'register') {
        await api.post('/auth/register', form);
        toast.success('Account created successfully');
        navigate('/login');
      } else if (mode === 'forgot') {
        const { data } = await api.post('/auth/forgot-password', { email: form.email });
        if (data.resetToken) toast.success(`Development reset token: ${data.resetToken}`);
        else toast.success(data.message);
      } else if (mode === 'reset') {
        await api.post('/auth/reset-password', { token: resetToken, password: form.password });
        toast.success('Password reset successfully');
        navigate('/login');
      } else {
        const { data } = await api.post('/auth/login', form);
        localStorage.setItem('quizarena_token', data.token);
        localStorage.setItem('quizarena_user', JSON.stringify(data.user));
        toast.success(`Welcome back, ${data.user.name}`);
        navigate(data.user.role === 'admin' ? '/admin' : '/dashboard');
      }
    } catch (error) { toast.error(error.response?.data?.message || 'Unable to continue'); }
    finally { setLoading(false); }
  };
  return (
    <main className="auth-page"><div className="auth-aside"><button className="brand light-brand" onClick={() => navigate('/')}><span className="brand-mark"><BrainCircuit size={22} /></span>QuizArena</button><div><span>LEARN. COMPETE. ACHIEVE.</span><h1>Make every question count.</h1><p>Join competitions, sharpen your skills, and see how far you can go.</p></div></div>
      <div className="auth-form-wrap"><form className="auth-form" onSubmit={submit}><div className="auth-heading"><h2>{mode === 'login' ? 'Welcome back' : mode === 'register' ? 'Create your account' : mode === 'forgot' ? 'Forgot password' : 'Reset password'}</h2><p>{mode === 'login' ? 'Enter your details to continue.' : mode === 'register' ? 'Start your QuizArena journey today.' : mode === 'forgot' ? 'Enter your account email.' : 'Choose a new password.'}</p></div>
        {DEMO_MODE && mode === 'login' && <div className="demo-login-note"><strong>Demo credentials</strong><span>Student: student@quizarena.com / Student@123</span><span>Admin: admin@quizarena.com / Admin@123</span></div>}
        {mode === 'register' && <label>Full name<input required value={form.name} onChange={e => setForm({...form, name: e.target.value})} placeholder="Your full name" /></label>}
        {(mode === 'login' || mode === 'register' || mode === 'forgot') && <label>Email address<input required type="email" value={form.email} onChange={e => setForm({...form, email: e.target.value})} placeholder="you@example.com" /></label>}
        {(mode === 'login' || mode === 'register' || mode === 'reset') && <label>Password<input required type="password" value={form.password} onChange={e => setForm({...form, password: e.target.value})} placeholder="At least 8 characters" /></label>}
        {mode === 'register' && <label>Confirm password<input required type="password" value={form.confirmPassword} onChange={e => setForm({...form, confirmPassword: e.target.value})} placeholder="Repeat your password" /></label>}
        <button className="btn-primary auth-submit" disabled={loading}>{loading ? 'Please wait...' : mode === 'login' ? 'Sign in' : mode === 'register' ? 'Create account' : mode === 'forgot' ? 'Request reset' : 'Reset password'}</button>
        {mode === 'login' && <p className="auth-switch"><button type="button" onClick={() => navigate('/forgot-password')}>Forgot password?</button></p>}
        <p className="auth-switch">{mode === 'login' ? 'New to QuizArena?' : 'Already have an account?'} <button type="button" onClick={() => navigate(mode === 'login' ? '/register' : '/login')}>{mode === 'login' ? 'Create account' : 'Sign in'}</button></p>
      </form></div>
    </main>
  );
}

function Dashboard({ theme, setTheme, page = 'dashboard' }) {
  const navigate = useNavigate();
  const [mobileOpen, setMobileOpen] = useState(false);
  const [data, setData] = useState({});
  const [loading, setLoading] = useState(true);
  const user = JSON.parse(localStorage.getItem('quizarena_user') || '{}');
  const isAdmin = user.role === 'admin';

  useEffect(() => {
    if (!localStorage.getItem('quizarena_token')) {
      navigate('/login');
      return;
    }
    if (page.startsWith('admin') && !isAdmin) {
      navigate('/dashboard');
      return;
    }
    if (DEMO_MODE) {
      const demo = getDemoState();
      if (page === 'competitions') setData({ competitions: demo.competitions });
      else if (page === 'practice') setData({ questions: demo.questions });
      else if (page === 'leaderboard') setData({ leaderboard: demo.users.filter(user => user.role === 'student').map((user, index) => ({ rank: index + 1, id: user.id, name: user.name, points: user.points, competitions: 0, averageScore: 75 })) });
      else if (page === 'results') setData({ results: demo.attempts });
      else if (page === 'admin') setData({ students: demo.users.filter(user => user.role === 'student').length, subjects: demo.subjects.length, questions: demo.questions.length, competitions: demo.competitions.length, attempts: demo.attempts.length });
      else if (page === 'admin-users') setData({ users: demo.users });
      else if (page === 'admin-questions') setData({ questions: demo.questions });
      else if (page === 'admin-competitions') setData({ competitions: demo.competitions });
      else if (page === 'admin-subjects') setData({ subjects: demo.subjects });
      else if (page === 'admin-learning') setData({ materials: demo.materials });
      else if (page === 'admin-announcements') setData({ announcements: demo.announcements });
      else if (page === 'learning') setData({ materials: demo.materials });
      else if (page === 'certificates') setData({ certificates: demo.certificates });
      else setData({
        stats: { attempted: demo.attempts.length, averageScore: 75, points: user.points || 240, accuracy: 75, xp: 340, level: 2, rank: 1, competitions: demo.attempts.length },
        recentAttempts: demo.attempts,
        upcomingCompetitions: demo.competitions.filter(item => item.status !== 'Completed')
      });
      setLoading(false);
      return;
    }
    setLoading(true);
    const endpoint = page === 'admin'
      ? '/admin/stats'
      : page === 'admin-users'
        ? '/admin/users'
        : page === 'admin-subjects'
          ? '/subjects'
        : page === 'admin-learning'
          ? '/admin/learning'
        : page === 'admin-announcements'
          ? '/admin/announcements'
      : page === 'admin-competitions'
        ? '/admin/competitions'
      : page === 'admin-participants'
        ? `/admin/competitions/${window.location.pathname.split('/')[3]}/participants`
        : page === 'admin-questions'
          ? '/admin/questions'
          : page === 'admin-results'
            ? '/results'
      : page === 'competitions'
        ? '/competitions'
        : page === 'learning'
          ? '/learning'
        : page === 'daily'
          ? '/daily-challenge'
        : page === 'certificates'
          ? '/certificates'
        : page === 'practice'
          ? '/practice/questions'
          : page === 'leaderboard'
            ? '/leaderboard'
            : page === 'results'
              ? '/results'
              : '/dashboard';
    api.get(endpoint, { headers: tokenHeaders() }).then(r => setData(r.data)).catch(() => toast.error('Unable to load data')).finally(() => setLoading(false));
  }, [page, isAdmin, navigate]);

  const navItems = isAdmin
    ? [['Dashboard', 'admin', LayoutDashboard], ['User Management', 'admin-users', Users], ['Subjects', 'admin-subjects', Database], ['Questions', 'admin-questions', BrainCircuit], ['Competitions', 'admin-competitions', Trophy], ['Learning Content', 'admin-learning', BookOpen], ['Announcements', 'admin-announcements', Bell], ['Results', 'admin-results', BarChart3]]
    : [['Dashboard', 'dashboard', LayoutDashboard], ['Competitions', 'competitions', Trophy], ['Practice', 'practice', BrainCircuit], ['Learn', 'learn', BookOpen], ['Daily Challenge', 'daily-challenge', Activity], ['Leaderboard', 'leaderboard', Crown], ['My Results', 'results', BarChart3], ['Certificates', 'certificates', Award], ['Profile', 'profile', Settings]];

  const logout = () => {
    localStorage.removeItem('quizarena_token');
    localStorage.removeItem('quizarena_user');
    navigate('/');
  };

  return (
    <div className="app-shell">
      <aside className={`sidebar ${mobileOpen ? 'open' : ''}`}>
        <button className="brand sidebar-brand" onClick={() => navigate(isAdmin ? '/admin' : '/dashboard')}><span className="brand-mark"><BrainCircuit size={22} /></span>QuizArena</button>
        <button className="close-sidebar" onClick={() => setMobileOpen(false)}><X size={20} /></button>
        <nav>
          {navItems.map(([label, target, Icon]) => <button key={label} className={page === target || (page === 'dashboard' && target === 'dashboard') ? 'active' : ''} onClick={() => {
            if (isAdmin) navigate(target === 'admin' ? '/admin' : `/admin/${target.replace('admin-', '')}`);
            else navigate(target === 'dashboard' ? '/dashboard' : `/${target}`);
            setMobileOpen(false);
          }}><Icon size={18} /><span>{label}</span></button>)}
        </nav>
        <button className="logout" onClick={logout}><LogOut size={18} /><span>Logout</span></button>
      </aside>
      <main className="dashboard-main">
        <header className="dashboard-header">
          <button className="menu-btn" onClick={() => setMobileOpen(true)}><Menu size={20} /></button>
          <div className="header-search"><input placeholder="Search..." /></div>
          <div className="header-actions"><button className="icon-btn" onClick={() => setTheme(theme === 'light' ? 'dark' : 'light')}>{theme === 'light' ? <Moon size={18} /> : <Sun size={18} />}</button><button className="icon-btn notification"><Bell size={18} /><i /></button><div className="avatar">{user.name?.[0] || 'U'}</div></div>
        </header>
        <div className="dashboard-content">
          {loading ? <div className="loader">Loading...</div> : (
            isAdmin && page === 'admin' ? <AdminOverview data={data} /> :
            isAdmin && page === 'admin-competitions' ? <AdminCompetitions /> :
            isAdmin && page === 'admin-participants' ? <AdminParticipants data={data} /> :
            isAdmin && page === 'admin-questions' ? <AdminQuestions /> :
            isAdmin && page === 'admin-results' ? <Results data={data.results || []} /> :
            isAdmin && page === 'admin-users' ? <AdminUsers data={data.users || []} /> :
            isAdmin && page === 'admin-subjects' ? <AdminSubjects data={data.subjects || []} /> :
            isAdmin && page === 'admin-learning' ? <AdminLearning data={data.materials || []} /> :
            isAdmin && page === 'admin-announcements' ? <AdminAnnouncements data={data.announcements || []} /> :
            page === 'competitions' ? <Competitions data={data.competitions || []} /> :
            page === 'learning' ? <Learning data={data.materials || []} /> :
            page === 'daily' ? <DailyChallenge /> :
            page === 'certificates' ? <Certificates data={data.certificates || []} /> :
            page === 'practice' ? <Practice data={data.questions || []} /> :
            page === 'leaderboard' ? <Leaderboard data={data.leaderboard || []} /> :
            page === 'results' ? <Results data={data.results || []} /> :
            page === 'profile' ? <Profile user={user} /> :
            <StudentDashboard data={data} user={user} navigate={navigate} />
          )}
        </div>
      </main>
    </div>
  );
}

function StudentDashboard({ data, user, navigate }) {
  const stats = data.stats || {};
  const cards = [
    ['Quizzes attempted', stats.attempted || 0, Trophy, 'blue'],
    ['Average score', `${stats.averageScore || 0}%`, BarChart3, 'violet'],
    ['Points earned', stats.points || 0, Award, 'amber'],
    ['Overall accuracy', `${stats.accuracy || 0}%`, Activity, 'green']
  ];
  const chartData = [
    { name: 'Mon', score: 42 }, { name: 'Tue', score: 55 }, { name: 'Wed', score: 48 },
    { name: 'Thu', score: 67 }, { name: 'Fri', score: 63 }, { name: 'Sat', score: 78 }, { name: 'Sun', score: 84 }
  ];
  return (
    <div>
      <div className="page-heading"><div><span>STUDENT DASHBOARD</span><h1>Welcome back, {user.name?.split(' ')[0] || 'Student'} 👋</h1><p>Here is how your learning journey is progressing.</p></div><button className="btn-primary" onClick={() => navigate('/competitions')}>Join a competition</button></div>
      <div className="stat-grid">{cards.map(([label, value, Icon, tone]) => <article className="stat-card" key={label}><div className={`stat-icon ${tone}`}><Icon size={21} /></div><div><span>{label}</span><strong>{value}</strong></div></article>)}</div>
      <div className="dashboard-grid"><section className="panel performance"><div className="panel-head"><div><span>PERFORMANCE</span><h3>Weekly progress</h3></div><select><option>This week</option></select></div><div className="chart"><ResponsiveContainer width="100%" height="100%"><AreaChart data={chartData}><defs><linearGradient id="chartFill" x1="0" y1="0" x2="0" y2="1"><stop offset="5%" stopColor="#5b5fe9" stopOpacity={0.25}/><stop offset="95%" stopColor="#5b5fe9" stopOpacity={0}/></linearGradient></defs><XAxis dataKey="name" axisLine={false} tickLine={false}/><YAxis axisLine={false} tickLine={false}/><Tooltip/><Area type="monotone" dataKey="score" stroke="#5b5fe9" strokeWidth={3} fill="url(#chartFill)"/></AreaChart></ResponsiveContainer></div></section>
      <section className="panel upcoming"><div className="panel-head"><div><span>COMPETITIONS</span><h3>Upcoming</h3></div></div>{(data.upcomingCompetitions || []).slice(0, 4).map(c => <div className="upcoming-row" key={c._id}><div className="calendar"><strong>{new Date(c.startAt).getDate()}</strong><span>{new Date(c.startAt).toLocaleString('en', { month: 'short' })}</span></div><div><h4>{c.title}</h4><p>{c.subject?.name} · {c.durationMinutes} min</p></div><button onClick={() => navigate('/competitions')}><ChevronRight size={16}/></button></div>)}</section></div>
    </div>
  );
}

function Competitions({ data }) {
  const navigate = useNavigate();
  const [testCode, setTestCode] = useState('');
  const join = async (competition, submittedCode) => {
    const code = submittedCode ?? window.prompt(`Enter the code for ${competition.title}`);
    if (!code) return;
    try {
      await api.post(`/competitions/${competition._id}/join`, { code }, { headers: tokenHeaders() });
      toast.success('Competition joined');
      if (competition.status === 'Live') navigate(`/quiz/${competition._id}`);
    } catch (error) { toast.error(error.response?.data?.message || 'Unable to join'); }
  };
  const joinByCode = async event => {
    event.preventDefault();
    try {
      const { data: response } = await api.post('/competitions/join-by-code', { code: testCode }, { headers: tokenHeaders() });
      setTestCode('');
      toast.success('Competition joined');
      if (response.competition?.status === 'Live') navigate(`/quiz/${response.competition.id}`);
    } catch (error) { toast.error(error.response?.data?.message || 'Unable to join test'); }
  };
  return <div><div className="page-heading"><div><span>CHALLENGES</span><h1>Competitions</h1><p>Enter the test code shared by your administrator to join a timed quiz.</p></div></div>
    <form className="panel test-code-form" onSubmit={joinByCode}><label htmlFor="test-code">Test code</label><div><input id="test-code" value={testCode} onChange={event => setTestCode(event.target.value.toUpperCase())} placeholder="Enter your test code" autoComplete="off" required /><button className="btn-primary" type="submit">Join test</button></div></form>
    <div className="competition-grid large">{data.map(c => <article className="competition-card" key={c._id}><div className={`status ${c.status?.toLowerCase()}`}>{c.status}</div><h3>{c.title}</h3><p>{c.description}</p><div className="competition-meta"><span>{c.subject?.name}</span><span>{c.durationMinutes} min</span><span>{c.participantCount || 0} joined</span></div><button className="btn-secondary" onClick={() => join(c)}>Enter test code</button></article>)}</div></div>;
}

function Learning({ data }) {
  const [search,setSearch]=useState('');
  const visible=data.filter(item=>`${item.title} ${item.topic} ${item.subject?.name}`.toLowerCase().includes(search.toLowerCase()));
  return <div><div className="page-heading"><div><span>LEARNING CENTER</span><h1>Study materials</h1><p>Read subject notes before attempting competitions.</p></div></div><input className="admin-search" value={search} onChange={e=>setSearch(e.target.value)} placeholder="Search learning material" /><div className="learning-grid">{visible.map(item=><article className="panel learning-card" key={item._id}><span className="role-badge">{item.subject?.name}</span><h3>{item.title}</h3><p className="learning-topic">{item.topic}</p><p>{item.content}</p></article>)}</div>{!visible.length&&<div className="empty-card">No learning materials match this search.</div>}</div>;
}

function DailyChallenge() {
  const [challenge,setChallenge]=useState(null);
  const [attempted,setAttempted]=useState(false);
  const [result,setResult]=useState(null);
  const [answers,setAnswers]=useState({});
  const [index,setIndex]=useState(0);
  useEffect(()=>{api.get('/daily-challenge',{headers:tokenHeaders()}).then(r=>{setChallenge(r.data.challenge);setAttempted(r.data.attempted);setResult(r.data.result);}).catch(error=>toast.error(error.response?.data?.message||'Unable to load daily challenge'));},[]);
  const submit=async()=>{try{const payload={answers:Object.entries(answers).map(([question,selectedOption])=>({question,selectedOption:Number(selectedOption)}))};const r=await api.post(`/daily-challenge/${challenge.id}/submit`,payload,{headers:tokenHeaders()});setResult(r.data.result);setAttempted(true);toast.success('Daily challenge submitted');}catch(error){toast.error(error.response?.data?.message||'Unable to submit challenge');}};
  if(!challenge)return <div className="empty-card">No daily challenge is available. Add questions to the question bank first.</div>;
  if(attempted)return <div><div className="page-heading"><div><span>DAILY CHALLENGE</span><h1>Today's result</h1></div></div><div className="panel result-summary"><article><strong>{result?.score||0}</strong><span>Score</span></article><article><strong>{result?.correct||0}</strong><span>Correct</span></article><article><strong>{result?.xpAwarded||0}</strong><span>XP awarded</span></article></div></div>;
  const question=challenge.questions[index];
  return <div><div className="page-heading"><div><span>DAILY CHALLENGE</span><h1>One attempt per day</h1></div></div><div className="practice-layout"><article className="practice-card"><div className="quiz-progress"><span>Question {index+1} of {challenge.questions.length}</span><span>{challenge.durationMinutes} min</span></div><h2>{question.statement}</h2><div className="option-list">{question.options.map((option,optionIndex)=><button key={optionIndex} className={answers[question._id]===optionIndex?'selected':''} onClick={()=>setAnswers({...answers,[question._id]:optionIndex})}>{String.fromCharCode(65+optionIndex)}. {option.text}</button>)}</div><div className="practice-actions"><button className="btn-secondary" disabled={index===0} onClick={()=>setIndex(index-1)}>Previous</button>{index<challenge.questions.length-1?<button className="btn-primary" onClick={()=>setIndex(index+1)}>Next</button>:<button className="btn-primary" onClick={submit}>Submit challenge</button>}</div></article></div></div>;
}

function Certificates({ data }) {
  const verify=async()=>{const id=window.prompt('Enter certificate ID');if(!id)return;try{const r=await api.get(`/certificates/verify/${encodeURIComponent(id)}`);toast.success(`${r.data.studentName} — ${r.data.competitionName}`);}catch(error){toast.error(error.response?.data?.message||'Certificate not found');}};
  const download=async certificate=>{
    try {
      const { jsPDF }=await import('jspdf');
      const doc=new jsPDF({orientation:'landscape'});
      doc.setFillColor(17,24,59);doc.rect(0,0,297,210,'F');
      doc.setTextColor(255,255,255);doc.setFontSize(28);doc.text('QuizArena',148.5,35,{align:'center'});
      doc.setFontSize(16);doc.text('Certificate of Achievement',148.5,55,{align:'center'});
      doc.setFontSize(13);doc.text(certificate.achievement||'Achievement',148.5,75,{align:'center'});
      doc.setFontSize(20);doc.text(certificate.user?.name||'QuizArena Student',148.5,98,{align:'center'});
      doc.setFontSize(12);doc.text(`Competition: ${certificate.competition?.title||'Quiz Competition'}`,148.5,115,{align:'center'});
      doc.text(`Certificate ID: ${certificate.certificateId}`,148.5,130,{align:'center'});
      doc.text(`Issued: ${new Date(certificate.issuedAt).toLocaleDateString()}`,148.5,145,{align:'center'});
      doc.save(`QuizArena-${certificate.certificateId}.pdf`);
    } catch {toast.error('Unable to generate certificate PDF');}
  };
  return <div><div className="page-heading"><div><span>CERTIFICATES</span><h1>Achievements</h1><button className="btn-secondary" onClick={verify}>Verify certificate</button></div></div>{data.length?<div className="result-grid">{data.map(certificate=><article className="result-card" key={certificate._id}><Award size={36}/><div><h3>{certificate.competition?.title}</h3><p>{certificate.achievement}</p><strong>{certificate.certificateId}</strong><button className="btn-primary" onClick={()=>download(certificate)}>Download PDF</button></div></article>)}</div>:<div className="empty-card">No eligible certificates have been issued yet.</div>}</div>;
}

function Practice({ data }) {
  const [answer, setAnswer] = useState(null);
  const [index, setIndex] = useState(0);
  const question = data[index];
  if (!question) return <div className="empty-card">No practice questions available. Seed the database or ask an administrator to add questions.</div>;
  return <div><div className="page-heading"><div><span>PRACTICE MODE</span><h1>Learn at your own pace</h1><p>Instant feedback and explanations, no timer.</p></div></div><div className="practice-layout"><article className="practice-card"><div className="quiz-progress"><span>Question {index + 1} of {data.length}</span><span>{question.difficulty}</span></div><h2>{question.statement}</h2><div className="option-list">{question.options.map((option, i) => <button key={i} className={answer === i ? 'selected' : ''} onClick={() => setAnswer(i)}>{String.fromCharCode(65 + i)}. {option.text}</button>)}</div>{answer !== null && <div className="feedback"><strong>{question.options[answer]?.isCorrect ? 'Correct!' : 'Not quite.'}</strong><p>{question.explanation}</p></div>}<div className="practice-actions"><button className="btn-secondary" disabled={index === 0} onClick={() => { setIndex(index - 1); setAnswer(null); }}>Previous</button><button className="btn-primary" onClick={() => { setIndex((index + 1) % data.length); setAnswer(null); }}>Next question</button></div></article></div></div>;
}

function Leaderboard({ data }) {
  return <div><div className="page-heading"><div><span>GLOBAL RANKING</span><h1>Leaderboard</h1><p>Ranked by points and learning performance.</p></div></div><div className="panel leaderboard-table"><table><thead><tr><th>Rank</th><th>Student</th><th>Points</th><th>Competitions</th><th>Average</th></tr></thead><tbody>{data.map(row => <tr key={row.id}><td><b>{row.rank}</b></td><td><div className="student-cell"><span>{row.name?.[0]}</span>{row.name}</div></td><td>{row.points}</td><td>{row.competitions}</td><td>{row.averageScore}%</td></tr>)}</tbody></table></div></div>;
}

function Results({ data }) {
  return <div><div className="page-heading"><div><span>MY PERFORMANCE</span><h1>Quiz results</h1><p>Your submitted competition attempts.</p></div></div>{data.length ? <div className="result-grid">{data.map(r => <article className="result-card" key={r._id}><div className="result-ring">{r.accuracy || 0}%</div><div><h3>{r.competition?.title || 'Competition'}</h3><p>{r.correct} correct · {r.incorrect} incorrect · {r.unattempted} unattempted</p><strong>{r.score} / {r.maxScore} marks</strong></div></article>)}</div> : <div className="empty-card">You have not submitted a quiz yet.</div>}</div>;
}

function Profile({ user }) {
  const [name,setName]=useState(user.name||'');
  const [avatar,setAvatar]=useState('');
  const [currentPassword,setCurrentPassword]=useState('');
  const [newPassword,setNewPassword]=useState('');
  const saveProfile=async event=>{event.preventDefault();try{const r=await api.put('/auth/profile',{name,avatar},{headers:tokenHeaders()});localStorage.setItem('quizarena_user',JSON.stringify(r.data.user));toast.success('Profile updated');}catch(error){toast.error(error.response?.data?.message||'Unable to update profile');}};
  const changePassword=async event=>{event.preventDefault();try{await api.put('/auth/change-password',{currentPassword,newPassword},{headers:tokenHeaders()});toast.success('Password changed');setCurrentPassword('');setNewPassword('');}catch(error){toast.error(error.response?.data?.message||'Unable to change password');}};
  return <div><div className="page-heading"><div><span>ACCOUNT</span><h1>My profile</h1></div></div><div className="profile-layout"><form className="panel profile-card" onSubmit={saveProfile}><div className="profile-avatar">{name?.[0]||'U'}</div><div className="profile-fields"><label>Student name<input required value={name} onChange={e=>setName(e.target.value)}/></label><label>Email<input value={user.email||''} disabled/></label><label>Avatar URL<input value={avatar} onChange={e=>setAvatar(e.target.value)} placeholder="Optional image URL"/></label><button className="btn-primary">Save profile</button></div></form><form className="panel profile-card" onSubmit={changePassword}><div><h3>Change password</h3><label>Current password<input required type="password" value={currentPassword} onChange={e=>setCurrentPassword(e.target.value)}/></label><label>New password<input required type="password" value={newPassword} onChange={e=>setNewPassword(e.target.value)}/></label><button className="btn-primary">Change password</button></div></form></div></div>;
}

function AdminParticipants({ data }) {
  const [search,setSearch]=useState('');
  const [status,setStatus]=useState('All');
  const visible=(data.participants||[]).filter(participant=>{
    const matchesText=`${participant.name} ${participant.email}`.toLowerCase().includes(search.toLowerCase());
    return matchesText&&(status==='All'||participant.attemptStatus===status);
  });
  const exportCsv=async()=>{
    try{
      const response=await api.get(`/admin/competitions/${window.location.pathname.split('/')[3]}/participants?format=csv`,{headers:tokenHeaders(),responseType:'blob'});
      const url=URL.createObjectURL(response.data);
      const link=document.createElement('a');
      link.href=url;link.download=`${data.competition?.title||'competition'}-participants.csv`;link.click();
      URL.revokeObjectURL(url);
    }catch(error){toast.error(error.response?.data?.message||'Unable to export results');}
  };
  return <div><div className="page-heading"><div><span>COMPETITION PARTICIPANTS</span><h1>{data.competition?.title||'Participants and activity'}</h1></div></div><div className="participant-toolbar"><input className="admin-search" value={search} onChange={e=>setSearch(e.target.value)} placeholder="Search participants"/><select value={status} onChange={e=>setStatus(e.target.value)}><option>All</option><option>submitted</option><option>in-progress</option><option>Not started</option></select><button className="btn-secondary" onClick={exportCsv}>Export CSV</button></div><div className="panel admin-table"><table><thead><tr><th>Participant</th><th>Attempt status</th><th>Score</th><th>Submitted</th></tr></thead><tbody>{visible.map(participant=><tr key={participant.id}><td><strong>{participant.name}</strong><small>{participant.email}</small></td><td>{participant.attemptStatus}</td><td>{participant.score===null?'—':`${participant.score} / ${participant.maxScore}`}</td><td>{participant.submittedAt?new Date(participant.submittedAt).toLocaleString():'—'}</td></tr>)}</tbody></table></div></div>;
}

function AdminUsers({ data: initialUsers }) {
  const [users, setUsers] = useState(initialUsers);
  const [search, setSearch] = useState('');
  const load = () => api.get('/admin/users', { headers: tokenHeaders() }).then(r => setUsers(r.data.users || [])).catch(error => toast.error(error.response?.data?.message || 'Unable to load users'));
  const toggle = async user => {
    try { await api.put(`/admin/users/${user._id}/status`, { active: !user.active }, { headers: tokenHeaders() }); toast.success(user.active ? 'Account deactivated' : 'Account activated'); load(); }
    catch (error) { toast.error(error.response?.data?.message || 'Unable to update account'); }
  };
  const visible = users.filter(user => `${user.name} ${user.email}`.toLowerCase().includes(search.toLowerCase()));
  return <div><div className="page-heading"><div><span>USER MANAGEMENT</span><h1>Registered users</h1><p>Search accounts and manage activation status.</p></div></div><div className="panel admin-table"><input className="admin-search" value={search} onChange={e => setSearch(e.target.value)} placeholder="Search name or email" /><table><thead><tr><th>Student</th><th>Role</th><th>XP</th><th>Status</th><th>Action</th></tr></thead><tbody>{visible.map(user => <tr key={user._id}><td><strong>{user.name}</strong><small>{user.email}</small></td><td>{user.role}</td><td>{user.xp || 0}</td><td>{user.active ? 'Active' : 'Deactivated'}</td><td><button className="btn-secondary" onClick={() => toggle(user)}>{user.active ? 'Deactivate' : 'Activate'}</button></td></tr>)}</tbody></table></div></div>;
}

function AdminSubjects({ data: initialSubjects }) {
  const [subjects, setSubjects] = useState(initialSubjects);
  const [form, setForm] = useState({ name: '', description: '', topics: '', active: true });
  const [search,setSearch]=useState('');
  const [editingId,setEditingId]=useState(null);
  const load = () => api.get('/subjects', { headers: tokenHeaders() }).then(r => setSubjects(r.data.subjects||[])).catch(error => toast.error(error.response?.data?.message || 'Unable to load subjects'));
  const save = async event => {
    event.preventDefault();
    const payload={...form,topics:form.topics.split(',').map(topic=>topic.trim()).filter(Boolean)};
    try {
      if(editingId) await api.put(`/admin/subjects/${editingId}`,payload,{headers:tokenHeaders()});
      else await api.post('/admin/subjects',payload,{headers:tokenHeaders()});
      toast.success(editingId?'Subject updated':'Subject created');
      setEditingId(null);setForm({name:'',description:'',topics:'',active:true});load();
    }
    catch (error) { toast.error(error.response?.data?.message || 'Unable to create subject'); }
  };
  const edit=subject=>{setEditingId(subject._id);setForm({name:subject.name,description:subject.description||'',topics:(subject.topics||[]).join(', '),active:subject.active!==false});};
  const archive=async subject=>{try{await api.put(`/admin/subjects/${subject._id}`,{active:!subject.active},{headers:tokenHeaders()});toast.success(subject.active?'Subject archived':'Subject restored');load();}catch(error){toast.error(error.response?.data?.message||'Unable to update subject');}};
  const remove = async subject => {
    if (!window.confirm(`Delete "${subject.name}"?`)) return;
    try { await api.delete(`/admin/subjects/${subject._id}`, { headers: tokenHeaders() }); toast.success('Subject deleted'); load(); }
    catch (error) { toast.error(error.response?.data?.message || 'Unable to delete subject'); }
  };
  const visible=subjects.filter(subject=>subject.name.toLowerCase().includes(search.toLowerCase()));
  return <div><div className="page-heading"><div><span>SUBJECT MANAGEMENT</span><h1>Subjects and topics</h1></div></div><div className="admin-form-layout"><form className="panel admin-form" onSubmit={save}><h3>{editingId?'Edit subject':'Create subject'}</h3><label>Name<input required value={form.name} onChange={e => setForm({...form,name:e.target.value})} /></label><label>Description<textarea value={form.description} onChange={e => setForm({...form,description:e.target.value})} /></label><label>Topics (comma separated)<input value={form.topics} onChange={e => setForm({...form,topics:e.target.value})} /></label><div className="admin-form-actions">{editingId&&<button type="button" className="btn-secondary" onClick={()=>{setEditingId(null);setForm({name:'',description:'',topics:'',active:true});}}>Cancel</button>}<button className="btn-primary">{editingId?'Save subject':'Create subject'}</button></div></form><div className="panel admin-list"><div className="admin-list-toolbar"><h3>Subjects</h3><input className="admin-search" value={search} onChange={e=>setSearch(e.target.value)} placeholder="Search subjects"/></div>{visible.map(subject => <article className="admin-list-row" key={subject._id}><div><h4>{subject.name}</h4><p>{subject.topics?.join(' · ') || subject.description} · {subject.active===false?'Archived':'Active'}</p></div><div><button className="btn-secondary" onClick={()=>edit(subject)}>Edit</button><button className="btn-secondary" onClick={()=>archive(subject)}>{subject.active===false?'Restore':'Archive'}</button><button className="danger-button" onClick={() => remove(subject)}>Delete</button></div></article>)}</div></div></div>;
}

function AdminLearning({ data: initialMaterials }) {
  const [subjects, setSubjects] = useState([]);
  const [materials, setMaterials] = useState(initialMaterials);
  const [form, setForm] = useState({ subject: '', topic: '', title: '', content: '', difficulty: 'Easy', published: true });
  useEffect(() => { api.get('/subjects',{headers:tokenHeaders()}).then(r=>setSubjects(r.data.subjects||[])); },[]);
  const load = () => api.get('/admin/learning',{headers:tokenHeaders()}).then(r=>setMaterials(r.data.materials||[]));
  const save = async event => { event.preventDefault(); try { await api.post('/admin/learning',form,{headers:tokenHeaders()}); toast.success('Learning material created'); setForm({subject:'',topic:'',title:'',content:'',difficulty:'Easy',published:true}); load(); } catch(error){toast.error(error.response?.data?.message||'Unable to save material');}};
  return <div><div className="page-heading"><div><span>LEARNING CONTENT</span><h1>Study materials</h1></div></div><div className="admin-form-layout"><form className="panel admin-form" onSubmit={save}><h3>Add material</h3><label>Subject<select required value={form.subject} onChange={e=>setForm({...form,subject:e.target.value})}><option value="">Select subject</option>{subjects.map(subject=><option key={subject._id} value={subject._id}>{subject.name}</option>)}</select></label><label>Topic<input required value={form.topic} onChange={e=>setForm({...form,topic:e.target.value})}/></label><label>Title<input required value={form.title} onChange={e=>setForm({...form,title:e.target.value})}/></label><label>Content<textarea required value={form.content} onChange={e=>setForm({...form,content:e.target.value})}/></label><button className="btn-primary">Create material</button></form><div className="panel admin-list"><h3>Published materials</h3>{materials.map(material=><article className="admin-list-row" key={material._id}><div><h4>{material.title}</h4><p>{material.subject?.name} · {material.topic}</p></div></article>)}</div></div></div>;
}

function AdminAnnouncements({ data: initialAnnouncements }) {
  const [announcements,setAnnouncements]=useState(initialAnnouncements);
  const [form,setForm]=useState({title:'',message:'',type:'General',published:true});
  const load=()=>api.get('/admin/announcements',{headers:tokenHeaders()}).then(r=>setAnnouncements(r.data.announcements||[]));
  const save=async event=>{event.preventEvent?.();event.preventDefault();try{await api.post('/admin/announcements',form,{headers:tokenHeaders()});toast.success('Announcement created');setForm({title:'',message:'',type:'General',published:true});load();}catch(error){toast.error(error.response?.data?.message||'Unable to save announcement');}};
  const remove=async announcement=>{if(!window.confirm(`Delete "${announcement.title}"?`))return;try{await api.delete(`/admin/announcements/${announcement._id}`,{headers:tokenHeaders()});toast.success('Announcement deleted');load();}catch(error){toast.error(error.response?.data?.message||'Unable to delete announcement');}};
  return <div><div className="page-heading"><div><span>ANNOUNCEMENTS</span><h1>Platform announcements</h1></div></div><div className="admin-form-layout"><form className="panel admin-form" onSubmit={save}><h3>Create announcement</h3><label>Title<input required value={form.title} onChange={e=>setForm({...form,title:e.target.value})}/></label><label>Message<textarea required value={form.message} onChange={e=>setForm({...form,message:e.target.value})}/></label><label>Type<select value={form.type} onChange={e=>setForm({...form,type:e.target.value})}><option>General</option><option>Competition</option><option>Result</option><option>Achievement</option></select></label><button className="btn-primary">Publish announcement</button></form><div className="panel admin-list"><h3>Announcements</h3>{announcements.map(announcement=><article className="admin-list-row" key={announcement._id}><div><h4>{announcement.title}</h4><p>{announcement.message}</p></div><button className="danger-button" onClick={()=>remove(announcement)}>Delete</button></article>)}</div></div></div>;
}

function AdminQuestions() {
  const [subjects, setSubjects] = useState([]);
  const [questions, setQuestions] = useState([]);
  const [editingId, setEditingId] = useState(null);
  const [form, setForm] = useState({
    subject: '', topic: '', statement: '', difficulty: 'Medium',
    explanation: '', marks: 1, negativeMarks: 0,
    options: ['', '', '', ''], correctOption: 0
  });
  const load = () => Promise.all([
    api.get('/subjects', { headers: tokenHeaders() }),
    api.get('/admin/questions', { headers: tokenHeaders() })
  ]).then(([subjectsResponse, questionsResponse]) => {
    setSubjects(subjectsResponse.data.subjects || []);
    setQuestions(questionsResponse.data.questions || []);
  }).catch(error => toast.error(error.response?.data?.message || 'Unable to load questions'));
  useEffect(() => { load(); }, []);
  const reset = () => {
    setEditingId(null);
    setForm({ subject: subjects[0]?._id || '', topic: '', statement: '', difficulty: 'Medium', explanation: '', marks: 1, negativeMarks: 0, options: ['', '', '', ''], correctOption: 0 });
  };
  useEffect(() => {
    if (!form.subject && subjects.length) setForm(current => ({ ...current, subject: subjects[0]._id }));
  }, [subjects]);
  const save = async event => {
    event.preventDefault();
    const payload = {
      ...form,
      options: form.options.map((text, index) => ({ text, isCorrect: index === Number(form.correctOption) }))
    };
    try {
      if (editingId) await api.put(`/admin/questions/${editingId}`, payload, { headers: tokenHeaders() });
      else await api.post('/admin/questions', payload, { headers: tokenHeaders() });
      toast.success(editingId ? 'Question updated' : 'Question created');
      reset();
      load();
    } catch (error) { toast.error(error.response?.data?.message || 'Unable to save question'); }
  };
  const edit = question => {
    setEditingId(question._id);
    const correctOption = question.options.findIndex(option => option.isCorrect);
    setForm({
      subject: question.subject?._id || question.subject,
      topic: question.topic || '',
      statement: question.statement,
      difficulty: question.difficulty,
      explanation: question.explanation || '',
      marks: question.marks,
      negativeMarks: question.negativeMarks,
      options: question.options.map(option => option.text),
      correctOption: correctOption >= 0 ? correctOption : 0
    });
  };
  const remove = async question => {
    if (!window.confirm(`Delete this question?`)) return;
    try {
      await api.delete(`/admin/questions/${question._id}`, { headers: tokenHeaders() });
      toast.success('Question deleted');
      load();
    } catch (error) { toast.error(error.response?.data?.message || 'Unable to delete question'); }
  };
  return <div>
    <div className="page-heading"><div><span>QUESTION MANAGEMENT</span><h1>MCQ questions</h1><p>Create, edit, and delete questions used by competitions.</p></div></div>
    <div className="admin-form-layout">
      <form className="panel admin-form" onSubmit={save}>
        <h3>{editingId ? 'Edit question' : 'Add question'}</h3>
        <label>Subject<select required value={form.subject} onChange={e => setForm({...form, subject: e.target.value})}><option value="">Select subject</option>{subjects.map(subject => <option key={subject._id} value={subject._id}>{subject.name}</option>)}</select></label>
        <label>Topic<input value={form.topic} onChange={e => setForm({...form, topic: e.target.value})} placeholder="Topic name" /></label>
        <label>Question statement<textarea required value={form.statement} onChange={e => setForm({...form, statement: e.target.value})} placeholder="Enter the MCQ question" /></label>
        {form.options.map((option, index) => <label key={index}>Option {String.fromCharCode(65 + index)}<div className="option-editor"><input required value={option} onChange={e => setForm({...form, options: form.options.map((item, optionIndex) => optionIndex === index ? e.target.value : item)})} /><input type="radio" name="correctOption" aria-label={`Mark option ${String.fromCharCode(65 + index)} correct`} checked={Number(form.correctOption) === index} onChange={() => setForm({...form, correctOption: index})} /></div></label>)}
        <div className="admin-form-grid"><label>Difficulty<select value={form.difficulty} onChange={e => setForm({...form, difficulty: e.target.value})}><option>Easy</option><option>Medium</option><option>Hard</option></select></label><label>Marks<input type="number" min="0" value={form.marks} onChange={e => setForm({...form, marks: Number(e.target.value)})} /></label><label>Negative marks<input type="number" min="0" value={form.negativeMarks} onChange={e => setForm({...form, negativeMarks: Number(e.target.value)})} /></label></div>
        <label>Explanation<textarea value={form.explanation} onChange={e => setForm({...form, explanation: e.target.value})} placeholder="Explain the correct answer" /></label>
        <div className="admin-form-actions">{editingId && <button type="button" className="btn-secondary" onClick={reset}>Cancel edit</button>}<button className="btn-primary" type="submit">{editingId ? 'Save changes' : 'Add question'}</button></div>
      </form>
      <div className="panel admin-list"><h3>Question bank</h3>{questions.length ? questions.map(question => <article className="admin-list-row" key={question._id}><div><h4>{question.statement}</h4><p>{question.subject?.name} · {question.difficulty} · {question.marks} mark(s)</p></div><div><button className="btn-secondary" onClick={() => edit(question)}>Edit</button><button className="danger-button" onClick={() => remove(question)}>Delete</button></div></article>) : <div className="empty-card">No questions have been created.</div>}</div>
    </div>
  </div>;
}

function AdminCompetitions() {
  const [subjects, setSubjects] = useState([]);
  const [questions, setQuestions] = useState([]);
  const [competitions, setCompetitions] = useState([]);
  const [editingId, setEditingId] = useState(null);
  const [form, setForm] = useState({
    title: '', subject: '', description: '', startAt: '', endAt: '',
    durationMinutes: 30, difficulty: 'Medium', status: 'Upcoming',
    published: true, negativeMarking: false, randomizeQuestions: false,
    randomizeOptions: false, maxParticipants: 0, totalMarks: 0, questions: []
  });
  const load = () => Promise.all([
    api.get('/subjects', { headers: tokenHeaders() }),
    api.get('/admin/questions', { headers: tokenHeaders() }),
    api.get('/admin/competitions', { headers: tokenHeaders() })
  ]).then(([subjectResponse, questionResponse, competitionResponse]) => {
    setSubjects(subjectResponse.data.subjects || []);
    setQuestions(questionResponse.data.questions || []);
    setCompetitions(competitionResponse.data.competitions || []);
  }).catch(error => toast.error(error.response?.data?.message || 'Unable to load competitions'));
  useEffect(() => { load(); }, []);
  useEffect(() => {
    if (!form.subject && subjects.length) setForm(current => ({ ...current, subject: subjects[0]._id }));
  }, [subjects]);
  const reset = () => {
    setEditingId(null);
    setForm({ title: '', subject: subjects[0]?._id || '', description: '', startAt: '', endAt: '', durationMinutes: 30, difficulty: 'Medium', status: 'Upcoming', published: true, negativeMarking: false, randomizeQuestions: false, randomizeOptions: false, maxParticipants: 0, totalMarks: 0, questions: [] });
  };
  const save = async event => {
    event.preventDefault();
    const selectedQuestions = questions.filter(question => form.questions.includes(question._id));
    const totalMarks = selectedQuestions.reduce((sum, question) => sum + (Number(question.marks) || 0), 0);
    const payload = { ...form, totalMarks: form.totalMarks ? Number(form.totalMarks) : totalMarks };
    try {
      if (editingId) await api.put(`/admin/competitions/${editingId}`, payload, { headers: tokenHeaders() });
      else await api.post('/admin/competitions', payload, { headers: tokenHeaders() });
      toast.success(editingId ? 'Competition updated' : 'Competition created');
      reset(); load();
    } catch (error) { toast.error(error.response?.data?.message || 'Unable to save competition'); }
  };
  const edit = competition => {
    setEditingId(competition._id);
    setForm({
      title: competition.title, subject: competition.subject?._id || competition.subject,
      description: competition.description || '',
      startAt: competition.startAt ? new Date(competition.startAt).toISOString().slice(0, 16) : '',
      endAt: competition.endAt ? new Date(competition.endAt).toISOString().slice(0, 16) : '',
      durationMinutes: competition.durationMinutes, difficulty: competition.difficulty,
      status: competition.status || 'Upcoming', published: competition.published,
      negativeMarking: competition.negativeMarking, randomizeQuestions: competition.randomizeQuestions,
      randomizeOptions: competition.randomizeOptions, maxParticipants: competition.maxParticipants || 0,
      totalMarks: competition.totalMarks || 0, questions: (competition.questions || []).map(question => question._id || question)
    });
  };
  const remove = async competition => {
    if (!window.confirm(`Delete "${competition.title}"? This cannot be undone.`)) return;
    try { await api.delete(`/admin/competitions/${competition._id}`, { headers: tokenHeaders() }); toast.success('Competition deleted'); load(); }
    catch (error) { toast.error(error.response?.data?.message || 'Unable to delete competition'); }
  };
  const activate = async competition => {
    try {
      await api.post(`/admin/competitions/${competition._id}/activate`, {}, { headers: tokenHeaders() });
      toast.success(`${competition.title} is live. Share code ${competition.code} with students.`);
      load();
    } catch (error) { toast.error(error.response?.data?.message || 'Unable to start test'); }
  };
  const copyCode = async code => {
    try {
      await navigator.clipboard.writeText(code);
      toast.success('Test code copied');
    } catch {
      toast.error(`Share this test code: ${code}`);
    }
  };
  const toggleQuestion = id => setForm(current => ({ ...current, questions: current.questions.includes(id) ? current.questions.filter(questionId => questionId !== id) : [...current.questions, id] }));
  return <div>
    <div className="page-heading"><div><span>COMPETITION MANAGEMENT</span><h1>Quizzes and competitions</h1><p>Create quizzes, start them when ready, and share the displayed test code with students.</p></div></div>
    <div className="admin-form-layout competition-admin-layout">
      <form className="panel admin-form" onSubmit={save}>
        <h3>{editingId ? 'Edit competition' : 'Create competition'}</h3>
        <label>Competition title<input required value={form.title} onChange={e => setForm({...form, title: e.target.value})} /></label>
        <label>Description<textarea required value={form.description} onChange={e => setForm({...form, description: e.target.value})} /></label>
        <div className="admin-form-grid"><label>Subject<select required value={form.subject} onChange={e => setForm({...form, subject: e.target.value})}><option value="">Select subject</option>{subjects.map(subject => <option key={subject._id} value={subject._id}>{subject.name}</option>)}</select></label><label>Difficulty<select value={form.difficulty} onChange={e => setForm({...form, difficulty: e.target.value})}><option>Easy</option><option>Medium</option><option>Hard</option></select></label></div>
        <div className="admin-form-grid"><label>Start date<input required type="datetime-local" value={form.startAt} onChange={e => setForm({...form, startAt: e.target.value})} /></label><label>End date<input required type="datetime-local" value={form.endAt} onChange={e => setForm({...form, endAt: e.target.value})} /></label></div>
        <div className="admin-form-grid"><label>Duration (minutes)<input required type="number" min="1" value={form.durationMinutes} onChange={e => setForm({...form, durationMinutes: Number(e.target.value)})} /></label><label>Maximum participants<input type="number" min="0" value={form.maxParticipants} onChange={e => setForm({...form, maxParticipants: Number(e.target.value)})} /></label><label>Total marks<input type="number" min="0" value={form.totalMarks} onChange={e => setForm({...form, totalMarks: Number(e.target.value)})} /></label></div>
        <div className="admin-form-grid"><label>Status<select value={form.status} onChange={e => setForm({...form, status: e.target.value})}><option>Draft</option><option>Upcoming</option><option>Live</option><option>Completed</option></select></label><label>Publish<select value={String(form.published)} onChange={e => setForm({...form, published: e.target.value === 'true'})}><option value="true">Published</option><option value="false">Unpublished</option></select></label></div>
        <div className="checkbox-grid"><label><input type="checkbox" checked={form.negativeMarking} onChange={e => setForm({...form, negativeMarking: e.target.checked})} /> Negative marking</label><label><input type="checkbox" checked={form.randomizeQuestions} onChange={e => setForm({...form, randomizeQuestions: e.target.checked})} /> Randomize questions</label><label><input type="checkbox" checked={form.randomizeOptions} onChange={e => setForm({...form, randomizeOptions: e.target.checked})} /> Randomize options</label></div>
        <div className="question-selector"><h4>Assign questions</h4>{questions.filter(question => !form.subject || !question.subject || String(question.subject._id || question.subject) === String(form.subject)).map(question => <label key={question._id}><input type="checkbox" checked={form.questions.includes(question._id)} onChange={() => toggleQuestion(question._id)} /><span>{question.statement}</span></label>)}</div>
        <div className="admin-form-actions">{editingId && <button type="button" className="btn-secondary" onClick={reset}>Cancel edit</button>}<button className="btn-primary" type="submit">{editingId ? 'Save changes' : 'Create competition'}</button></div>
      </form>
      <div className="panel admin-list"><h3>Competitions</h3>{competitions.length ? competitions.map(competition => <article className="admin-list-row" key={competition._id}><div><h4>{competition.title}</h4><p>{competition.subject?.name} · {competition.status || 'Upcoming'} · {competition.published ? 'Published' : 'Unpublished'}</p><p className="test-code-display">Student test code: <strong>{competition.code}</strong> <button type="button" className="text-btn" onClick={() => copyCode(competition.code)}>Copy code</button></p></div><div>{competition.status !== 'Live' && <button className="btn-primary" disabled={!competition.questions?.length} title={!competition.questions?.length ? 'Assign at least one question before starting' : ''} onClick={() => activate(competition)}>Start test</button>}<button className="btn-secondary" onClick={() => window.location.href=`/admin/competitions/${competition._id}/participants`}>Participants</button><button className="btn-secondary" onClick={() => edit(competition)}>Edit</button><button className="danger-button" onClick={() => remove(competition)}>Delete</button></div></article>) : <div className="empty-card">No competitions have been created.</div>}</div>
    </div>
  </div>;
}

function AdminOverview({ data }) {
  const cards = [['Registered students', data.students, Users, 'blue'], ['Subjects', data.subjects, Database, 'violet'], ['Questions', data.questions, BrainCircuit, 'green'], ['Competitions', data.competitions, Trophy, 'amber']];
  return <div><div className="page-heading"><div><span>ADMIN CONTROL</span><h1>Platform overview</h1><p>Monitor users, content, and competition activity.</p></div></div><div className="stat-grid">{cards.map(([label, value, Icon, tone]) => <article className="stat-card" key={label}><div className={`stat-icon ${tone}`}><Icon size={21}/></div><div><span>{label}</span><strong>{value || 0}</strong></div></article>)}</div><div className="dashboard-grid"><section className="panel"><div className="panel-head"><div><span>ACTIVITY</span><h3>Platform growth</h3></div></div><div className="chart"><ResponsiveContainer width="100%" height="100%"><AreaChart data={[{name:'Week 1',users:2},{name:'Week 2',users:5},{name:'Week 3',users:7},{name:'Week 4',users:11}]}><XAxis dataKey="name"/><YAxis/><Tooltip/><Area type="monotone" dataKey="users" stroke="#5b5fe9" fill="#e9eaff"/></AreaChart></ResponsiveContainer></div></section><section className="panel"><div className="panel-head"><div><span>OPERATIONS</span><h3>Management modules</h3></div></div><div className="admin-actions"><button><Users/><span>User management</span></button><button><Database/><span>Subjects</span></button><button><BrainCircuit/><span>Questions</span></button><button><Trophy/><span>Competitions</span></button></div></section></div></div>;
}

function QuizPage() {
  const navigate = useNavigate();
  const [quiz, setQuiz] = useState(null);
  const [instructions,setInstructions]=useState(null);
  const [instructionsAccepted,setInstructionsAccepted]=useState(false);
  const [answers, setAnswers] = useState([]);
  const [index, setIndex] = useState(0);
  const [seconds, setSeconds] = useState(0);
  const [submitted, setSubmitted] = useState(null);
  const answersRef = useRef(answers);
  const submitQuizRef = useRef(null);
  const id = window.location.pathname.split('/').pop();

  useEffect(() => {
    api.get('/competitions', { headers: tokenHeaders() }).then(r => {
      setInstructions(r.data.competitions.find(item => item._id === id) || null);
    }).catch(e => toast.error(e.response?.data?.message || 'Unable to load competition instructions'));
  }, [id]);

  const submitQuiz = useCallback(async auto => {
    if (!quiz) return;
    try {
      const { data } = await api.post(`/attempts/${quiz.attemptId}/submit`, { answers: answersRef.current, forceAutoSubmit: auto, manual: !auto }, { headers: tokenHeaders() });
      setSubmitted(data.result);
    } catch (error) { toast.error(error.response?.data?.message || 'Submission failed'); }
  }, [quiz]);
  useEffect(() => {
    answersRef.current = answers;
  }, [answers]);
  useEffect(() => {
    submitQuizRef.current = submitQuiz;
  }, [submitQuiz]);

  useEffect(() => {
    if (!quiz || submitted) return;
    const timer = setInterval(() => setSeconds(s => {
      if (s <= 1) { clearInterval(timer); submitQuizRef.current?.(true); return 0; }
      return s - 1;
    }), 1000);
    return () => clearInterval(timer);
  }, [quiz, submitted]);

  useEffect(() => {
    if (!quiz || submitted) return;
    const recordEvent = type => {
      api.post(`/attempts/${quiz.attemptId}/suspicious-event`, { type }, { headers: tokenHeaders() }).catch(() => {});
      toast(`Warning: ${type === 'tab-switch' ? 'Tab switching' : 'Leaving fullscreen'} has been recorded.`);
    };
    const onVisibility = () => { if (document.hidden) recordEvent('tab-switch'); };
    const onFullscreen = () => { if (!document.fullscreenElement) recordEvent('fullscreen-exit'); };
    document.addEventListener('visibilitychange', onVisibility);
    document.addEventListener('fullscreenchange', onFullscreen);
    return () => {
      document.removeEventListener('visibilitychange', onVisibility);
      document.removeEventListener('fullscreenchange', onFullscreen);
    };
  }, [quiz, submitted]);

  const selectAnswer = optionIndex => setAnswers(prev => {
    const next = [...prev];
    next[index] = { question: quiz.questions[index]._id, selectedOption: quiz.questions[index].options[optionIndex].originalIndex, marked: false };
    api.put(`/attempts/${quiz.attemptId}/progress`, { answers: next }, { headers: tokenHeaders() }).catch(() => {});
    return next;
  });
  const markForReview = () => setAnswers(prev => {
    const next = [...prev];
    next[index] = { ...(next[index] || { question: quiz.questions[index]._id, selectedOption: null }), marked: true };
    api.put(`/attempts/${quiz.attemptId}/progress`, { answers: next }, { headers: tokenHeaders() }).catch(() => {});
    return next;
  });
  const clearResponse = () => setAnswers(prev => {
    const next = [...prev];
    next[index] = undefined;
    api.put(`/attempts/${quiz.attemptId}/progress`, { answers: next }, { headers: tokenHeaders() }).catch(() => {});
    return next;
  });
  const beginQuiz=async()=>{
    if(!instructionsAccepted)return;
    try{
      if(document.documentElement.requestFullscreen)await document.documentElement.requestFullscreen().catch(()=>{});
      const r=await api.post(`/competitions/${id}/start`,{}, {headers:tokenHeaders()});
      setQuiz(r.data);
      setSeconds(Math.max(0,Math.floor((new Date(r.data.deadlineAt)-new Date())/1000)));
    }catch(e){toast.error(e.response?.data?.message||'Unable to start quiz');}
  };
  if(!quiz){
    if(!instructions)return <main className="quiz-loading"><div className="loader" /><p>Preparing instructions...</p></main>;
    return <main className="exam-instructions"><section className="panel instruction-card"><span className="eyebrow">COMPETITION INSTRUCTIONS</span><h1>{instructions.title}</h1><p>{instructions.description}</p><div className="instruction-grid"><article><strong>{instructions.durationMinutes}</strong><span>Minutes</span></article><article><strong>{instructions.questions?.length||0}</strong><span>Questions</span></article><article><strong>{instructions.negativeMarking?'Enabled':'Disabled'}</strong><span>Negative marking</span></article></div><ul><li>Use Previous, Next, Save and Clear controls to navigate.</li><li>Mark uncertain questions for review from the question palette.</li><li>Submit manually or before the server-enforced deadline.</li><li>Fullscreen is recommended. Leaving it will record a warning but does not guarantee disqualification.</li><li>Refreshing the page preserves the server-generated attempt deadline.</li></ul><label className="instruction-confirm"><input type="checkbox" checked={instructionsAccepted} onChange={e=>setInstructionsAccepted(e.target.checked)}/> I have read and agree to follow the competition instructions.</label><div className="instruction-actions"><button className="btn-secondary" onClick={()=>navigate('/competitions')}>Cancel</button><button className="btn-primary" disabled={!instructionsAccepted} onClick={beginQuiz}>Enter fullscreen and begin</button></div></section></main>;
  }
  if (submitted) return <main className="result-page"><div className="result-hero"><Award size={48}/><span>QUIZ SUBMITTED</span><h1>Great effort!</h1><p>You completed the competition.</p><div className="result-score">{submitted.score}<small> / {submitted.maxScore}</small></div></div><div className="result-summary"><article><strong>{submitted.correct}</strong><span>Correct</span></article><article><strong>{submitted.incorrect}</strong><span>Incorrect</span></article><article><strong>{submitted.unattempted}</strong><span>Unattempted</span></article><article><strong>{submitted.accuracy}%</strong><span>Accuracy</span></article></div><button className="btn-primary" onClick={() => navigate('/dashboard')}>Back to dashboard</button></main>;
  const question = quiz.questions[index];
  const mins = Math.floor(seconds / 60), secs = seconds % 60;
  return <main className="quiz-page"><header><button className="brand" onClick={() => navigate('/dashboard')}><span className="brand-mark"><BrainCircuit size={22}/></span>QuizArena</button><div className="quiz-timer"><span>Time remaining</span><strong>{String(mins).padStart(2,'0')}:{String(secs).padStart(2,'0')}</strong></div><button className="btn-primary" onClick={() => window.confirm('Submit this quiz now?') && submitQuiz(false)}>Submit test</button></header><div className="quiz-layout"><aside><h3>Question palette</h3><div className="palette">{quiz.questions.map((q, i) => <button key={q._id} className={`${i === index ? 'current' : ''} ${answers[i]?.marked ? 'marked' : answers[i]?.selectedOption !== undefined ? 'answered' : ''}`} onClick={() => setIndex(i)}>{i + 1}</button>)}</div><div className="palette-legend"><span><i className="legend-answer"/>Answered</span><span><i className="legend-marked"/>Marked</span><span><i className="legend-unanswered"/>Unanswered</span></div></aside><section className="question-panel"><div className="question-head"><span>Question {index + 1} of {quiz.questions.length}</span><span>{question.marks} mark</span></div><h2>{question.statement}</h2><div className="option-list quiz-options">{question.options.map((option, i) => <button key={i} className={answers[index]?.selectedOption === option.originalIndex ? 'selected' : ''} onClick={() => selectAnswer(i)}><span>{String.fromCharCode(65+i)}</span>{option.text}</button>)}</div><div className="quiz-actions"><button className="btn-secondary" disabled={index === 0} onClick={() => setIndex(index - 1)}>Previous</button><button className="btn-secondary" onClick={clearResponse}>Clear response</button><button className="btn-secondary" onClick={markForReview}>Mark for review</button><button className="btn-primary" onClick={() => setIndex(Math.min(index + 1, quiz.questions.length - 1))}>{index === quiz.questions.length - 1 ? 'Review test' : 'Save & next'}</button></div></section></div></main>;
}

export default App;
