import { describe, it, expect } from 'vitest';
import { render, screen } from '@testing-library/react';
import SupportAdmin from './SupportAdmin.tsx';
import React from 'react';

describe('SupportAdmin Page', () => {
  it('renders the Support / Admin View heading', () => {
    render(<SupportAdmin />);
    expect(screen.getByText(/Support \/ Admin View/i)).toBeInTheDocument();
  });
});

