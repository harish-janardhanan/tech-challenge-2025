import React, { useState } from 'react';
import Layout from '../components/Layout';
import {useSearchParams} from "react-router-dom";
import {HomeContent} from "../components/HomeContent";
import {observer} from "mobx-react-lite";
import AllUserView from '../modal/AllUserView';
import UserActionsModal from '../modal/UserActionsModal';

const Main: React.FC = observer(() => {
    const [searchParams, setSearchParams] = useSearchParams();
    const view = searchParams.get('view') || 'default';
    const [selectedUser, setSelectedUser] = useState(null);

    const handleSetView = (newView: string, user?: any) => {
        setSearchParams({ view: newView });
        if (user) setSelectedUser(user);
    };

    return (
        <Layout>
            {view === 'default' && <HomeContent/>}
            {view === 'all-users' && (
                <AllUserView setView={handleSetView} selectedUser={selectedUser} setSelectedUser={setSelectedUser} />
            )}
            {view === 'user-actions' && (
                <UserActionsModal user={selectedUser} setView={handleSetView} />
            )}
        </Layout>
    );
});

export default Main;
