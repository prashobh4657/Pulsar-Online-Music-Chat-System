// actions
import { ActionTypes } from "./_types"

// store
import { store } from "../redux/store"


export const handleSignup = async (payload,navigate) => {
    store.dispatch({
        type: ActionTypes.USER_SIGNUP_REQUEST,
    });
    console.log(payload)
    try {
        const url = "http://127.0.0.1:5000/signup";
        const res = await fetch(url, {
            method: 'POST',
            body: JSON.stringify(payload),
            headers: {
                'Content-Type': 'application/json',
            },
        })
        const resp = await res.json();
        console.log(resp)
        console.log(resp.success)
        if (resp.success) {
            // update store
            store.dispatch({
                type: ActionTypes.USER_SIGNUP_SUCCESS,
                payload: {
                    username: payload.username
                }
            });
            // notification
            store.dispatch({
                type: ActionTypes.SHOW_NOTIFICATION,
                payload: {
                    show: true,
                    message: resp.message,
                    timeout: 3000,
                    color: 'success',
                }
            });
            // Redirect to login after a delay
            setTimeout(() => {
                navigate('/login');
            }, 500);
        } else {
            store.dispatch({
                type: ActionTypes.USER_SIGNUP_FAILURE,
                payload: resp.error
            });
        }
    } catch (error) {
        console.log(error)
        store.dispatch({
            type: ActionTypes.USER_SIGNUP_FAILURE,
            payload: error
        });
    }
}
