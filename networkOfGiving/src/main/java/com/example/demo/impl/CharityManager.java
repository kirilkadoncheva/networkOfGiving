package com.example.demo.impl;

import com.example.demo.ICharityManager;
import com.example.demo.data.Charity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CharityManager implements ICharityManager {

    private Repository repository;

    @Autowired
    public CharityManager(Repository repository) {
        this.repository = repository;
    }

    @Override
    public int addCharity(Charity charity) {
        return this.repository.addCharity(charity);
    }

    @Override
    public void deleteCharity(int id) {

        this.repository.deleteCharity(id);
    }

    @Override
    public List<Charity> getAllCharities() {
        return this.repository.getAllCharities();
    }

    @Override
    public Charity getCharityById(int id) {
        return this.repository.getCharityById(id);
    }

    @Override
    public List<Charity> getAllCharitiesByOwner(String username) {
        return this.repository.getAllCharitiesByOwner(username);
    }

    @Override
    public Double estimateDonationToCharityById(int id) {
        return this.repository.estimateDonationToCharityById(id);
    }

    @Override
    public List<Charity> getAllDonatedToCharitiesByUsername(String username) {
        return this.repository.getAllDonatedToCharitiesByUsername(username);
    }

    @Override
    public List<Charity> getAllParticipatedCharitiesByUsername(String username) {
        return this.repository.getAllParticipatedCharitiesByUsername(username);
    }

    @Override
    public List<Charity> getCharitiesByName(String name) {
        return this.repository.getCharitiesByName(name);
    }

    @Override
    public void updateCharity(int id, Charity charity) {

        this.repository.updateCharity(id,charity);
    }

    @Override
    public List<Charity> getNMostPopularCharities(int n) {
        return null;
    }

    @Override
    public void setCharityThumbnailPath(String path, int charityId)
    {
       this.repository.setCharityThumbnailPath(path,charityId);
    }
}
