package com.demo.JournalService.controller;

import com.demo.JournalService.dto.JournalRequest;
import com.demo.JournalService.entity.Journal;
import com.demo.JournalService.service.JournalServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/journals")
public class JournalController {

    @Autowired
    private JournalServiceImpl journalService;

    @PostMapping
    public ResponseEntity<Journal> addJournal(@RequestBody Journal journal){
        return new ResponseEntity<>(journalService.addJournal(journal), HttpStatus.CREATED);
    }

    @PostMapping("/add")
    public ResponseEntity<Journal> addToJournal(@RequestBody JournalRequest request){
        return new ResponseEntity<>(journalService.addToJournal(request), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Journal>> getAllJournals(){
        return new ResponseEntity<>(journalService.getAllJournals(), HttpStatus.OK);
    }

    @GetMapping("/{journalId}")
    public ResponseEntity<Journal> getJournal(@PathVariable int journalId){
        return new ResponseEntity<>(journalService.getJournal(journalId), HttpStatus.OK);
    }

    @PutMapping("/{journalId}")
    public ResponseEntity<Journal> updateJournal(@PathVariable int journalId, @RequestBody JournalRequest journal){
        return new ResponseEntity<>(journalService.updateJournal(journalId, journal), HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Journal>> getJournalByUser(@PathVariable int userId) {
        return new ResponseEntity<>(journalService.getJournalsByUser(userId), HttpStatus.OK);
    }

    @DeleteMapping("/{journalId}")
    public ResponseEntity<String> deleteJournal(@PathVariable int journalId){
        return new ResponseEntity<>(journalService.deleteJournal(journalId), HttpStatus.OK);
    }
}
