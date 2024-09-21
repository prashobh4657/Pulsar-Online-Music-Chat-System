import { dispatchAction, lS } from "../utils";
import { ActionTypes } from "./_types";

const themeCycle = ['yellow', 'red', 'blue', 'green'];

export const changeTheme = () => {
    const currentTheme = lS.get('theme') || 'red';
    const nextTheme = getNextTheme(currentTheme);
    lS.set('theme', nextTheme);
    dispatchAction(ActionTypes.CHANGE_THEME,{ color: nextTheme })
}

const getNextTheme = (currentTheme) => {
    const currentIndex = themeCycle.indexOf(currentTheme);
    const nextIndex = (currentIndex + 1) % themeCycle.length;
    return themeCycle[nextIndex];
}
