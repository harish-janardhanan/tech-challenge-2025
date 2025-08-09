import { describe, it, expect } from 'vitest';
import { render, screen } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import '@testing-library/jest-dom';
import TraderSales from './TraderSales';
import React from 'react';

describe('TraderSales Page', () => {
  it('renders the Trader / Sales View heading', () => {
    render(
      <MemoryRouter>
        <TraderSales />
      </MemoryRouter>
    );
    expect(screen.getByText(/Welcome to the Trade Platform/i)).toBeInTheDocument();
  });
});

