import { describe, it, expect } from 'vitest';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import Navbar from './Navbar';
import React from 'react';

describe('Navbar Component', () => {
  it('renders the Trade Platform brand', () => {
    render(<Navbar />);
    expect(screen.getByText(/Trade Platform/i)).toBeInTheDocument();
  });
});

