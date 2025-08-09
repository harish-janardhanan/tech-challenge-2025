import { describe, it, expect } from 'vitest';
import { render, screen } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import Support from './Support';
import React from 'react';

describe('Support Page', () => {
  it('renders the Support / Admin View heading', () => {
    render(
      <MemoryRouter>
        <Support />
      </MemoryRouter>
    );
    expect(screen.getByText(/Welcome to the Trade Platform/i)).toBeInTheDocument();
  });
});

