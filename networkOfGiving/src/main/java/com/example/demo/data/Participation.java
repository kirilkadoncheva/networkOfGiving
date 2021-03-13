package com.example.demo.data;

import java.util.Date;

public class Participation {
    String volunteerUsername;
    int charityId;
    Date sign_up_date;

    public Participation(String volunteerUsername, int charityId, Date sign_up_date) {
        this.volunteerUsername = volunteerUsername;
        this.charityId = charityId;
        this.sign_up_date = sign_up_date;
    }

    public String getVolunteerUsername() {
        return volunteerUsername;
    }

    public void setVolunteerUsername(String volunteerUsername) {
        this.volunteerUsername = volunteerUsername;
    }

    public int getCharityId() {
        return charityId;
    }

    public void setCharityId(int charityId) {
        this.charityId = charityId;
    }

    public Date getSign_up_date() {
        return sign_up_date;
    }

    public void setSign_up_date(Date sign_up_date) {
        this.sign_up_date = sign_up_date;
    }
}