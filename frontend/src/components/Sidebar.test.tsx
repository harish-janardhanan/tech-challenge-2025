import { describe, it, expect } from 'vitest';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import Sidebar from './Sidebar';
import React from 'react';

describe('Sidebar Component', () => {
  it('renders the sidebar container', () => {
    render(<Sidebar />);
    // Check for the sidebar element by role or class
    expect(screen.getByRole('complementary')).toBeInTheDocument();
  });
});

