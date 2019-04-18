package com.example.rustaying;

public class Service {
    private String requestID;
    private String requestType;
    private String description;
    private String status;
    private String hourValue;
    private String minuteValue;
    private String ampmValue;
    private String luggageValue;
    private String requestedTimeValet;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String requestedTimeBellboy;

    public Service(){

    }

    public Service(String requestType, String luggageValue, String requestedTime) {
        this.requestType = requestType;
        this.luggageValue = luggageValue;
        this.requestedTimeBellboy = requestedTime;
    }

    public Service(String requestType, String requestedTime, String answer1, String answer2, String answer3, String answer4) {
        this.requestType = requestType;
        this.requestedTimeValet = requestedTime;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
    }



    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public Service(String hourValue){
        this.hourValue = hourValue;
    }

    public String getHourValue() {
        return hourValue;
    }

    public String getLuggageValue() {
        return luggageValue;
    }

    public void setLuggageValue(String luggageValue) {
        this.luggageValue = luggageValue;
    }

    public String getRequestedTimeBellboy() {
        return requestedTimeBellboy;
    }

    public void setRequestedTimeBellboy(String requestedTime) {
        this.requestedTimeBellboy = requestedTime;
    }

    public String getRequestedTimeValet() {
        return requestedTimeValet;
    }

    public void setRequestedTimeValet(String requestedTime) {
        this.requestedTimeValet = requestedTime;
    }

    public void setHourValue(String hourValue) {
        this.hourValue = hourValue;
    }

    public String getMinuteValue() {
        return minuteValue;
    }

    public void setMinuteValue(String minuteValue) {
        this.minuteValue = minuteValue;
    }

    public String getAmpmValue() {
        return ampmValue;
    }

    public void setAmpmValue(String ampmValue) {
        this.ampmValue = ampmValue;
    }

    public Service(String requestID, String requestType, String description, String status)
    {
        this.requestID = requestID;
        this.requestType = requestType;
        this.description = description;
        this.status = status;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}