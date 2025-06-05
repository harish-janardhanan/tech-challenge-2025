import React from 'react';
import {AgGridReact} from 'ag-grid-react';
import 'ag-grid-community/styles/ag-grid.css';
import 'ag-grid-community/styles/ag-theme-alpine.css';

const AGGridTable = ({columnDefs, rowData, ...props}) => {
    return (
        <div className="ag-theme-alpine" style={{height: 400, width: '100%'}}>
            <AgGridReact
                columnDefs={columnDefs}
                rowData={rowData}
                domLayout="autoHeight"
                {...props}
            />
        </div>
    )
};

export default AGGridTable;

