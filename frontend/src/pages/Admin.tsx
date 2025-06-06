import React from 'react';
import {useSearchParams} from "react-router-dom";
import Layout from "../components/Layout";
import {HomeContent} from "../components/HomeContent";
import {UserDetailsModal} from "../modal/UserDetailsModal";

const Admin: React.FC = () => {
    const [searchParams] = useSearchParams();
    const view = searchParams.get('view') || 'default';
    return (
        <Layout>
            {view === 'default' && <HomeContent/>}
            {view === 'user-list' && <UserDetailsModal/>}
        </Layout>
    );
};

export default Admin;
