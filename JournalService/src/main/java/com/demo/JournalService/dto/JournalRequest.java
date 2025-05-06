package com.demo.JournalService.dto;

import lombok.Data;

@Data
public class JournalRequest {

    private String movieTitle;
    private int userId;
    private String title;
    private String content;
    private String moodTag;
    private int movieId;
}
