package com.example.demo.impl;

import com.example.demo.IDonationManager;
import com.example.demo.data.Donation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class DonationManager implements IDonationManager {

    private Repository repository;

    @Autowired
    public DonationManager(Repository repository) {
        this.repository = repository;
    }

    @Override
    public boolean addDonation(Donation donation) {
        return this.repository.addDonation(donation);
    }

    @Override
    public void deleteDonation(String username, int charityId, Date date) {

        this.repository.deleteDonation(username,charityId,date);
    }

    @Override
    public void deleteAllDonationsToCharity(int charityId) {

        this.repository.deleteAllDonationsToCharity(charityId);
    }

    @Override
    public List<Donation> getAllDonations() {
        return this.repository.getAllDonations();
    }

    @Override
    public List<Donation> getAllDonationsByUsernameAndCharity(String username, int charityId) {
        return this.repository.getAllDonationsByUsernameAndCharity(username,charityId);
    }

    @Override
    public List<Donation> getAllDonationsByUsername(String username) {
        return this.repository.getAllDonationsByUsername(username);
    }

    @Override
    public List<Donation> getAllDonationsToCharity(int charityId) {
        return this.repository.getAllDonationsToCharity(charityId);
    }

    @Override
    public List<Donation> getAllDonationsByDate(Date date) {
        return this.repository.getAllDonationsByDate(date);
    }
}

