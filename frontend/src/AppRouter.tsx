import React from 'react';
import {BrowserRouter, Navigate, Route, Routes} from 'react-router-dom';
import {observer} from 'mobx-react-lite';
import SignIn from './pages/SignIn';
import Main from './pages/Main';
import userStore from './stores/userStore';
import TraderSales from "./pages/TraderSales";
import MiddleOffice from "./pages/MiddleOffice";
import Support from "./pages/Support";
import Admin from "./pages/Admin";

const AppRouter = observer(() => (
    <BrowserRouter>
        <Routes>
            <Route path="/signin" element={<SignIn/>}/>
            <Route path="/home" element={<Main/>}/>
            <Route path="/trade" element={<TraderSales/>}/>
            <Route path="/middle-office" element={<MiddleOffice/>}/>
            <Route path="/support" element={<Support/>}/>
            <Route path="/admin" element={<Admin/>}/>
            <Route
                path="*"
                element={
                    userStore.authenticated ? <Navigate to="/home" replace/> : <Navigate to="/signin" replace/>
                }
            />
        </Routes>
    </BrowserRouter>
));

export default AppRouter;