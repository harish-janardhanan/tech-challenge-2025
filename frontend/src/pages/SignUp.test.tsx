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
  it('renders form fields when open', () => {
    render(<SignUp isOpen={true} onClose={() => {}} />);
    expect(screen.getByText(/Sign Up/i)).toBeInTheDocument();
    expect(screen.getByPlaceholderText(/First Name/i)).toBeInTheDocument();
  });
});

