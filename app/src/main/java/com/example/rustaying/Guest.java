package com.example.rustaying;

import java.util.Date;

public class Guest {

    private String firstName, lastName, guestEmail;
    private Date accountCreated;
    private boolean accountStatus;
    private int luggage;
    private boolean isCheckedIn;
    //add more as we keep working on project

    public Guest() {
        // Default constructor need for DataSnapshot on firebase
    }


    //Constructor to create Guest object
    public Guest(String firstName, String lastName, String guestEmail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.guestEmail = guestEmail;
        this.accountStatus = true;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
    }

    public Date getAccountCreated() {
        return accountCreated;
    }

    public void setAccountCreated(Date accountCreated) {
        this.accountCreated = accountCreated;
    }

    public boolean isAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(boolean accountStatus) {
        this.accountStatus = accountStatus;
    }

    public int getLuggage() {
        return luggage;
    }

    public void setLuggage(int luggage) {
        this.luggage = luggage;
    }

    public boolean isCheckedIn() {
        return isCheckedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        isCheckedIn = checkedIn;
    }
}
