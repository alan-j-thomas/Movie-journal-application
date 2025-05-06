package com.demo.movieservice.service;

import com.demo.movieservice.entity.Movie;

import java.util.List;

public interface MovieService {

    Movie addMovie(Movie movie);
    List<Movie> getAllMovies();
    Movie getMovie(int movieId);
    Movie findMovieByTitle(String title);
}
