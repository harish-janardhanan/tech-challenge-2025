import { describe, it, expect } from 'vitest';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import SignUp from './SignUp';
import React from 'react';

describe('SignUp Page', () => {
  it('renders the Sign Up heading', () => {
    render(<SignUp />);
    expect(screen.getByText(/Sign Up/i)).toBeInTheDocument();
  });
});

