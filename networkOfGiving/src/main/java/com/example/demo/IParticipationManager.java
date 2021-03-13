package com.example.demo;

import com.example.demo.data.Participation;

import java.util.Date;
import java.util.List;

public interface IParticipationManager {
    public boolean addParticipation(Participation participation);
    public void deleteParticipation(String username, int charityId, Date date);
    public void deleteAllParticipationByCharity(int charityId);
    public List<Participation> getAllParticipations();

    public List<Participation> getAllParticipationsByUsernameAndCharity(String username, int charityId);
    public List<Participation> getAllParticipationsByUsername(String username);
    public List<Participation> getAllParticipationsByCharity(int charityId);
    public List<Participation> getAllParticipationsByDate(Date date);
}
