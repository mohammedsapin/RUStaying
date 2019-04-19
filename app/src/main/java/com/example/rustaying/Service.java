package com.example.rustaying;

public class Service {
    private long requestID;
    private String requestType;
    private String description;
    private String status;
    private String hourValue;
    private String minuteValue;
    private String ampmValue;
    private String luggageValue;
    private String requestedTimeValet;
    private String requestedTimeBellboy;
    private String requestDate;
    private String inputs;
    private String bathroom;
    private String electronic;
    private String lighting;
    private String checkboxes;
    private String towels;
    private String soap;
    private String bedsheets;
    private String cleaningservice;
    private String requestedTimeMaintenance;
    private String requestedTimeRoomService;
    private String fromWhere;
    private String startingStreet;
    private String startingCityStateZip;
    private String destinationStreet;
    private String destinationCityStateZip;


    public Service(){

    }

    //bellboy
    public Service(String requestType, String requestDate, String luggageValue, String requestedTime, String fromWhere) {
        this.requestType = requestType;
        this.requestDate=requestDate;
        this.luggageValue = luggageValue;
        this.requestedTimeBellboy = requestedTime;
        this.fromWhere=fromWhere;
    }

    //valet and travel
    public Service(String requestType, String requestDate, String requestedTime, String answer1, String answer2, String answer3, String answer4) {
        this.requestType = requestType;
        this.requestDate=requestDate;
        this.requestedTimeValet = requestedTime;
        this.startingStreet=answer1;
        this.startingCityStateZip=answer2;
        this.destinationStreet=answer3;
        this.destinationCityStateZip=answer4;

    }

    //maintenance
    public Service(String requestType, String requestDate, String requestedTime, String inputs, String bathroom, String electronic, String lighting, String checkboxes) {
        this.requestType = requestType;
        this.requestedTimeMaintenance = requestedTime;
        this.requestDate=requestDate;
        this.inputs=inputs;
        this.bathroom=bathroom;
        this.electronic=electronic;
        this.lighting=lighting;
        this.checkboxes=checkboxes;
    }


    //roomservice
    public Service(String requestType, String requestDate, String requestedTime, String inputs, String towels, String soap, String bedsheets, String cleaningservice, String checkboxes){
        this.requestType = requestType;
        this.requestedTimeRoomService = requestedTime;
        this.requestDate=requestDate;
        this.inputs=inputs;
        this.towels=towels;
        this.soap=soap;
        this.bedsheets=bedsheets;
        this.cleaningservice=cleaningservice;
        this.checkboxes=checkboxes;
    }


    public String getRequestedTimeMaintenance() {
        return requestedTimeMaintenance;
    }

    public void setRequestedTimeMaintenance(String requestedTimeMaintenance) {
        this.requestedTimeMaintenance = requestedTimeMaintenance;
    }

    public String getRequestedTimeRoomService() {
        return requestedTimeRoomService;
    }

    public void setRequestedTimeRoomService(String requestedTimeRoomService) {
        this.requestedTimeRoomService = requestedTimeRoomService;
    }

    public String getFromWhere(){
        return fromWhere;
    }

    public void setFromWhere(String fromWhere){
        this.fromWhere=fromWhere;
    }

    public String getTowels() {
        return towels;
    }

    public void setTowels(String towels) {
        this.towels = towels;
    }

    public String getSoap() {
        return soap;
    }

    public void setSoap(String soap) {
        this.soap = soap;
    }

    public String getBedsheets() {
        return bedsheets;
    }

    public void setBedsheets(String bedsheets) {
        this.bedsheets = bedsheets;
    }

    public String getCleaningservice() {
        return cleaningservice;
    }

    public void setCleaningservice(String cleaningservice) {
        this.cleaningservice = cleaningservice;
    }

    public String getInputs(){return inputs;}

    public void setInputs(String inputs){this.inputs=inputs;}

    public String getCheckboxes() {
        return checkboxes;
    }

    public void setCheckboxes(String checkboxes) {
        this.checkboxes= checkboxes;
    }

    public String getBathroom() {
        return bathroom;
    }

    public void setBathroom(String bathroom) {
        this.bathroom= bathroom;
    }

    public String getElectronic() {
        return electronic;
    }

    public void setElectronic(String electronic) {
        this.electronic= electronic;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate= requestDate;
    }


    public String getLighting() {
        return lighting;
    }

    public void setLighting(String lighting) {
        this.lighting= lighting;
    }

    public String getStartingStreet() {
        return startingStreet;
    }

    public void setStartingStreet(String startingStreet) {
        this.startingStreet = startingStreet;
    }

    public String getStartingCityStateZip() {
        return startingCityStateZip;
    }

    public void setStartingCityStateZip(String startingCityStateZip) {
        this.startingCityStateZip = startingCityStateZip;
    }

    public String getDestinationStreet() {
        return destinationStreet;
    }

    public void setDestinationStreet(String destinationStreet) {
        this.destinationStreet = destinationStreet;
    }

    public String getDestinationCityStateZip() {
        return destinationCityStateZip;
    }

    public void setDestinationCityStateZip(String destinationCityStateZip) {
        this.destinationCityStateZip = destinationCityStateZip;
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


    public long getRequestID() {
        return requestID;
    }

    public void setRequestID(long requestID) {
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