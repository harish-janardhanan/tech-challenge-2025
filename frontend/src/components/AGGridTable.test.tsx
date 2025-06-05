import { describe, it, expect } from 'vitest';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import AGGridTable from './AGGridTable';
import React from 'react';

describe('AGGridTable Component', () => {
  it('renders the AG Grid container', () => {
    const columnDefs = [{ headerName: 'Test', field: 'test' }];
    const rowData = [{ test: 'value' }];
    render(<AGGridTable columnDefs={columnDefs} rowData={rowData} />);
    expect(screen.getByRole('grid')).toBeInTheDocument();
  });
});

