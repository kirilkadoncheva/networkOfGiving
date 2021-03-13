package com.example.demo.data;

import java.util.Date;


public class Donation {
    String donorUsername;
    int charityId;
    double amount;
    Date placeDate;

    public Donation(String donorUsername, int charityId, double amount, Date placeDate) {
        this.donorUsername = donorUsername;
        this.charityId = charityId;
        this.amount = amount;
        this.placeDate = placeDate;
    }

    public String getDonorUsername() {
        return donorUsername;
    }

    public void setDonorUsername(String donorUsername) {
        this.donorUsername = donorUsername;
    }

    public int getCharityId() {
        return charityId;
    }

    public void setCharityId(int charityId) {
        this.charityId = charityId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getPlaceDate() {
        return placeDate;
    }

    public void setPlaceDate(Date placeDate) {
        this.placeDate = placeDate;
    }
}
