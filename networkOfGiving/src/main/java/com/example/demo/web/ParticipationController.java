package com.example.demo.web;

import com.example.demo.data.Donation;
import com.example.demo.data.Participation;
import com.example.demo.impl.ParticipationManager;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200",allowedHeaders = "*")
@RestController
@RequestMapping(value = "/participations", produces = MediaType.APPLICATION_JSON_VALUE)
public class ParticipationController {

    final private ParticipationManager participationManager;

    public ParticipationController(ParticipationManager participationManager) {
        this.participationManager = participationManager;
    }

    @GetMapping
    public List<Participation> getAllParticipations()
    {
        return this.participationManager.getAllParticipations();
    }

    @GetMapping("/by_username/{username}")
    public List<Participation> getAllParticipationsByUsername(@PathVariable("username") String username)
    {
        return this.participationManager.getAllParticipationsByUsername(username);
    }

    @GetMapping("/by_charity/{charityId}")
    public List<Participation> getAllParticipationsByCharity(@PathVariable("charityId") int charityId)
    {
        return this.participationManager.getAllParticipationsByCharity(charityId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean addParticipation(@RequestBody Participation participation) {
        return this.participationManager.addParticipation(participation);
    }


    @DeleteMapping("/{charityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteParticipationsToCharity(@PathVariable("charityId") int charityId) {
        this.participationManager.deleteAllParticipationByCharity(charityId);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void taskNotFoundExceptionHandler() {
    }
}
