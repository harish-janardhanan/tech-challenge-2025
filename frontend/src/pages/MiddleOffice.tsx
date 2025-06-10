import React from 'react';
import {useSearchParams} from "react-router-dom";
import Layout from "../components/Layout";
import {HomeContent} from "../components/HomeContent";
import SingleTradeModal from "../modal/SingleTradeModal";
import {TradeActionsModal} from "../modal/TradeActionsModal";

const MiddleOffice = () => {
    const [searchParams] = useSearchParams();
    const view = searchParams.get('view') || 'default';
    return (
        <Layout>
            {view === 'default' && <HomeContent/>}
            {view === 'actions' && <TradeActionsModal /> }
        </Layout>
    );
};

export default MiddleOffice;

