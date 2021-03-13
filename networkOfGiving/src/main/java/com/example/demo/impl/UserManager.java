package com.example.demo.impl;

import com.example.demo.IUserManager;
import com.example.demo.data.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserManager implements IUserManager {

    private UserRepository repository;

    @Autowired
    public UserManager(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public double getBalanceByUsername(String username) {
        return this.repository.getBalanceByUsername(username);
    }

    @Override
    public String getNameByUsername(String username) {
        return this.repository.getNameByUsername(username);
    }

    @Override
    public void updateBalance(String username, double balance) {
        this.repository.updateBalance(balance,username);
    }

    @Override
    public UserInfo getInfoByUsername(String username) {
        return this.repository.getInfoByUsername(username);
    }
}
