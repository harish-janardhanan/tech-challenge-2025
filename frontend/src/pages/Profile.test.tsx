import { describe, it, expect } from 'vitest';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import Profile from './Profile';
import React from 'react';

describe('Profile Page', () => {
  it('renders the Profile heading', () => {
    render(<Profile />);
    expect(screen.getByText(/Profile/i)).toBeInTheDocument();
  });
});

