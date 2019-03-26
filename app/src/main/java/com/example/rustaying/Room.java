package com.example.rustaying;

public class Room {
    private int roomId;
    private String roomType;
    private boolean isAvailable;
    //private Date nextReservation;

    public Room(){

    }

    public Room(int roomId, String roomType, boolean isAvailable){
        this.roomId = roomId;
        this.roomType = roomType;
        this.isAvailable = isAvailable;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
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
}
