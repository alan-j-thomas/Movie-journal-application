package com.demo.movieservice.service;

import com.demo.movieservice.entity.Movie;
import com.demo.movieservice.exceptions.MovieNotFoundException;
import com.demo.movieservice.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

@Service
public class MovieServiceImpl implements MovieService{

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Movie getMovie(int movieId) {
        return movieRepository.findById(movieId).orElseThrow(MovieNotFoundException::new);
    }

    @Override
    public Movie findMovieByTitle(String title) {
        return movieRepository.findMovieByTitleIgnoreCase(title).orElseThrow(MovieNotFoundException::new);

    }
}
