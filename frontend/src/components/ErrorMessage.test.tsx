import { describe, it, expect } from 'vitest';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import ErrorMessage from './ErrorMessage';
import React from 'react';

describe('ErrorMessage Component', () => {
  it('renders the error message', () => {
    render(<ErrorMessage message="Test error" />);
    expect(screen.getByText(/Test error/i)).toBeInTheDocument();
  });
});

