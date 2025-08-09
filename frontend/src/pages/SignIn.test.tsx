import { describe, it, expect } from 'vitest';
import { render, screen } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import '@testing-library/jest-dom';
import SignIn from './SignIn';
import React from 'react';

describe('SignIn Page', () => {
  it('renders the Sign In heading', () => {
    render(
      <MemoryRouter>
        <SignIn />
      </MemoryRouter>
    );
    expect(screen.getByText(/Sign In/i)).toBeInTheDocument();
  });
});

