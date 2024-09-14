import { lS } from "../utils";
import { store } from "../redux/store";
import { ActionTypes } from "./_types";

const themeCycle = ['yellow', 'red', 'blue', 'green'];

export const changeTheme = () => {
    const currentTheme = lS.get('theme') || 'red';
    const nextTheme = getNextTheme(currentTheme);

    lS.set('theme', nextTheme);
    store.dispatch({
        type: ActionTypes.CHANGE_THEME,
        payload: { color: nextTheme }
    });
}

const getNextTheme = (currentTheme) => {
    const currentIndex = themeCycle.indexOf(currentTheme);
    const nextIndex = (currentIndex + 1) % themeCycle.length;
    return themeCycle[nextIndex];
}
