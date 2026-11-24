# QuizArena

QuizArena is a full-stack online quiz and learning platform for timed competitions, practice, learning resources, leaderboards, gamification, certificates, and administration.

## Problem statement

Students need a single platform to practice subjects, join timed competitions, review results, track progress, and compete fairly.

## Objectives

- Provide secure student and administrator authentication.
- Store competitions, questions, attempts, learning content, announcements, and certificates in MongoDB.
- Calculate quiz results and rankings from submitted database records.
- Provide responsive student and admin interfaces.
- Keep browser timers secondary to server-generated deadlines.

## Technology stack

- Frontend: React, Vite, React Router, Axios, Lucide React, Recharts, React Hot Toast, jsPDF.
- Backend: Node.js, Express.js, Mongoose, JWT, bcrypt, dotenv.
- Database: MongoDB.
- Development tools: VS Code, Postman, MongoDB.
- Hosting targets: Vercel, Render, MongoDB Atlas.

## Architecture

```text
frontend/  React application
backend/   Express REST API
MongoDB    Users, subjects, questions, competitions, attempts,
           learning material, announcements, certificates,
           daily challenges, and suspicious events
```

## Main features

- Student and administrator login.
- Registration, logout, profile editing, password change, and password reset.
- Student dashboard with real MongoDB statistics.
- Competition listing, joining, timed attempts, answer saving, review marking, submission, and results.
- Admin competition and MCQ management.
- A 50-question sample bank spanning Java, Python, data structures, databases, web development, operating systems, networking, and aptitude. Missing sample subjects/questions are added safely when the API starts.
- Administrators can start a scheduled test from the competition list, then share its private entry code with students.
- Admin user, subject, learning, announcement, result, and question management.
- Practice mode and learning materials.
- Global, weekly, monthly, and subject-capable leaderboard data.
- Daily challenge with one attempt per student per day.
- XP, levels, streaks, and achievement fields.
- Suspicious tab-switch and fullscreen-exit event recording.
- Eligible certificate creation and browser-generated PDF download.
- Public certificate verification.

## Local setup

### Prerequisites

- Node.js 20 or newer.
- MongoDB 8 or MongoDB Atlas.

### Database and backend

```powershell
cd backend
Copy-Item .env.example .env
npm install
npm run seed
npm run dev
```

The API runs at `http://localhost:5000`.

### Frontend

```powershell
cd frontend
Copy-Item .env.example .env
npm install
npm run dev
```

The frontend runs at `http://localhost:5173`.

## Environment variables

### Backend

| Variable | Purpose |
| --- | --- |
| `PORT` | API port |
| `MONGODB_URI` | MongoDB connection string |
| `JWT_SECRET` | JWT signing secret |
| `JWT_EXPIRES_IN` | JWT lifetime |
| `CLIENT_URL` | Primary frontend origin |
| `CLIENT_URLS` | Optional comma-separated additional frontend origins |
| `NODE_ENV` | Set to `development` or `production` |
| `APP_TIMEZONE` | Daily challenge timezone |
| `SMTP_HOST` | SMTP server host |
| `SMTP_PORT` | SMTP server port, usually 587 |
| `SMTP_SECURE` | Use TLS directly, usually `false` |
| `SMTP_USER` | SMTP username |
| `SMTP_PASS` | SMTP password |
| `SMTP_FROM` | Sender address |
| `ADMIN_NAME` | Seed administrator name |
| `ADMIN_EMAIL` | Seed administrator email |
| `ADMIN_PASSWORD` | Seed administrator password |

### Frontend

| Variable | Purpose |
| --- | --- |
| `VITE_API_URL` | REST API base URL |

Never commit `.env` or real credentials.

## Password reset

When `NODE_ENV=development`, `POST /api/auth/forgot-password` returns a time-limited reset token for local testing. In production, integrate an email provider and do not return the token.

Example:

```text
POST /api/auth/reset-password
{ "token": "<token>", "password": "<new password>" }
```

## Demo accounts

- Admin: `admin@quizarena.com` / `Admin@123`
- Student: `student@quizarena.com` / `Student@123`

Run `npm run seed` in `backend` to recreate demonstration data. This command clears existing demo collections; production deployments add any missing sample questions on API startup without deleting existing data.

For a database-free, single-browser demo, the frontend production build uses
`VITE_DEMO_MODE=true` and stores demo accounts, quiz progress, and results in
browser `localStorage`. This mode is suitable for demonstrations on the same
browser and is not a replacement for MongoDB-backed authentication or data
shared across different devices.

## API overview

- `POST /api/auth/register`
- `POST /api/auth/login`
- `POST /api/auth/forgot-password`
- `POST /api/auth/reset-password`
- `GET /api/auth/me`
- `PUT /api/auth/profile`
- `PUT /api/auth/change-password`
- `GET /api/dashboard`
- `GET /api/competitions`
- `POST /api/competitions/join-by-code`
- `POST /api/competitions/:id/join`
- `POST /api/competitions/:id/start`
- `PUT /api/attempts/:id/progress`
- `POST /api/attempts/:id/submit`
- `GET /api/results`
- `GET /api/leaderboard`
- `GET /api/daily-challenge`
- `POST /api/daily-challenge/:id/submit`
- `GET /api/learning`
- `GET /api/announcements`
- `GET /api/certificates`
- Admin question, subject, learning, announcement, competition, and user management routes require an administrator JWT.
- `POST /api/admin/competitions/:id/activate` starts a quiz immediately and returns its student entry code.

## Testing

```powershell
cd frontend
npm run build
npm test

cd ../backend
node --check src/server.js
node --check src/models.js
npm test
```

Use the demo accounts for browser verification. Browser-side tab-switch and fullscreen events are warning controls and are recorded, not considered foolproof anti-cheating.

## Deployment

### MongoDB Atlas

Create a database user and network allowlist, then set `MONGODB_URI` to the Atlas connection string.

### Render

Deploy `backend` as a Node web service. Set all backend environment variables and use `npm start`.

### Vercel

Deploy `frontend`. Set `VITE_API_URL` to the deployed Render API plus `/api`, for example `https://your-api.onrender.com/api`.

No deployment is verified until a public URL has been opened and authenticated API requests have been tested.

## Team contributions

- Ayush Singh — Frontend Design & Development
- Himanshu Ojha — Backend Development
- Md. Sahabuddin — Documentation
- Ashutosh Kumar — Database Management
- Manish Kumar — Testing / QA
