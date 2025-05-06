package com.demo.WatchlistService.service;

import com.demo.WatchlistService.dto.WatchlistRequest;
import com.demo.WatchlistService.entity.Movie;
import com.demo.WatchlistService.entity.WatchList;
import com.demo.WatchlistService.exceptions.MovieNotFoundException;
import com.demo.WatchlistService.exceptions.WatchListNotFoundException;
import com.demo.WatchlistService.external.MovieClient;
import com.demo.WatchlistService.repository.WatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WatchServiceImpl implements WatchService{

    @Autowired
    private WatchRepository watchRepository;

    @Autowired
    private MovieClient movieClient;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public WatchList addWatchList(WatchList watchList) {
        return watchRepository.save(watchList);
    }

    @Override
    public List<WatchList> getAllWatchLists() {
        List<WatchList> allWatchLists = watchRepository.findAll();

        return allWatchLists.stream().map(watchList -> {
            Movie movie = movieClient.getMovies(watchList.getMovieId());
            watchList.setMovies(List.of(movie)); // wrap in list if your WatchList expects a list

            return watchList;
        }).collect(Collectors.toList());
    }

    @Override
    public WatchList getSingleWatchList(int watchId) {
        WatchList watchList = watchRepository.findById(watchId).orElseThrow(WatchListNotFoundException::new);
        Movie movie = movieClient.getMovies(watchList.getMovieId());
        watchList.setMovies(List.of(movie)); // wrap in list if your WatchList expects a list


        return watchList;
    }

    @Override
    public List<WatchList> getWatchListsByUser(int userId) {

        List<WatchList> watchLists = watchRepository.getWatchListsByUserId(userId);

        return watchLists.stream().map(watchList -> {
            Movie movie = movieClient.getMovies(watchList.getMovieId());
            watchList.setMovies(List.of(movie)); // assuming movies is a list
            return watchList;
        }).collect(Collectors.toList());
    }

    @Override
    public WatchList addToWatchList(WatchlistRequest request) {
        // Call MovieService to get movieId by title
        Movie movie = restTemplate.getForObject(
                "http://MOVIE-SERVICE/movie/title/" + request.getMovieTitle(),
                Movie.class
        );

        if (movie == null || movie.getMovieId() == 0) {
            throw new MovieNotFoundException();
        }

        WatchList watchList = new WatchList();
        watchList.setUserId(request.getUserId());
        watchList.setMovieId(movie.getMovieId());
        watchList.setStatus(request.getStatus());
        watchList.setNote(request.getNote());

        return watchRepository.save(watchList);
    }

    @Override
    public String deleteFromWatchList(int watchlistId) {
        WatchList watchList = watchRepository.findById(watchlistId).orElseThrow(WatchListNotFoundException::new);
        watchRepository.delete(watchList);
        return "Deleted!";
    }

    @Override
    public WatchList updateWatchList(int watchId, WatchList watchList) {
        WatchList watchList1 = watchRepository.findById(watchId).orElseThrow(WatchListNotFoundException::new);
        watchList1.setStatus(watchList.getStatus());
        watchList1.setNote(watchList.getNote());
        return watchRepository.save(watchList1);
    }
}
