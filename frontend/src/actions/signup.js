import { ActionTypes } from "./_types";
import { store } from "../redux/store";

// Constants
const API_URL = "http://127.0.0.1:5000/signup";
const NOTIFICATION_TIMEOUT = 3000;

export const handleSignup = async (payload, navigate) => {
    store.dispatch({ type: ActionTypes.USER_SIGNUP_REQUEST });

    try {
        const response = await fetch(API_URL, {
            method: 'POST',
            body: JSON.stringify(payload),
            headers: { 'Content-Type': 'application/json' },
        });

        const data = await response.json();
        console.log('Signup response:', data);

        if (data.success) {
            handleSignupSuccess(payload.username, data.message, navigate);
        } else {
            handleSignupFailure(data.error);
        }
    } catch (error) {
        console.error('Signup error:', error);
        handleSignupFailure(error);
    }
};

const showNotification = (message, color = 'success') => {
    store.dispatch({
        type: ActionTypes.SHOW_NOTIFICATION,
        payload: { show: true, message, timeout: NOTIFICATION_TIMEOUT, color }
    });
};

const handleSignupSuccess = (username, message, navigate) => {
    store.dispatch({
        type: ActionTypes.USER_SIGNUP_SUCCESS,
        payload: { username }
    });
    showNotification(message);
    setTimeout(() => navigate('/login'), 500);
};

const handleSignupFailure = (error) => {
    store.dispatch({
        type: ActionTypes.USER_SIGNUP_FAILURE,
        payload: error.message || 'An error occurred'
    });
    showNotification('Signup failed', 'error');
};
