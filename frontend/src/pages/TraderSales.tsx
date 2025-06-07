import React from 'react';
import {useSearchParams} from "react-router-dom";
import Layout from "../components/Layout";
import {HomeContent} from "../components/HomeContent";
import {TradeBlotterModal} from "../modal/TradeBlotterModal";

const TraderSales = () => {

    const [searchParams] = useSearchParams();
    const view = searchParams.get('view') || 'default';
    return (
        <Layout>
            {view === 'default' && <HomeContent/>}
            {view === 'blotter' && <TradeBlotterModal/>}
        </Layout>
    );
};

export default TraderSales;

