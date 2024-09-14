import React, { useEffect, useState, useRef, useCallback } from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { connect } from 'react-redux';
import './css/main.scss';

// Containers
import Dashboard from "./containers/Dashboard";
import Login from "./containers/Login";
import Signup from "./containers/Signup";
import UserDashboard from "./containers/UserDashboard";
import Search from "./containers/Search";
import Friends from "./containers/Friends";
import Groups from "./containers/Groups";
import Playlists from "./containers/Playlists";

// Components
import Logo from "./components/_common/Logo";
import NavBar from "./components/_common/NavBar";
import Notification from "./components/_common/Notification";

// Config
import { dashboardNavBarTabs, themeColors } from "./config";

const App = ({ login, theme }) => {
    const { success } = login;
    const navBarRef = useRef();

    const handleCloseDropdown = useCallback(() => {
        navBarRef.current?.closeDropdown();
    }, []);

    return (
        <div className="app" style={{ "--theme": themeColors[theme.color] }} onClick={handleCloseDropdown}>
            <Notification />
            <Router>
                <Routes>
                    {!success ? (
                        <>
                            <Route path="/" element={<Dashboard />} />
                            <Route path="/login" element={<Login />} />
                            <Route path="/signup" element={<Signup />} />
                            <Route path="*" element={<Navigate to="/login" />} />
                        </>
                    ) : (
                        <Route
                            path="*"
                            element={
                                <SecureRoutes
                                    navTabs={getNavTabs(login)}
                                    navBarRef={navBarRef}
                                />
                            }
                        />
                    )}
                </Routes>
            </Router>
        </div>
    );
};

// Helper function to generate navTabs
const getNavTabs = (login) => {
    return dashboardNavBarTabs.slice(0, -2).concat([
        {
            label: login?.user?.fullname || 'Sign Out',
            value: 'signout',
            path: '/',
        },
    ]);
};

const SecureRoutes = ({ navTabs, navBarRef }) => {
    const [selectedTab, setSelectedTab] = useState(navTabs[1]?.value || '');

    useEffect(() => {
        const matchingTab = navTabs.find((tab) => tab.path === window.location.pathname);
        setSelectedTab(matchingTab ? matchingTab.value : navTabs[1]?.value || '');
    }, [navTabs]);

    return (
        <div className="layout-container">
            <div className="site-header">
                <Logo />
                <NavBar
                    tabs={navTabs}
                    selectedTab={selectedTab}
                    switchTab={(tab) => setSelectedTab(tab.value)}
                    classes="secure"
                    showSearch
                    connectedRef={navBarRef}
                />
            </div>
            <Routes>
                <Route path="/" element={<UserDashboard />} />
                <Route path="/search" element={<Search />} />
                <Route path="/friends" element={<Friends />} />
                <Route path="/groups" element={<Groups />} />
                <Route path="/playlists" element={<Playlists />} />
                <Route path="*" element={<Navigate to="/" />} />
            </Routes>
        </div>
    );
};

// Connect to Redux store
export default connect((store) => ({
    login: store.login,
    theme: store.theme,
}))(App);
