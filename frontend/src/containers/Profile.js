import { connect } from "react-redux";
const Profile = ({ theme }) => {
  return (
    <div className="profile-container">
    <img className="profilePicture" src="/stock/dashboard2.png" height={200} width={350} ></img>
    <div className="button-container">
      <button className="popup-button">Favourites</button>
      <button className="popup-button">Groups</button>
      <button className="popup-button">Saved Playlists</button>
    </div>
    <div className="profile-settings">Profile Settings</div>
  </div>
  );
};
export default connect((store) => ({
  theme: store.theme,
}))(Profile);
