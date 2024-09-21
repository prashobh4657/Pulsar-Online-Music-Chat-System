import { ActionTypes } from "./_types";
import {API_BASE_URL,ENDPOINTS} from "../utils/Constants"
import { dispatchAction, showNotification } from "../utils/index";


export const handleSignup = async (payload, navigate) => {
  dispatchAction(ActionTypes.USER_SIGNUP_REQUEST);
  try {
    const response = await fetch(API_BASE_URL+ENDPOINTS.SIGNUP, {
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
