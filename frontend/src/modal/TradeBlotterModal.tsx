import React from "react";
import {observer} from "mobx-react-lite";
import AGGridTable from "../components/AGGridTable";
import {useQuery} from "@tanstack/react-query";
import {queries} from "@testing-library/react";
import api, {fetchTrades} from "../utils/api";
import {getColDefFromResult, getRowDataFromData} from "../utils/agGridUtils";

export const TradeBlotterModal: React.FC = observer(() => {
    const [trades, setTrades] = React.useState<any[]>([]);
    const [isSnackbarOpen, setIsSnackbarOpen] = React.useState(false);

    const {data, isLoading, error, isSuccess} = useQuery({
        queryKey: ['trades'],
        queryFn: async () => {
            const res = await fetchTrades();
            return res.data;
        },
        onError: (err) => {
            console.error("Error fetching trades:", err);
            setIsSnackbarOpen(true);
            setTimeout(() => {
                setIsSnackbarOpen(false)
            }, 3000);
        },
        refetchInterval: 30000,
    });

    React.useEffect(() => {
        if (isSuccess && data) {
            setTrades(data);
        }
    }, [isSuccess, data]);

    const columnDefs = getColDefFromResult(trades);
    const rowData = getRowDataFromData(trades);
    return (
        <div className={"h-fit w-full flex flex-col min-h-full min-w-full justify-start"}>
            <div>
                <AGGridTable columnDefs={columnDefs}
                             rowData={rowData}
                             onSelectionChanged={() => {
                             }}
                             rowSelection={"singleRow"}> </AGGridTable>
            </div>
        </div>
    )
})