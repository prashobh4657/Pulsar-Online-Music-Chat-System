import { store } from "../redux/store";
import { ActionTypes } from "../actions/_types";

export const NOTIFICATION_TIMEOUT = 3000;

export const isLoggedin = () => {
  if (lS.get("auth")) {
    return true;
  } else {
    return false;
  }
};

export const lS = {
  get: (key) => {
    const ls =
      localStorage.getItem("DBMS") && JSON.parse(localStorage.getItem("DBMS"));
    if (ls && ls[key]) {
      return ls[key];
    } else {
      return null;
    }
  },
  set: (key, value) => {
    const ls =
      localStorage.getItem("DBMS") && JSON.parse(localStorage.getItem("DBMS"));
    if (ls) {
      ls[key] = value;
      localStorage.setItem("DBMS", JSON.stringify(ls));
    } else {
      let data = {};
      data[key] = value;
      localStorage.setItem("DBMS", JSON.stringify(data));
    }
  },
  remove: (key) => {
    const ls =
      localStorage.getItem("DBMS") && JSON.parse(localStorage.getItem("DBMS"));
    if (ls) {
      delete ls[key];
      localStorage.setItem("DBMS", JSON.stringify(ls));
    }
    return true;
  },
};
window.lS = lS;

export const getReadableTime = (duration) => {
  const minutes = Math.floor(duration / 60);
  const seconds = duration % 60;
  return `${minutes}:${seconds.toString().padStart(2, "0")}`;
};

export const scroll = (config = { top: 0, left: 0 }, el = window) => {
  try {
    el.scroll({
      ...config,
      behavior: "smooth",
    });
  } catch (error) {
    if (el.scroll) {
      el.scroll(config.left, config.top);
    }
    return;
  }
};

export const showNotification = (message, color = "success") => {
  dispatchAction(ActionTypes.SHOW_NOTIFICATION, {
    show: true,
    message,
    timeout: NOTIFICATION_TIMEOUT,
    color,
  });
};

export const dispatchAction = (type, payload = {}) => {
  store.dispatch({
    type,
    payload,
  });
};
