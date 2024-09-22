import { Routes, Route, Navigate } from 'react-router-dom';
import UserDashboard from "./UserDashboard";
import Search from "./Search";
import Friends from "./Friends";
import Groups from "./Groups";
import Playlists from "./Playlists";
import Profile from "./Profile";


const SecureRoutes = () => {
    return (
            <Routes>
                <Route path="/" element={<UserDashboard />} />
                <Route path="/search" element={<Search />} />
                <Route path="/friends" element={<Friends />} />
                <Route path="/groups" element={<Groups />} />
                <Route path="/playlists" element={<Playlists />} />
                <Route path="/profile" element={<Profile />} />
                <Route path="*" element={<Navigate to="/" />} />
            </Routes>
    );
};
export default SecureRoutes;
