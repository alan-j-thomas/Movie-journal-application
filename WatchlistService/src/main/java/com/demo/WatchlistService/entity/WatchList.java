package com.demo.WatchlistService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class WatchList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int watchId;
    private int userId;
    private int movieId;
    private Status status;
    private String note;

    @Transient
    private List<Movie> movies = new ArrayList<>();
}
