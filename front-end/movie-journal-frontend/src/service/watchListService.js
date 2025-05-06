import axios from "axios";

const REST_API_BASE_URL = "http://localhost:8084/watchlist";

export const getWatchlistByUserId = (userId) => {
    return axios.get(`${REST_API_BASE_URL}/users/${userId}`);
}
export const addToWatchlist = (data) => {
    return axios.post(`${REST_API_BASE_URL}`, data);
}

export const getWatchLists = () => {
    return axios.get(`${REST_API_BASE_URL}`)
}

export const updateWatchlist = (id, data) => {
    return axios.put(`${REST_API_BASE_URL}/${id}`, data);
}

export const deleteWatchlist = (id) => {
    return axios.delete(`${REST_API_BASE_URL}/${id}`);
}


