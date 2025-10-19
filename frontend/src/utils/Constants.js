export const API_BASE_URL="http://127.0.0.1:5000";
export const JAVA_API_BASE_URL="http://localhost:8080";

export const NOTIFICATION_TIMEOUT = 3000;
export const ENDPOINTS = {
    LOGIN: '/v1/api/login',
    SIGNUP: '/v1/api/signup',
    USER_TABLE : '/data/user_info',
    TOP_FRIENDS : (userId) => `/top-friends/${userId}`,
    TOP_GROUPS : (userId) => `/top-groups/${userId}`,
}