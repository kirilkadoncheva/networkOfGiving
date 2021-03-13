package com.example.demo.web;

import com.example.demo.data.Charity;
import com.example.demo.impl.CharityManager;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200",allowedHeaders = "*")
@RestController
@RequestMapping(value = "/charities", produces = MediaType.APPLICATION_JSON_VALUE)
public class CharityController {

    private final CharityManager charityManager;

    public CharityController(CharityManager charityManager) {
        this.charityManager = charityManager;
    }

    @GetMapping("/all")
    public List<Charity> getAllCharities()
    {
        return this.charityManager.getAllCharities();
    }

    @GetMapping("/{id}")
    public Charity getCharityById(@PathVariable("id") int id)
    {
        return this.charityManager.getCharityById(id);
    }

    @GetMapping("/owner/{username}")
    public List<Charity> getCharitiesByUsername(@PathVariable("username") String username)
    {
        return this.charityManager.getAllCharitiesByOwner(username);
    }

    @GetMapping("/estimate_donation/{id}")
    public Double estimateDonation(@PathVariable("id") int id)
    {
        return this.charityManager.estimateDonationToCharityById(id);
    }

    @GetMapping("/donated_to/{username}")
    public List<Charity> getAllDonatedToCharitiesByUsername(@PathVariable("username") String username)
    {
        return this.charityManager.getAllDonatedToCharitiesByUsername(username);
    }

    @GetMapping("/participated/{username}")
    public List<Charity> getAllParticipatedCharitiesByUsername(@PathVariable("username") String username)
    {
        return this.charityManager.getAllParticipatedCharitiesByUsername(username);
    }

//    @DeleteMapping("/{username}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteUser(@PathVariable("username") String username) {
//        this.userManager.deleteUser(username);
//    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    //@ResponseStatus(HttpStatus.NO_CONTENT)
    public int addCharity(@RequestBody Charity charity) {

        return this.charityManager.addCharity(charity);
    }


    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCharity(@PathVariable int id, @RequestBody Charity charity) {
        this.charityManager.updateCharity(id,charity);
    }

    @DeleteMapping(path="/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCharity(@PathVariable int id)
    {
        this.charityManager.deleteCharity(id);
    }
    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void taskNotFoundExceptionHandler() {
    }


}
