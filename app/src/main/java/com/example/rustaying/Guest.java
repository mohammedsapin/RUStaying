package com.example.rustaying;

public class Guest {
    private int guestId;
    private String firstName;
    private String lastName;
    private String guestEmail;
    private String guestPassword;
    //private Date accountCreated;
    private String accountStatus;
    private int luggage;
    private Boolean isCheckedIn;

    //Constructor to create Guest object
    public Guest(int guestId, String guestEmail, String guestPassword)
    {
        this.guestId = guestId;
        this.guestEmail = guestEmail;
        this.guestPassword = guestPassword;
        this.accountStatus = "Active"; //Active means they have successfully created an account
        this.luggage = 0; //If not specificed, default is zero
        this.isCheckedIn = false;
    }
    public Guest(String guestEmail, String guestPassword, int luggage)
    {
        //this.guestId = 0; How do we assign a guestId number?
        this.guestEmail = guestEmail;
        this.guestPassword = guestPassword;
        this.accountStatus = "Active"; //Active means they have successfully created an account
        this.luggage = luggage;
        this.isCheckedIn = false;
    }

    //Getter methods
    public int getGuestId()
    {
        return this.guestId;
    }

    public String getFirstName() { return this.firstName; }

    public String getLastName() { return this.lastName; }

    public String getGuestEmail()
    {
        return this.guestEmail;
    }

    public String getGuestPassword()
    {
        return this.guestPassword;
    }

    public String getAccountStatus()
    {
        return this.accountStatus;
    }

    public int getLuggage()
    {
        return this.luggage;
    }

    public Boolean getCheckedIn() {
        return isCheckedIn;
    }

    //Setter methods
    public void setGuestEmail(String email)
    {
        this.guestEmail = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGuestPassword(String password)
    {
        this.guestPassword = password;
    }

    public void setAccountStatus(String status)
    {
        this.accountStatus = status;
    }

    public void setLuggage(int luggage) {
        this.luggage = luggage;
    }

    public void setCheckedIn(Boolean checkedIn) {
        isCheckedIn = checkedIn;
    }
}
