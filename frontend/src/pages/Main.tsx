import React, {useEffect} from 'react';
import Layout from '../components/Layout';
import {useSearchParams} from "react-router-dom";
import {HomeContent} from "../components/HomeContent";
import staticStore, {useUserTypesQuery} from "../stores/staticStore";

const Main: React.FC = () => {
    const [searchParams] = useSearchParams();
    const view = searchParams.get('view') || 'default';

    return (
        <Layout>
            {view === 'default' && <HomeContent/>}
        </Layout>
    );
};

export default Main;
