import { defineConfig } from 'vitest/config';

process.env.NODE_ENV = 'test';
process.env.JWT_SECRET = 'automated-test-secret';

export default defineConfig({
  test: {
    environment: 'node',
    testTimeout: 30000,
    hookTimeout: 30000
  }
});
