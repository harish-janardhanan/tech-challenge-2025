import React, {useMemo} from 'react';
import {AgGridReact} from 'ag-grid-react';
import 'ag-grid-community/styles/ag-grid.css';
import 'ag-grid-community/styles/ag-theme-alpine.css';

interface AGGridTableProps {
    columnDefs: never;
    rowData: never;
    onSelectionChanged?: (event) => void;
    rowSelection: 'singleRow' | 'multiRow';
    [key: string]: never;
}

const AGGridTable: React.FC<AGGridTableProps> = (props) => {
    const { columnDefs, rowData, onSelectionChanged, rowSelection, ...rest } = props;

    const gridRowSelection = useMemo(() => {
        return {
            mode: rowSelection,
            checkboxes: false,
            enableClickSelection: true,
        };
    }, [rowSelection]);

    return (
        <div className={"h-fit w-full ag-theme-alpine"}>
            <AgGridReact
                columnDefs={columnDefs}
                rowData={rowData}
                domLayout="autoHeight"
                theme={"legacy"}
                rowSelection={gridRowSelection}
                onSelectionChanged={onSelectionChanged}

                {...rest}
            />
        </div>
    )
};

export default AGGridTable;
