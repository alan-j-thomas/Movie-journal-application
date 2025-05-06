package com.demo.WatchlistService.controller;

import com.demo.WatchlistService.dto.WatchlistRequest;
import com.demo.WatchlistService.entity.WatchList;
import com.demo.WatchlistService.exceptions.WatchListNotFoundException;
import com.demo.WatchlistService.service.WatchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/watchlist")
public class WatchController {

    @Autowired
    private WatchServiceImpl watchService;

    @PostMapping
    public ResponseEntity<WatchList> addWatchList(@RequestBody WatchList watchList) {
        return new ResponseEntity<>(watchService.addWatchList(watchList), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<WatchList> addToWatchlist(@RequestBody WatchlistRequest request) {
        WatchList saved = watchService.addToWatchList(request);
        return ResponseEntity.ok(saved);
    }


    @GetMapping
    public ResponseEntity<List<WatchList>> getAllWatchLists() {
        return new ResponseEntity<>(watchService.getAllWatchLists(), HttpStatus.OK);
    }

    @GetMapping("/{watchId}")
    public ResponseEntity<WatchList> getSingleWatchList(@PathVariable int watchId) {
        return new ResponseEntity<>(watchService.getSingleWatchList(watchId), HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<WatchList>> getWatchListsByUser(@PathVariable int userId) {
        return new ResponseEntity<>(watchService.getWatchListsByUser(userId), HttpStatus.OK);
    }

    @DeleteMapping("/{watchId}")
    public ResponseEntity<String> deleteFromWatchList(@PathVariable int watchId){
        return new ResponseEntity<>(watchService.deleteFromWatchList(watchId), HttpStatus.OK);
    }

    @PutMapping("/{watchId}")
    public ResponseEntity<WatchList> updateWatchList(@PathVariable int watchId, @RequestBody WatchList watchList){
        return new ResponseEntity<>(watchService.updateWatchList(watchId, watchList), HttpStatus.OK);
    }
}
