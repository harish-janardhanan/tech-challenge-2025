import { describe, it, expect } from 'vitest';
import { render, screen } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import '@testing-library/jest-dom';
import MiddleOffice from './MiddleOffice';
import React from 'react';

describe('MiddleOffice Page', () => {
  it('renders the Middle Office View heading', () => {
    render(
      <MemoryRouter>
        <MiddleOffice />
      </MemoryRouter>
    );
    expect(screen.getByText(/Welcome to the Trade Platform/i)).toBeInTheDocument();
  });
});

