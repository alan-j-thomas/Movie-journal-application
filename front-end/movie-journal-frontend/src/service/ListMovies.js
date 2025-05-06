import axios from "axios"
const REST_API_BASE_URL = "http://localhost:8082/movie";

export const listMovies = () => {
    return axios.get(REST_API_BASE_URL);
}

export const getMovieById = (id) => {
    return axios.get(`${REST_API_BASE_URL}/${id}`);
}

export const getMovieByTitle = (title) => {
    return axios.get(`${REST_API_BASE_URL}/title/${title}`)
}
