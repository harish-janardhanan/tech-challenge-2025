import { describe, it, expect, beforeAll } from 'vitest';
import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import AGGridTable from './AGGridTable';
import React from 'react';
import { ModuleRegistry, AllCommunityModule } from 'ag-grid-community';

beforeAll(() => {
  ModuleRegistry.registerModules([AllCommunityModule]);
});

describe('AGGridTable Component', () => {
  it('renders the AG Grid container', () => {
    const columnDefs = [{ headerName: 'Test', field: 'test' }];
    const rowData = [{ test: 'value' }];
    const { container } = render(
      <AGGridTable columnDefs={columnDefs} rowData={rowData} rowSelection={'single'} />
    );
    expect(container.querySelector('.ag-theme-alpine')).toBeInTheDocument();
  });
});

