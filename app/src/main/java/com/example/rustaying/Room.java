package com.example.rustaying;

public class Room {
    private int roomId;
    private String roomType;
    private boolean isAvailable;
    //private Date nextReseration;

    public void Room()
    {
        this.roomId = -1;
        this.roomType = null;
        this.isAvailable = false;
    }

    //Getter Methods
    public int getRoomId()
    {
        return this.roomId;
    }

    public String getRoomType()
    {
        return this.roomType;
    }

    public boolean getIsAvailable()
    {
        return this.isAvailable;
    }

    //Setter methods
    public void setRoomId(int id)
    {
        this.roomId = id;
    }

    public void setRoomType(String type)
    {
        this.roomType = type;
    }

    public void setAvailable(boolean available)
    {
        isAvailable = available;
    }
}
