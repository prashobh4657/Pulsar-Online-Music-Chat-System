export const MusicBars = ({ isPlaying }) => (
    <div className={"music-bars" + (isPlaying ? " play" : " pause")}>
      <div className="bar a"></div>
      <div className="bar b"></div>
      <div className="bar c"></div>
      <div className="bar d"></div>
    </div>
  );
  