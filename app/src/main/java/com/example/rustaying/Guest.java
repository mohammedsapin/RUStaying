package com.example.rustaying;

public class Guest {

    private String firstName, lastName, guestEmail;
    private String accountCreated;
    private boolean accountStatus;
    private int luggage;
    private boolean isCheckedIn;

    String roomNum;
    private String checkInDate;
    private String checkOutDate;
    private String creditCardNumber;
    private String CCV;
    private String nameOnCCard;
    private String billingAddress;
    private String expirationDate;
    private boolean validPayment;

    private String keyCode;

    private boolean reservationMade;

    //add more as we keep working on project

    public Guest() {
        //this.checkInDate = null;
        //this.checkOutDate = null;
        this.checkInDate = "";
        this.checkOutDate = "";
        this.roomNum = "";
        this.reservationMade = false;
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

        this.creditCardNumber = "";
        this.CCV="";
        this.nameOnCCard = "";
        this.billingAddress="";
        this.expirationDate="";
        this.validPayment = false;

        this.reservationMade = false;

        this.keyCode = "-1";

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

    public void setCheckOutDate(String checkOutDate) { this.checkOutDate = checkOutDate; }

    public String getCreditCardNumber() { return creditCardNumber; }
    public void setCreditCardNumber(String creditCardNumber) { this.creditCardNumber = creditCardNumber; }

    public String getCCV() { return CCV; }
    public void setCCV(String CCV) { this.CCV = CCV; }

    public String getNameOnCCard() { return nameOnCCard; }
    public void setNameOnCCard(String nameOnCCard) { this.nameOnCCard = nameOnCCard; }

    public String getBillingAddress() { return billingAddress; }
    public void setBillingAddress(String billingAddress) { this.billingAddress = billingAddress; }

    public String getExpirationDate() { return expirationDate; }
    public void setExpirationDate(String expirationDate) { this.expirationDate = expirationDate; }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }
    public boolean isValidPayment() { return validPayment; }
    public void setValidPayment(boolean validPayment) { this.validPayment = validPayment; }

    public boolean isReservationMade() {
        return reservationMade;
    }

    public void setReservationMade(boolean reservationMade) {
        this.reservationMade = reservationMade;
    }

    public String getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }
}
