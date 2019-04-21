package com.example.rustaying;

import java.time.LocalDate;
import java.util.ArrayList;

public class Room {
    private String roomId;
    private String roomType;
    private boolean isAvailable;


    //2D array for holding pairs of checkIn and checkOut dates
    ArrayList[][] reservations = new ArrayList[50][2];

    public Room(){

    }

    public Room(String roomId, String roomType, boolean isAvailable){
        this.roomId = roomId;
        this.roomType = roomType;
        this.isAvailable = isAvailable;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean available) {
        isAvailable = available;
    }

    public ArrayList[][] getReservations() {
        return reservations;
    }

    public void setReservations(ArrayList[][] reservations) {
        this.reservations = reservations;
    }


}
