import { ActionTypes } from "./_types";
import { store } from "../redux/store";
import { dispatchAction, showNotification } from "../utils/index";
// Constants
const API_URL = "http://127.0.0.1:5000/signup";

export const handleSignup = async (payload, navigate) => {
  dispatchAction(ActionTypes.USER_SIGNUP_REQUEST);
  try {
    const response = await fetch(API_URL, {
      method: "POST",
      body: JSON.stringify(payload),
      headers: { "Content-Type": "application/json" },
    });

    const data = await response.json();
    console.log("Signup response:", data);

    if (data.success) {
      handleSignupSuccess(payload.username, data.message, navigate);
    } else {
      handleSignupFailure(data.error);
    }
  } catch (error) {
    console.error("Signup error:", error);
    handleSignupFailure(error);
  }
};

const handleSignupSuccess = (username, message, navigate) => {
  dispatchAction(ActionTypes.USER_SIGNUP_SUCCESS, { username });
  showNotification(message);
  setTimeout(() => navigate("/login"), 500);
};

const handleSignupFailure = (error) => {
  dispatchAction(
    ActionTypes.USER_SIGNUP_FAILURE,
    error.message || "An error occurred"
  );
  showNotification("Signup failed", "error");
};
