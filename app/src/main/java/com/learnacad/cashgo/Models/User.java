package com.learnacad.cashgo.Models;

/**
 * Created by Sahil Malhotra on 27-09-2017.
 */

public class User {

    private String name;
    private String mobileNum;
    private String email;
    private String dob;
    private String gender;
    private String userId;
    private String password;

    public User(){


    }

    public User(String userId,String name, String mobileNum, String email, String dob, String gender) {
        this.name = name;
        this.mobileNum = mobileNum;
        this.email = email;
        this.dob = dob;
        this.gender = gender;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
