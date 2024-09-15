import React, {useRef, useCallback } from "react";
import { BrowserRouter as Router} from 'react-router-dom';
import { connect } from 'react-redux';
import './css/main.scss';

// Components
import Notification from "./components/_common/Notification";
import SecureRoutes from "./containers/SecureRoutes.js";
import SecureLayout from "./containers/SecureLayout"; 
import NotSecureRoutes from "./containers/NotSecureRoutes.js";

// Config
import { dashboardNavigationBarTabs, themeColors } from "./config";

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
                    {!success ? (
                        <NotSecureRoutes/>
                    ) : (
                        <SecureLayout navTabs={dashboardNavigationBarTabs(login)} navBarRef={navBarRef}>
                        <SecureRoutes />
                        </SecureLayout>
                    )}
            </Router>
        </div>
    );
};


// Connect to Redux store
export default connect((store) => ({
    login: store.login,
    theme: store.theme,
}))(App);
