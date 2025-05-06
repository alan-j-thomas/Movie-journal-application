package com.demo.JournalService.service;

import com.demo.JournalService.dto.JournalRequest;
import com.demo.JournalService.entity.Journal;
import com.demo.JournalService.entity.Movie;
import com.demo.JournalService.exceptions.JournalNotFoundException;
import com.demo.JournalService.exceptions.MovieNotFoundException;
import com.demo.JournalService.external.MovieClient;
import com.demo.JournalService.repository.JournalRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class JournalServiceImpl implements JournalService{

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private MovieClient movieClient;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public Journal addJournal(Journal journal) {
        return journalRepository.save(journal);
    }

    @Override
    public Journal addToJournal(JournalRequest request) {
        Movie movie = restTemplate.getForObject(
                "http://localhost:8082/movie/title/" + request.getMovieTitle(),
                Movie.class
        );

        if (movie == null || movie.getMovieId() == 0) {
            throw new MovieNotFoundException();
        }

        Journal journal = new Journal();
        journal.setUserId(request.getUserId());
        journal.setMovieId(movie.getMovieId());
        journal.setMoodTag(request.getMoodTag());
        journal.setTitle(request.getTitle());
        journal.setContent(request.getContent());


        return journalRepository.save(journal);
    }

    @Override
    public List<Journal> getAllJournals() {
        List<Journal> allJournals = journalRepository.findAll();

        for (Journal journal : allJournals) {
            try {
                if (journal.getMovieId() > 0) {  // extra safety
                    journal.setMovies(List.of(movieClient.getMovies(journal.getMovieId())));
                } else {
                    journal.setMovies(List.of()); // No movie if movieId is null or invalid
                }
            } catch (FeignException e) {
                System.out.println("Error fetching movie for journal ID: " + journal.getJournalId() + ", movieId: " + journal.getMovieId() + " - " + e.getMessage());
                journal.setMovies(List.of()); // Set empty list in case of error
            }
        }

        return allJournals;
    }


    @Override
    public Journal getJournal(int journalId) {
        Journal journal = journalRepository.findById(journalId).orElseThrow(JournalNotFoundException::new);
        journal.setMovies(List.of(movieClient.getMovies(journal.getMovieId())));
        return journal;
    }

    @Override
    public List<Journal> getJournalsByUser(int userId) {
        return journalRepository.getJournalsByUserId(userId);
    }

    @Override
    public Journal updateJournal(int journalId, JournalRequest journal) {
        Journal oldJournal = journalRepository.getReferenceById(journalId);
        if(oldJournal != null){
            oldJournal.setTitle(journal.getTitle());
            oldJournal.setContent(journal.getContent());
            oldJournal.setMoodTag(journal.getMoodTag());
            oldJournal.setUserId(journal.getUserId());



            Journal updated = journalRepository.save(oldJournal);
            oldJournal.setMovieId(updated.getMovieId());

            return updated;
        }
        return null;
    }

    @Override
    public String deleteJournal(int journalId) {
        Journal oldJournal = journalRepository.findById(journalId).orElseThrow(JournalNotFoundException::new);
        journalRepository.delete(oldJournal);
        return "Journal deleted Successfully!";
    }

}
