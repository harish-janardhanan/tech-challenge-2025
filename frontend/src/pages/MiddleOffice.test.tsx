import { describe, it, expect } from 'vitest';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import MiddleOffice from './MiddleOffice';
import React from 'react';

describe('MiddleOffice Page', () => {
  it('renders the Middle Office View heading', () => {
    render(<MiddleOffice />);
    expect(screen.getByText(/Middle Office View/i)).toBeInTheDocument();
  });
});

