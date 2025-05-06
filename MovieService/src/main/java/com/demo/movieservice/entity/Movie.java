package com.demo.movieservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int movieId;
    private String title;
    private String genre;
    private String posterUrl;
    private int releaseYear;
}
