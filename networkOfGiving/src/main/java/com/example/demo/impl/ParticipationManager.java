package com.example.demo.impl;

import com.example.demo.IParticipationManager;
import com.example.demo.data.Participation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ParticipationManager implements IParticipationManager {

    private Repository repository;

    @Autowired
    public ParticipationManager(Repository repository) {
        this.repository = repository;
    }

    @Override
    public boolean addParticipation(Participation participation) {
        return this.repository.addParticipation(participation);
    }

    @Override
    public void deleteParticipation(String username, int charityId, Date date) {

        this.repository.deleteParticipation(username,charityId,date);
    }

    @Override
    public void deleteAllParticipationByCharity(int charityId) {
        this.repository.deleteAllParticipationByCharity(charityId);

    }

    @Override
    public List<Participation> getAllParticipations() {
        return this.repository.getAllParticipations();
    }

    @Override
    public List<Participation> getAllParticipationsByUsernameAndCharity(String username, int charityId) {
        return this.repository.getAllParticipationsByUsernameAndCharity(username, charityId);
    }

    @Override
    public List<Participation> getAllParticipationsByUsername(String username) {
        return this.repository.getAllParticipationsByUsername(username);
    }

    @Override
    public List<Participation> getAllParticipationsByCharity(int charityId) {
        return this.repository.getAllParticipationsByCharity(charityId);
    }

    @Override
    public List<Participation> getAllParticipationsByDate(Date date) {
        return this.repository.getAllParticipationsByDate(date);
    }
}
