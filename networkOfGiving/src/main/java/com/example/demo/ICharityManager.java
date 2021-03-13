package com.example.demo;

import com.example.demo.data.Charity;

import java.util.List;

public interface ICharityManager {

    int addCharity(Charity charity);
    void deleteCharity(int id);
    void updateCharity(int id, Charity charity);

    List<Charity> getAllCharities();
    Charity getCharityById(int id);
    List<Charity> getAllCharitiesByOwner(String username);
    public Double estimateDonationToCharityById(int id);
    public List<Charity> getAllDonatedToCharitiesByUsername(String username);
    public List<Charity> getAllParticipatedCharitiesByUsername(String username);
    public void setCharityThumbnailPath(String path, int charityId);
    List<Charity> getCharitiesByName(String name);
    // void completeCharity(int id);
    List<Charity> getNMostPopularCharities(int n);
}
