package com.example.demo;

import com.example.demo.data.Donation;

import java.util.Date;
import java.util.List;

public interface IDonationManager {
    public boolean addDonation(Donation donation);
    public void deleteDonation(String username, int charityId, Date date);
    public void deleteAllDonationsToCharity(int charityId);
    public List<Donation> getAllDonations();

    public List<Donation> getAllDonationsByUsernameAndCharity(String username, int charityId);
    public List<Donation> getAllDonationsByUsername(String username);
    public List<Donation> getAllDonationsToCharity(int charityId);
    public List<Donation> getAllDonationsByDate(Date date);
}
