package com.example.rustaying;

import java.time.LocalDate;
import java.util.Date;

public class Guest {

    private String firstName, lastName, guestEmail;
    private String accountCreated;
    private boolean accountStatus;
    private int luggage;
    private boolean isCheckedIn;

    String roomNum;
    private String checkInDate;
    private String checkOutDate;

    //add more as we keep working on project

    public Guest() {
        //this.checkInDate = null;
        //this.checkOutDate = null;
        this.checkInDate = "";
        this.checkOutDate = "";
        this.roomNum = "";
        // Default constructor need for DataSnapshot on firebase
    }

    //Constructor to create Guest object
    public Guest(String firstName, String lastName, String guestEmail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.guestEmail = guestEmail;
        this.isCheckedIn = false;
        this.accountStatus = true;
        this.checkInDate = "";
        this.checkOutDate = "";
        this.roomNum = "";
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

    public String getAccountCreated() {
        return accountCreated;
    }

    public void setAccountCreated(String accountCreated) {
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

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

}
