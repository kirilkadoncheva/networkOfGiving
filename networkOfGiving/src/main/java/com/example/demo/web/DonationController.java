package com.example.demo.web;



import com.example.demo.data.Donation;
import com.example.demo.impl.DonationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200",allowedHeaders = "*")
@RestController
@RequestMapping(value = "/donations", produces = MediaType.APPLICATION_JSON_VALUE)
public class DonationController {

    private final DonationManager donationManager;


    public DonationController(DonationManager donationManager) {
        this.donationManager = donationManager;
    }

    @GetMapping
    public List<Donation> getAllDonations()
    {
        return this.donationManager.getAllDonations();
    }

    @GetMapping("/by_username/{username}")
    public List<Donation> getAllDonationsByUsername(@PathVariable("username") String username)
    {
        return this.donationManager.getAllDonationsByUsername(username);
    }

    @GetMapping("/by_charity/{charityId}")
    public List<Donation> getAllDonationsByCharity(@PathVariable("charityId") int charityId)
    {
        return this.donationManager.getAllDonationsToCharity(charityId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean addDonation(@RequestBody Donation donation) {
        return this.donationManager.addDonation(donation);
    }


    @DeleteMapping("/{charityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDonationsToCharity(@PathVariable("charityId") int charityId) {
        this.donationManager.deleteAllDonationsToCharity(charityId);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void taskNotFoundExceptionHandler() {
    }
}
