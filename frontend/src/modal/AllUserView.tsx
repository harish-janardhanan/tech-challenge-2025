import React, {useState} from "react";
import AGGridTable from "./../components/AGGridTable";
import {AllCommunityModule, ModuleRegistry} from 'ag-grid-community';
import {fetchAllUsers, createUser, updateUser} from "../utils/api";
import Snackbar from "./../components/Snackbar";
import LoadingSpinner from "./../components/LoadingSpinner";
import Button from "./../components/Button";
import {getColDefFromResult, getRowDataFromData} from "../utils/agGridUtils";
import axios from "axios";
import {observer} from "mobx-react-lite";
import {SingleUserModal} from "./SingleUserModal";
import {ApplicationUser} from "../utils/ApplicationUser";

ModuleRegistry.registerModules([AllCommunityModule]);

interface AllUserViewProps {
    setView: (view: string, user?: any) => void;
    selectedUser: any;
    setSelectedUser: (user: any) => void;
}

export const AllUserView: React.FC<AllUserViewProps> = observer(() => {
    const [snackbarOpen, setSnackbarOpen] = useState(false);
    const [snackbarMsg, setSnackbarMsg] = useState("");
    const [snackbarType, setSnackbarType] = useState<'success' | 'error'>("success");
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState("");
    const [isDetailOpen, setIsDetailOpen] = useState(false);
    const [currentUser, setCurrentUser] = useState<ApplicationUser | null>(null);
    const [users, setUsers] = useState<ApplicationUser[]>([]);


    const onLoadUsers = async () => {
        setIsLoading(true);
        setError("");
        setSnackbarOpen(false);
        setSnackbarMsg("");
        fetchAllUsers()
            .then((res) => {
                setUsers(res.data);
            })
            .catch((err) => {
                if (axios.isAxiosError(err)) {
                    setError(err.message);
                    setSnackbarOpen(true);
                    setSnackbarMsg("Error Fetching Users: " + err.message);
                } else if (err && typeof err === "object" && "message" in err) {
                    setError(err);
                    setSnackbarOpen(true);
                    setSnackbarMsg("Non API error fetching users: " + err);
                } else {
                    setError("Unknown error");
                    setSnackbarOpen(true);
                    setSnackbarMsg("Non API error fetching users: Unknown error");
                }
            })
            .finally(() => {
                setIsLoading(false);
                setCurrentUser(null)
            });
    };

    const handleSaveUser = async () => {
        if (!currentUser) return;
        // Only send fields expected by backend (exclude id, version, lastModifiedTimestamp)
        const userToSend = {
            firstName: currentUser.firstName,
            lastName: currentUser.lastName,
            loginId: currentUser.loginId,
            password: currentUser.password,
            active: currentUser.active,
            userProfile: currentUser.userProfile,
        };
        const isEdit = !!currentUser.id;
        try {
            if (isEdit) {
                await updateUser(currentUser.id, userToSend);
                setSnackbarMsg("User updated successfully.");
            } else {
                await createUser(userToSend);
                setSnackbarMsg("User saved successfully.");
            }
            setSnackbarType("success");
            setSnackbarOpen(true);
            setIsDetailOpen(false);
            onLoadUsers();
            setCurrentUser(null)
        } catch (err: any) {
            setSnackbarMsg("Failed to save user: " + (err?.message || 'Unknown error'));
            setSnackbarType("error");
            setSnackbarOpen(true);
        }
    };

    // Handler to open modal and load selected user from grid
    const handleSelectionChanged = (event: any) => {
        const selectedRows = event.api.getSelectedRows();
        if (selectedRows.length > 0) {
            setCurrentUser(selectedRows[0]);
        } else {
            setCurrentUser(null);
        }
    };

    const handleEditUser = () => {
        if (currentUser) {
            setIsDetailOpen(true)
        }
    }

    const columnDefs = getColDefFromResult(users);
    const rowDefs = getRowDataFromData(users);
    return (
        <div className={"flex flex-col justify-start min-h-full w-full justify-items-center mt-2"}>
            <div className={"flex flex-col justify-center justify-items-center"}>
                <div
                    className={"flex text-2xl justify-self-center mb-2 flex-row justify-center shadow rounded bg-indigo-100"}>User
                    Details
                </div>
                {isLoading ? (
                    <LoadingSpinner/>
                ) : error ? (
                    <Snackbar open={snackbarOpen} message={snackbarMsg} type={error ? "error" : "success"}
                              onClose={() => setSnackbarOpen(false)}/>
                ) : (
                    <AGGridTable
                        columnDefs={columnDefs}
                        rowData={rowDefs}
                        rowSelection="single"
                        onSelectionChanged={handleSelectionChanged}
                    />
                )}
            </div>
            <div className={"flex flex-row justify-end gap-2 mt-2"}>
                <Button variant={"primary"} type={"submit"} size={"sm"} className={"w-30 mt-2"} onClick={onLoadUsers}>Load
                    Users</Button>
                <Button variant={"primary"} type={"submit"} size={"sm"}
                        className={"w-30  mt-2 !bg-amber-500 hover:!bg-amber-700"}
                        onClick={handleEditUser} disabled={!currentUser}>Edit User</Button></div>
            <SingleUserModal isOpen={isDetailOpen}
                             user={currentUser}
                             setUser={setCurrentUser}
                             onClose={() => { setIsDetailOpen(false); setCurrentUser(null); }}
                             onClickSave={handleSaveUser}
            />
        </div>

    );
});

export default AllUserView;
