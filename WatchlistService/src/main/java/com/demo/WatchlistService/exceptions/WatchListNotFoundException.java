package com.demo.WatchlistService.exceptions;

public class WatchListNotFoundException extends RuntimeException{

    public WatchListNotFoundException() {
        super("Watchlist Not Found!");
    }

    public WatchListNotFoundException(String message) {
        super(message);
    }
}
