package com.example.demo.data;


public class Charity{

    private int id;
    private String owner_username;
    private String name;
    private String description;
    private double required_amount;
    private int required_participants;
    private double donated_amount;
    private int participants;
    private boolean completed = false;
    private String pathToThumbnail;


    public Charity(int id, String owner_username, String name, String description, double required_amount, int required_participants, double donated_amount, int participants, boolean completed, String pathToThumbnail) {
        this.id = id;
        this.owner_username = owner_username;
        this.name = name;
        this.description = description;
        this.required_amount = required_amount;
        this.required_participants = required_participants;
        this.donated_amount = donated_amount;
        this.participants = participants;
        this.completed = completed;
        this.pathToThumbnail = pathToThumbnail;
    }

    public void setOwner_username(String owner_username) {
        this.owner_username = owner_username;
    }

    public int getId() {
        return id;
    }

    public String getOwner_username() {
        return owner_username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRequired_amount() {
        return required_amount;
    }

    public void setRequired_amount(double required_amount) {
        this.required_amount = required_amount;
    }

    public int getRequired_participants() {
        return required_participants;
    }

    public void setRequired_participants(int required_participants) {
        this.required_participants = required_participants;
    }

    public double getDonated_amount() {
        return donated_amount;
    }

    public void setDonated_amount(double donated_amount) {
        this.donated_amount = donated_amount;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getPathToThumbnail() {
        return pathToThumbnail;
    }

    public void setPathToThumbnail(String pathToThumbnail) {
        this.pathToThumbnail = pathToThumbnail;
    }

    public double getNeededMoney()
    {
        return this.required_amount - this.donated_amount;
    }

    public double getNeededVolunteers()
    {
        return this.required_participants - this.participants;
    }
}
