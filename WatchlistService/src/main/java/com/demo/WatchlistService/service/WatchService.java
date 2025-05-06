package com.demo.WatchlistService.service;

import com.demo.WatchlistService.dto.WatchlistRequest;
import com.demo.WatchlistService.entity.WatchList;

import java.util.List;

public interface WatchService {
    WatchList addWatchList(WatchList watchList);
    List<WatchList> getAllWatchLists();
    WatchList getSingleWatchList(int watchId);
    List<WatchList> getWatchListsByUser(int userId);
    WatchList addToWatchList(WatchlistRequest request);
    String deleteFromWatchList(int watchlistId);
    WatchList updateWatchList(int watchId, WatchList watchList);
}
