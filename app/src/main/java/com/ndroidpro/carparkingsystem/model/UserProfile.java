package com.ndroidpro.carparkingsystem.model;

import com.ndroidpro.carparkingsystem.Constants;

public class UserProfile {

    private String email;
    private String userName;
    // 1 for Super Admin , 2 for customer
    private int role;

    public UserProfile() {
        this.role = Constants.USER_ROLE_CUSTOMER;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
