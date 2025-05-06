package com.demo.JournalService.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    private int movieId;
    private String title;
    private String genre;
    private String posterUrl;
    private int releaseYear;
}
