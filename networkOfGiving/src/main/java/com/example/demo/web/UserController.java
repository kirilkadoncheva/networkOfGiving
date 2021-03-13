package com.example.demo.web;

import com.example.demo.data.Charity;
import com.example.demo.data.Donation;
import com.example.demo.data.UserInfo;
import com.example.demo.impl.DonationManager;
import com.example.demo.impl.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200",allowedHeaders = "*")
@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserManager userManager;


    public UserController(UserManager userManager) {
        this.userManager = userManager;
    }

    @GetMapping("/balance/{username}")
    public Double getBalanceByUsername(@PathVariable("username") String username)
    {
        return this.userManager.getBalanceByUsername(username);
    }

    @GetMapping("/{username}")
    public UserInfo getInfoByUsername(@PathVariable("username") String username)
    {
        return this.userManager.getInfoByUsername(username);
    }

    @GetMapping("/name/{username}")
    public ResponseEntity<?> getNameByUsername(@PathVariable("username") String username)
    {
        return ResponseEntity.ok(this.userManager.getNameByUsername(username));
    }


    @PutMapping(path = "/balance/{username}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBalance(@PathVariable String username, @RequestParam("newBalance") double newBalance) {
        this.userManager.updateBalance(username,newBalance);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void taskNotFoundExceptionHandler() {
    }
}
