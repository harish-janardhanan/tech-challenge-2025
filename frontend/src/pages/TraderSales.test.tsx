import { describe, it, expect } from 'vitest';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import TraderSales from './TraderSales';
import React from 'react';

describe('TraderSales Page', () => {
  it('renders the Trader / Sales View heading', () => {
    render(<TraderSales />);
    expect(screen.getByText(/Trader \/ Sales View/i)).toBeInTheDocument();
  });
});

