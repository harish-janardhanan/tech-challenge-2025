import React, {useEffect, useState} from "react";
import AGGridTable from "./AGGridTable";
import {useQuery} from '@tanstack/react-query';
import {AllCommunityModule, ModuleRegistry} from 'ag-grid-community';
import api, {fetchAllUsers} from "../utils/api";
import Snackbar from "./Snackbar";
import LoadingSpinner from "./LoadingSpinner";

ModuleRegistry.registerModules([AllCommunityModule]);

export const UserDetailsComponent: React.FC = () => {
    const [snackbarOpen, setSnackbarOpen] = useState(false);
    const [snackbarMsg, setSnackbarMsg] = useState("");
    const {data: users = [], isLoading, error, isSuccess} = useQuery({
        queryKey: ['users'],
        queryFn: fetchAllUsers, // Use the imported fetchAllUsers directly
        refetchInterval: 30000,
    });

    useEffect(() => {
        if (isSuccess) {
            setSnackbarMsg("Users fetched successfully!");
            setSnackbarOpen(true);
            const timer = setTimeout(() => setSnackbarOpen(false), 5000);
            return () => clearTimeout(timer);
        } else if (error) {
            setSnackbarMsg("Error fetching users");
            setSnackbarOpen(true);
            const timer = setTimeout(() => setSnackbarOpen(false), 5000);
            return () => clearTimeout(timer);
        }
    }, [isSuccess, error]);

    const columnDefs = [
        {headerName: "ID", field: "id", sortable: true, filter: true},
        {headerName: "Name", field: "name", sortable: true, filter: true},
        {headerName: "Email", field: "email", sortable: true, filter: true},
        {headerName: "Role", field: "role", sortable: true, filter: true}
    ];
    return (
        <div className={"flex flex-col justify-start min-h-full min-w-full justify-items-center"}>
            <Snackbar open={snackbarOpen} message={snackbarMsg} type={error ? "error": "success"} onClose={() => setSnackbarOpen(false)}/>
            <div className={"flex text-2xl"}>User Details</div>
            {isLoading ? (
                <LoadingSpinner/>
            ) : error ? (
                <div>Error loading users</div>
            ) : (
                <AGGridTable columnDefs={columnDefs} rowData={users}/>
            )}
        </div>
    );
}