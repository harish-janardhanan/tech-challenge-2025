import { describe, it, expect } from 'vitest';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import SignIn from './SignIn';
import React from 'react';

describe('SignIn Page', () => {
  it('renders the Sign In heading', () => {
    render(<SignIn />);
    expect(screen.getByText(/Sign In/i)).toBeInTheDocument();
  });
});

