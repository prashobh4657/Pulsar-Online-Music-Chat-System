import { dispatchAction, lS, showNotification } from "../utils/index";
import { ActionTypes } from "./_types";
import { store } from "../redux/store";

const API_URL = "http://127.0.0.1:5000/login";
const NOTIFICATION_TIMEOUT = 3000;

export const handleLogin = async (payload, navigate) => {
  dispatchAction(ActionTypes.USER_LOGIN_REQUEST);
  try {
    const response = await fetch(API_URL, {
      method: "POST",
      body: JSON.stringify(payload),
      headers: {
        "Content-Type": "application/json",
      },
    });
    const data = await response.json();
    if (data.success) {
      handleLoginSuccess(data.data, navigate);
    } else {
      handleLoginFailure(data.error);
    }
  } catch (error) {
    handleLoginFailure(error);
  }
};

export const handleSignout = (navigate) => {
  lS.remove("auth");
  dispatchAction(ActionTypes.USER_SIGNOUT);

  showNotification("Sign out successful");
  setTimeout(() => navigate("/login"), 0);
};

const handleLoginSuccess = (data, navigate) => {
  dispatchAction(ActionTypes.USER_LOGIN_SUCCESS, {
    success: true,
    loginDetail: data,
  });
  lS.set("auth", data);
  showNotification("Login successful");
  setTimeout(() => navigate("/"), 500);
};
const handleLoginFailure = (error) => {
  dispatchAction(
    ActionTypes.USER_LOGIN_FAILURE,
    error.message || "An error occurred"
  );
  showNotification("Login failed", "error");
};
