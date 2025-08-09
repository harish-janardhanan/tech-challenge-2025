import { describe, it, expect } from 'vitest';
import { render, screen } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import '@testing-library/jest-dom';
import Navbar from './Navbar';
import React from 'react';

describe('Navbar Component', () => {
  it('renders the Trade Platform brand', () => {
    render(
      <MemoryRouter>
        <Navbar />
      </MemoryRouter>
    );
    expect(screen.getByText(/Trading Platform/i)).toBeInTheDocument();
  });
});

