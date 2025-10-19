import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import Dashboard from '../containers/Dashboard';
import Login from '../containers/Login';
import Signup from '../containers/Signup';

const NotSecureRoutes = () => {
    return (
        <Routes>
            <Route path="/" element={<Dashboard />} />
            <Route path="/login" element={<Login />} />
            <Route path="/v1/api/signup" element={<Signup />} />
            <Route path="*" element={<Navigate to="/login" />} />
        </Routes>
    );
};
export default NotSecureRoutes;
