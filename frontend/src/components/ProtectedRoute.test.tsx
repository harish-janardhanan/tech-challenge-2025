import { describe, it, expect } from 'vitest';
import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import ProtectedRoute from './ProtectedRoute';
import React from 'react';
import { MemoryRouter } from 'react-router-dom';

describe('ProtectedRoute Component', () => {
  it('renders children when allowed', () => {
    const { getByText } = render(
      <MemoryRouter>
        <ProtectedRoute isAllowed={true}>
          <div>Allowed Content</div>
        </ProtectedRoute>
      </MemoryRouter>
    );
    expect(getByText('Allowed Content')).toBeInTheDocument();
  });
});

