import { SongDetails } from "./SongDetails";
import { MusicBars } from "./MusicBars";
export const SongCard = ({
    song,
    index,
    isPlaying,
    currSong,
    theme,
    handlePlaySong
  }) => (
    <div theme={theme.color} className="card-container" key={index}>
      {isPlaying && song.id === currSong.id ? (
        <MusicBars isPlaying={isPlaying} />
      ) : (
        <div className="num">{index + 1}</div>
      )}
      {song.art ? (
        <img className="icon-art" src={song.art} alt="" />
      ) : (
        <div className="icon"></div>
      )}
      <SongDetails
        song={song}
        isSelected={song.id === currSong.id}
        onClick={() => handlePlaySong(song, index)}
      />
    </div>
  );
  