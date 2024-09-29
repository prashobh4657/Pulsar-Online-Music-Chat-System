import { ActionTypes } from "./_types";
import { ENDPOINTS } from "../utils/Constants";
import { dispatchAction, showNotification, apiRequest } from "../utils/index";

export const handleSignup = async (payload, navigate) => {
  dispatchAction(ActionTypes.USER_SIGNUP_REQUEST);
  try {
    const data = await apiRequest(ENDPOINTS.SIGNUP, payload);
    if (data.success) {
      handleSignupSuccess(payload.username, data.message, navigate);
    } else {
      handleSignupFailure(data.error);
    }
  } catch (error) {
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
