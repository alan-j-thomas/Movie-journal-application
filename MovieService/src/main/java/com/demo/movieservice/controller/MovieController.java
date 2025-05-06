package com.demo.movieservice.controller;

import com.demo.movieservice.entity.Movie;
import com.demo.movieservice.service.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieServiceImpl movieService;

    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie){
        return new ResponseEntity<>(movieService.addMovie(movie), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies(){
        return new ResponseEntity<>(movieService.getAllMovies(), HttpStatus.OK);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<Movie> getMovieByTitle(@PathVariable String title) {
        return new ResponseEntity<>(movieService.findMovieByTitle(title), HttpStatus.OK);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<Movie> getMovie(@PathVariable int movieId){
        Movie movie = movieService.getMovie(movieId);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

}
