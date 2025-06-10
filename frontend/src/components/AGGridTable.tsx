import React from 'react';
import { AgGridReact } from 'ag-grid-react';
import { ColDef } from 'ag-grid-community';
import 'ag-grid-community/styles/ag-grid.css';
import 'ag-grid-community/styles/ag-theme-alpine.css';
import {ab} from "vitest/dist/chunks/reporters.d.C1ogPriE";

interface AGGridTableProps {
    columnDefs: ColDef[];
    rowData: never[];
    onSelectionChanged?: (event: any) => void;
    rowSelection: 'single' | 'multiple';
    [key: string]: any;
}

const AGGridTable: React.FC<AGGridTableProps> = (props) => {
    const { columnDefs, rowData, onSelectionChanged, rowSelection, ...rest } = props;

    return (
        <div className={"h-fit w-full ag-theme-alpine"}>
            <AgGridReact
                columnDefs={columnDefs}
                rowData={rowData}
                domLayout="autoHeight"
                rowSelection={rowSelection}
                theme={"legacy"}
                onSelectionChanged={onSelectionChanged}
                {...rest}
            />
        </div>
    );
};

export default AGGridTable;
