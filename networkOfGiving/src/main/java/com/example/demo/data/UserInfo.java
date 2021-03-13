package com.example.demo.data;

public class UserInfo {
    private String username;

    private String name;
    private int age;
    private String gender;
    private String town;
    private double balance;

    public UserInfo(String username, String name, int age, String gender, String town, double balance) {
        this.username = username;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.town = town;
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
