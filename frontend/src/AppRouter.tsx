import React from 'react';
import {BrowserRouter, Navigate, Route, Routes} from 'react-router-dom';
import {observer} from 'mobx-react-lite';
import SignIn from './pages/SignIn';
import Home from './pages/Home';
import userStore from './stores/userStore';

const AppRouter = observer(() => (
    <BrowserRouter>
        <Routes>
            <Route path="/signin" element={<SignIn/>}/>
            <Route path="/home" element={<Home/>}/>
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