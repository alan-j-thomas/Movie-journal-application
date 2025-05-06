package com.demo.WatchlistService.dto;

import com.demo.WatchlistService.entity.Status;
import lombok.Data;

@Data
public class WatchlistRequest {

    private String movieTitle;
    private int userId;
    private Status status;
    private String note;
}
