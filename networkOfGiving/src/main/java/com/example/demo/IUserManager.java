package com.example.demo;

import com.example.demo.data.UserInfo;

public interface IUserManager {
    public double getBalanceByUsername(String username);
    public String getNameByUsername(String username);

    public void updateBalance(String username, double balance);
    public UserInfo getInfoByUsername(String username);


}
