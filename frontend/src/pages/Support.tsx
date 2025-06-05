import React from 'react';
import {useSearchParams} from "react-router-dom";
import Layout from "../components/Layout";
import {HomeContent} from "../components/HomeContent";

const Support: React.FC = () => {
    const [searchParams] = useSearchParams();
    const view = searchParams.get('view') || 'default';
    return (
        <Layout>
            {view === 'default' && <HomeContent/>}
        </Layout>
    );
};

export default Support;

