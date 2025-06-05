import { describe, it, expect } from 'vitest';
import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import LoadingSpinner from './LoadingSpinner';
import React from 'react';

describe('LoadingSpinner Component', () => {
  it('renders the spinner svg', () => {
    const { container } = render(<LoadingSpinner />);
    expect(container.querySelector('svg')).toBeInTheDocument();
  });
});

