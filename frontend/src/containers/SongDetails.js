export const SongDetails = ({ song, isSelected, onClick }) => (
    <div className={"song" + (isSelected ? " selected" : "")} onClick={onClick}>
      <div className="name">{song.name}</div>
      <div className="artist">{song.artist}</div>
      <div className="album">{song.album}</div>
      <div className="duration">{song.duration}</div>
    </div>
  );
  