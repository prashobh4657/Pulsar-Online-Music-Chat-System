import { ActionTypes } from "./_types";
import { ENDPOINTS } from "../utils/Constants";
import { dispatchAction, showNotification, apiRequest } from "../utils/index";

export const handleSignup = async (payload, navigate) => {
  dispatchAction(ActionTypes.USER_SIGNUP_REQUEST);
  try {
    console.log("Signup Payload:", payload);
    const data = await apiRequest(ENDPOINTS.SIGNUP, payload);
    console.log("Signup Response:", data);
    console.log("Signup Response:", data.success);

    if (data.success) {
      handleSignupSuccess(payload.userName, data.message, navigate);
    } else {
      handleSignupFailure(data.error);
    }
  } catch (error) {
    handleSignupFailure(error);
  }
};

const handleSignupSuccess = (userName, message, navigate) => {
  dispatchAction(ActionTypes.USER_SIGNUP_SUCCESS, { userName });
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
