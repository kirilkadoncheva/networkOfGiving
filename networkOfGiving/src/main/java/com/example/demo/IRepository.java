package com.example.demo;

import com.example.demo.data.Charity;
import com.example.demo.data.Donation;
import com.example.demo.data.Participation;

import java.util.Date;
import java.util.List;

public interface IRepository {
    int addCharity(Charity charity);
    void deleteCharity(int id);
    //boolean updateCharity(int id);
    List<Charity> getAllCharities();
    Charity getCharityById(int id);
    List<Charity> getCharitiesByName(String name);
    void updateCharity(int id, Charity charity);
    // List<Charity> getNMostPopularCharities(int n);
    List<Charity> getAllCharitiesByOwner(String username);
    Double estimateDonationToCharityById(int id);
    List<Charity> getAllDonatedToCharitiesByUsername(String username);
    List<Charity> getAllParticipatedCharitiesByUsername(String username);


    public boolean addDonation(Donation donation);
    public void deleteDonation(String username, int charityId, Date date);
    public void deleteAllDonationsToCharity(int charityId);
    public List<Donation> getAllDonations();
    public List<Donation> getAllDonationsByUsernameAndCharity(String username, int charityId);
    public List<Donation> getAllDonationsByUsername(String username);
    public List<Donation> getAllDonationsToCharity(int charityId);
    public List<Donation> getAllDonationsByDate(Date date);

    public boolean addParticipation(Participation participation);
    public void deleteParticipation(String username, int charityId, Date date);
    public void deleteAllParticipationByCharity(int charityId);
    public List<Participation> getAllParticipations();
    public List<Participation> getAllParticipationsByUsernameAndCharity(String username, int charityId);
    public List<Participation> getAllParticipationsByUsername(String username);
    public List<Participation> getAllParticipationsByCharity(int charityId);
    public List<Participation> getAllParticipationsByDate(Date date);
}
