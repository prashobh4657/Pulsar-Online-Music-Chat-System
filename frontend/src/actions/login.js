import { lS } from "../utils"
import { ActionTypes } from "./_types"
import { store } from "../redux/store"

const API_URL = "http://127.0.0.1:5000/login";
const NOTIFICATION_TIMEOUT = 3000;

export const handleLogin = async (payload,navigate) => {
    store.dispatch({type: ActionTypes.USER_LOGIN_REQUEST,});
    try {
        const response = await fetch(API_URL, {
            method: 'POST',
            body: JSON.stringify(payload),
            headers: {
                'Content-Type': 'application/json',
            },
        })
        const data = await response.json();
        if (data.success) {
            handleLoginSuccess(data.data, navigate);
        } else {
            handleLoginFailure(data.error);
        }
    } catch (error) {
        handleLoginFailure(error);
    }
}

const handleLoginSuccess = (data, navigate) => {
    store.dispatch({
        type: ActionTypes.USER_LOGIN_SUCCESS,
        payload: { success: true, loginDetail: data }
    });
    lS.set('auth', data);
    showNotification('Login successful');
    setTimeout(() => navigate('/'), 500);
};
const handleLoginFailure = (error) => {
    store.dispatch({
        type: ActionTypes.USER_LOGIN_FAILURE,
        payload: error.message || 'An error occurred'
    });
    showNotification('Login failed', 'error');
};
export const handleSignout = (navigate) => {
    lS.remove('auth');
    store.dispatch({ type: ActionTypes.USER_SIGNOUT });
    showNotification('Sign out successful');
    setTimeout(() => navigate('/login'), 0);
};

const showNotification = (message, color = 'success') => {
    store.dispatch({
        type: ActionTypes.SHOW_NOTIFICATION,
        payload: { show: true, message, timeout: NOTIFICATION_TIMEOUT, color }
    });
};
