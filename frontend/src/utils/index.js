import { store } from "../redux/store";
import { ActionTypes } from "../actions/_types";
import {JAVA_API_BASE_URL,NOTIFICATION_TIMEOUT} from "./Constants"

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

export const apiRequest = async (endpoint, payload, method = 'POST') => {
  const response = await fetch(JAVA_API_BASE_URL+endpoint, {
      method,
      body: JSON.stringify(payload),
      headers: {
          "Content-Type": "application/json",
      },
  });
  const data = await response.json();
  if (!response.ok) {
      throw new Error(data.error || 'An error occurred');
  }
  return data;
};

export const apiGetRequest = async (endpoint) => {
  const response = await fetch(JAVA_API_BASE_URL + endpoint, {
    method: 'GET',
    headers: {
      "Content-Type": "application/json",
    },
  });
  const data = await response.json();
  if (!response.ok) {
    throw new Error(data.error || 'An error occurred');
  }
  return data;
};
