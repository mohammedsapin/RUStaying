package com.example.rustaying;

public class Service {
    private String requestID;
    private String requestType;
    private String description;
    private String status;
    private String hourValue;
    private String minuteValue;
    private String ampmValue;
    private int luggageValue;
    private String requestedTime;

    public Service(){

    }

    public Service(String requestType, int luggageValue, String requestedTime) {
        this.requestType = requestType;
        this.luggageValue = luggageValue;
        this.requestedTime = requestedTime;
    }

    public Service(String hourValue){
        this.hourValue = hourValue;
    }

    public String getHourValue() {
        return hourValue;
    }

    public int getLuggageValue() {
        return luggageValue;
    }

    public void setLuggageValue(int luggageValue) {
        this.luggageValue = luggageValue;
    }

    public String getRequestedTime() {
        return requestedTime;
    }

    public void setRequestedTime(String requestedTime) {
        this.requestedTime = requestedTime;
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