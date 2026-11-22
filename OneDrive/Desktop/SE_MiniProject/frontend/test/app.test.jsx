import { describe, it, expect, beforeEach } from 'vitest';
import { render, screen } from '@testing-library/react';
import App from '../src/App.jsx';

beforeEach(() => {
  localStorage.clear();
  window.history.pushState({}, '', '/login');
});

describe('QuizArena authentication pages', () => {
  it('renders login form', () => {
    render(<App />);
    expect(screen.getByRole('heading', { name: 'Welcome back' })).toBeInTheDocument();
  });

  it('renders forgot password form', () => {
    window.history.pushState({}, '', '/forgot-password');
    render(<App />);
    expect(screen.getByRole('heading', { name: 'Forgot password' })).toBeInTheDocument();
  });

  it('renders registration form', () => {
    window.history.pushState({}, '', '/register');
    render(<App />);
    expect(screen.getByRole('heading', { name: 'Create your account' })).toBeInTheDocument();
  });
});
