import { describe, it, expect, vi } from 'vitest';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import SignUp from './SignUp';
import React from 'react';

vi.mock('../utils/api', () => ({
  fetchUserProfiles: vi.fn().mockResolvedValue({ data: [] }),
  createUser: vi.fn().mockResolvedValue({}),
}));

describe('SignUp Modal', () => {
  it('renders form fields when open', async () => {
    render(<SignUp isOpen={true} onClose={() => {}} />);
    expect(await screen.findByRole('heading', { name: /Sign Up/i })).toBeInTheDocument();
    expect(await screen.findByPlaceholderText(/First Name/i)).toBeInTheDocument();

  });
});

