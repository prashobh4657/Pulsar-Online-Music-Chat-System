import React, { useState, useCallback, useEffect, useRef } from "react";

// third party
import { connect } from "react-redux";

// utils
import { getReadableTime } from "../utils";

// icons
import { IconContext } from "react-icons";
import { MdPlayCircle, MdPauseCircle } from "react-icons/md";
import { FaHeart, FaRegHeart } from "react-icons/fa";
import { BiSkipNext, BiSkipPrevious } from "react-icons/bi";
import { IoIosShuffle } from "react-icons/io";
import { RiRepeat2Fill, RiRepeatOneFill } from "react-icons/ri";

// config
import { themeColors } from "../config";
import { dummySearchResults, dummyPlaylist } from "../dummy";
import { DashSection } from "./DashSection";
import { BlockContainer } from "./BlockContainer";
import { AdditionalInfoComponent } from "./Info";
const UserDashboard = ({ theme }) => {
  const [currSong, setCurrSong] = useState(dummyPlaylist.songs[1]);
  const [currSongIndex, setCurrSongIndex] = useState(1);
  const [currDuration, setCurrDuration] = useState(0);
  const [isPlaying, setIsPlaying] = useState(false);
  const [isShuffle, setIsShuffle] = useState(false);
  const [isRepeat, setIsRepeat] = useState("all");
  const intervalRef = useRef();

  useEffect(() => {
    handlePlay();
  }, []);

  const handlePlay = () => {
    setIsPlaying(true);
    if (!currSong.id) {
      setCurrSong(dummyPlaylist.songs[0]);
      setCurrSongIndex(0);
    }
  };

  const handlePause = useCallback(() => {
    setIsPlaying(false);
    clearInterval(intervalRef.current);
  }, [intervalRef]);

  const handleSlider = (value) => {
    setCurrDuration(parseInt(value));
  };

  const handleActions = useCallback(
    (action = "next") => {
      switch (action) {
        case "next":
          setCurrSong(
            dummyPlaylist.songs[currSongIndex === 9 ? 0 : currSongIndex + 1]
          );
          setCurrSongIndex(currSongIndex === 9 ? 0 : currSongIndex + 1);
          setCurrDuration(0);
          if (!isPlaying) {
            setIsPlaying(true);
          }
          break;

        case "previous":
          setCurrSong(
            dummyPlaylist.songs[currSongIndex === 0 ? 9 : currSongIndex - 1]
          );
          setCurrSongIndex(currSongIndex === 0 ? 9 : currSongIndex - 1);
          setCurrDuration(0);
          if (!isPlaying) {
            setIsPlaying(true);
          }
          break;

        case "shuffle":
          setIsShuffle(!isShuffle);
          break;

        case "repeat":
          if (isRepeat === "off") {
            setIsRepeat("all");
          } else if (isRepeat === "all") {
            setIsRepeat("one");
          } else {
            setIsRepeat("off");
          }
          break;

        case "like":
          setCurrSong({
            ...currSong,
            liked: !currSong.liked,
          });
          break;

        default:
          break;
      }
    },
    [isPlaying, isShuffle, isRepeat, currSong, currSongIndex]
  );

  const handlePlaySong = (song, index) => {
    setCurrDuration(0);
    setCurrSong(song);
    setCurrSongIndex(index);
    if (!isPlaying) {
      setIsPlaying(true);
    }
  };

  const handleDuration = useCallback(() => {
    if (currDuration === currSong.durationInSec) {
      if (isRepeat === "off" && currSongIndex === 9) {
        setCurrSong({});
        setCurrSongIndex(undefined);
        setCurrDuration(0);
        handlePause();
      } else if (isRepeat === "one") {
        setCurrDuration(0);
      } else {
        handleActions("next");
      }
    } else {
      setCurrDuration(currDuration + 1);
    }
  }, [
    currDuration,
    currSong,
    currSongIndex,
    isRepeat,
    handleActions,
    handlePause,
  ]);

  useEffect(() => {
    if (isPlaying) {
      const id = setInterval(() => {
        handleDuration();
      }, 1000);
      intervalRef.current = id;
    }
    return () => clearInterval(intervalRef.current);
  }, [handleDuration, isPlaying]);

  return (
    <div className="user-dashboard-container">
      <div className="main-view">
        <DashSection>
          <BlockContainer
            header="Top Friends"
            items={dummySearchResults?.profiles || []}
            renderItem={(user, i) => (
              <div theme={theme.color} className="card-container" key={i}>
                <div className="icon friends"></div>
                <div className="user">
                  <div className="name">{user.name}</div>
                </div>
                <AdditionalInfoComponent time={user.time} unreads={user.unreads} />
              </div>
            )}
          />
          <BlockContainer
            header="Top Groups"
            items={dummySearchResults?.groups || []}
            renderItem={(group, i) => (
              <div theme={theme.color} className="card-container" key={i}>
                <div className="icon groups"></div>
                <div className="group">
                  <div className="name">{group.name}</div>
                </div>
                <AdditionalInfoComponent time={group.time} unreads={group.unreads} />
              </div>
            )}
          />
          <BlockContainer
            header="Top Playlists"
            items={dummySearchResults?.playlists || []}
            renderItem={(playlist, i) => (
              <div theme={theme.color} className="card-container" key={i}>
                <div className="icon"></div>
                <div className="playlist">
                  <div className="name">{playlist.name}</div>
                </div>
              </div>
            )}
          />
        </DashSection>
        <DashSection>
          <div className="block-container large">
            <div className="header">Your Favourites</div>
            <div className="content">
              {dummySearchResults?.songs?.slice(0, 6)?.map((song, i) => (
                <div theme={theme.color} className="card-container" key={i}>
                  {isPlaying && song.id === currSong.id ? (
                    <div
                      className={
                        "music-bars" + (isPlaying ? " play" : " pause")
                      }
                    >
                      <div className="bar a"></div>
                      <div className="bar b"></div>
                      <div className="bar c"></div>
                      <div className="bar d"></div>
                    </div>
                  ) : (
                    <div className="num">{i + 1}</div>
                  )}
                  {song.art ? (
                    <img className="icon-art" src={song.art} alt="" />
                  ) : (
                    <div className="icon"></div>
                  )}
                  <div
                    className={
                      "song" + (song.id === currSong.id ? " selected" : "")
                    }
                    onClick={() => handlePlaySong(song, i)}
                  >
                    <div className="name">{song.name}</div>
                    <div className="artist">{song.artist}</div>
                    <div className="album">{song.album}</div>
                    <div className="duration">{song.duration}</div>
                  </div>
                </div>
              ))}
            </div>
          </div>
          <div className="block-container now-playing">
            <div className="content">
              {currSong.art ? (
                <img className="album-art" src={currSong.art} alt="" />
              ) : (
                <div className="placeholder-art"></div>
              )}
              <div className="song-info">
                <div>
                  <div className="name">{currSong.name}</div>
                  <div className="artist">{currSong.artist}</div>
                </div>
                <IconContext.Provider
                  value={{
                    color: themeColors[theme.color],
                    size: "15px",
                    className: "like",
                  }}
                >
                  {currSong.liked ? (
                    <FaHeart onClick={() => handleActions("like")} />
                  ) : (
                    <FaRegHeart onClick={() => handleActions("like")} />
                  )}
                </IconContext.Provider>
              </div>
              <div className={"slider" + (!currSong.id ? " disabled" : "")}>
                <div className="curr-duration">
                  {currSong.id ? getReadableTime(currDuration) : "--:--"}
                </div>
                <input
                  style={{ "--range-theme": themeColors[theme.color] }}
                  className="range"
                  type="range"
                  value={currDuration}
                  min={0}
                  max={currSong?.durationInSec}
                  onChange={(e) => handleSlider(e.target.value)}
                />
                <div className="total-duration">
                  {currSong.duration || "--:--"}
                </div>
              </div>
              <div className="controls">
                <IconContext.Provider
                  value={{
                    color: isShuffle ? themeColors[theme.color] : "#808080",
                    size: "25px",
                    className: "shuffle",
                  }}
                >
                  <IoIosShuffle onClick={() => handleActions("shuffle")} />
                </IconContext.Provider>
                <IconContext.Provider
                  value={{
                    color: themeColors[theme.color],
                    size: "35px",
                    className: currSong.id ? "previous" : "previous disabled",
                  }}
                >
                  <BiSkipPrevious onClick={() => handleActions("previous")} />
                </IconContext.Provider>
                <IconContext.Provider
                  value={{
                    color: themeColors[theme.color],
                    size: "35px",
                    className: "play-pause",
                  }}
                >
                  {isPlaying ? (
                    <MdPauseCircle onClick={handlePause} />
                  ) : (
                    <MdPlayCircle onClick={handlePlay} />
                  )}
                </IconContext.Provider>
                <IconContext.Provider
                  value={{
                    color: themeColors[theme.color],
                    size: "35px",
                    className: currSong.id ? "next" : "next disabled",
                  }}
                >
                  <BiSkipNext onClick={() => handleActions("next")} />
                </IconContext.Provider>
                <IconContext.Provider
                  value={{
                    color:
                      isRepeat === "off" ? "#808080" : themeColors[theme.color],
                    size: "18px",
                    className: "repeat",
                  }}
                >
                  {(isRepeat === "off" || isRepeat === "all") && (
                    <RiRepeat2Fill onClick={() => handleActions("repeat")} />
                  )}
                  {isRepeat === "one" && (
                    <RiRepeatOneFill onClick={() => handleActions("repeat")} />
                  )}
                </IconContext.Provider>
              </div>
            </div>
          </div>
        </DashSection>
      </div>
    </div>
  );
};
export default connect((store) => ({
  theme: store.theme,
}))(UserDashboard);
