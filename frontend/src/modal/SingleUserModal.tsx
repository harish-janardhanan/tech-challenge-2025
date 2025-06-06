import React from "react";
import {observer} from "mobx-react-lite";
import CloseButton from "../components/CloseButton";
import InputRow from "../components/InputRow";
import Button from "../components/Button";
import Label from "../components/Label";
import Dropdown from "../components/Dropdown";
import staticStore, {useUserTypesQuery} from "../stores/staticStore";
import {ApplicationUser} from "../utils/ApplicationUser";

interface UserDetailModalProps {
    isOpen: boolean;
    onClose: () => void;
    onClickSave: () => void;
    user: ApplicationUser | null;
    setUser: React.Dispatch<React.SetStateAction<ApplicationUser | null>>;
}

export const SingleUserModal: React.FC<UserDetailModalProps> = observer((props) => {
    useUserTypesQuery();
    const { user, setUser } = props;
    if (!user) return null;

    // Generic handler for all fields
    const handleFieldChange = (field: keyof ApplicationUser, value: unknown) => {
        setUser(u => u ? { ...u, [field]: value } : u);
    };

    // Determine if we are in edit mode (user has an id)
    const isEditMode = !!user.id;

    return (
        <>
            <div
                className={`fixed inset-0 z-50 flex items-center justify-center transition-opacity duration-300 ${props.isOpen ? 'opacity-100 pointer-events-auto' : 'opacity-0 pointer-events-none'}`}>

                <div className="absolute inset-0 bg-black/30 transition-opacity duration-300" onClick={props.onClose}></div>

                <div
                    className={`relative bg-indigo-50 w-fit rounded-lg shadow-lg p-6 transition-all duration-300 transform ${props.isOpen ? 'translate-y-0 opacity-100' : '-translate-y-10 opacity-0'}`}
                >
                    {/* Close button */}
                    <CloseButton onClick={props.onClose}/>
                    <h2 className={"text-2xl font-semibold justify-center"}>Add or Edit User</h2>
                    <div className={" flex flex-col  justify-start mt-2 font-semibold"}>
                        <InputRow label={"First Name"} inputProps={{
                            value: user.firstName,
                            onChange: e => handleFieldChange("firstName", e.target.value)
                        }}/>
                        <InputRow label={"Last Name"} inputProps={{
                            value: user.lastName,
                            onChange: e => handleFieldChange("lastName", e.target.value)
                        }}/>
                        <InputRow label={"User Id"} inputProps={{
                            value: user.loginId,
                            onChange: e => handleFieldChange("loginId", e.target.value)
                        }}/>
                        {isEditMode? null: <InputRow label={"Password"} inputProps={{
                            type: "password",
                            value: isEditMode ? '' : (user.password || ''),
                            onChange: e => handleFieldChange("password", e.target.value),
                            placeholder: isEditMode ? "(hidden)" : ""
                        }}/> }

                        <InputRow label={"Active"} inputProps={{
                            type: "checkbox",
                            checked: user.active,
                            onChange: e => handleFieldChange("active", e.target.checked)
                        }}/>
                        <div className="mb-4 flex flex-row">
                            <Label htmlFor={"User Profile"}>User Profile</Label>
                            <Dropdown id={"user-type"}
                                      value={user.userProfile}
                                      placeholder={"Select"}
                                      onChange={e => handleFieldChange("userProfile", e.currentTarget.value)}
                                      options={(staticStore.userTypeCache ?? []).map(u => ({
                                value: u.userType,
                                label: u.userType
                            }))}/>
                        </div>

                    </div>
                    <div className={"flex-row flex justify-end mt-2 gap-x-2"}>
                        <Button variant={"primary"} size={"sm"} onClick={() => props.onClickSave()}>Save</Button>
                        <Button variant={"primary"} size={"sm"} className={"!bg-gray-500 hover:!bg-gray-700"}
                                type={"button"} onClick={props.onClose}>Cancel</Button>
                    </div>
                </div>
            </div>
        </>
    )
})