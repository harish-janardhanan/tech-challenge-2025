import { describe, it, expect } from 'vitest';
import { render, screen } from '@testing-library/react';
import Support from './Support.tsx';
import React from 'react';

describe('Support Page', () => {
  it('renders the Support / Admin View heading', () => {
    render(<Support />);
    expect(screen.getByText(/Support \/ Admin View/i)).toBeInTheDocument();
  });
});

