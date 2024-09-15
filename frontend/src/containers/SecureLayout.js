// SecureLayout.js
import React, { useState, useEffect } from 'react';
import Logo from "../components/_common/Logo";
import NavBar from "../components/_common/NavBar";

const SecureLayout = ({ children, navTabs, navBarRef }) => {
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
            {children}
        </div>
    );
};

export default SecureLayout;
