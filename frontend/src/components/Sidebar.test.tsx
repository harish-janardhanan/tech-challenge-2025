import { describe, it, expect } from 'vitest';
import { render, screen } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import '@testing-library/jest-dom';
import Sidebar from './Sidebar';
import React from 'react';

describe('Sidebar Component', () => {
  it('renders the sidebar container', () => {
    render(
      <MemoryRouter>
        <Sidebar />
      </MemoryRouter>
    );
    expect(screen.getByRole('complementary')).toBeInTheDocument();
  });
});

