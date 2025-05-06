package com.demo.WatchlistService.external;

import com.demo.WatchlistService.entity.Movie;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "MOVIE-SERVICE")
public interface MovieClient {

    @GetMapping("/movie/{movieId}")
    Movie getMovies(@PathVariable int movieId);
//
//    @GetMapping("/movie/specific/{movieId}")
//    Movie getMovie(@PathVariable int movieId);
}
