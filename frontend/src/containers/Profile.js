import { connect } from "react-redux";
const Profile = ({ theme }) => {
  return (
<div className="profile-container">
  <div className="left-side">
    <div className="profile-picture">
      <img src="/stock/dashboard2.png" alt="Profile pic"/>
    </div>

    <div className="button-container">
      <button className="popup-button">Favourites</button>
      <button className="popup-button">Groups</button>
      <button className="popup-button">Saved Playlists</button>
    </div>
  </div>

  <div className="profile-settings">Profile Settings</div>
</div>
  );
};
export default connect((store) => ({
  theme: store.theme,
}))(Profile);
