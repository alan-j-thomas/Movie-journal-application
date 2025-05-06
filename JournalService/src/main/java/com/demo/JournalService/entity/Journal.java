package com.demo.JournalService.entity;

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
public class Journal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int journalId;
    private int movieId;
    private int userId;
    private String title;
    private String content;
    private String moodTag;

    @Transient
    private List<Movie> movies = new ArrayList<>();
}
