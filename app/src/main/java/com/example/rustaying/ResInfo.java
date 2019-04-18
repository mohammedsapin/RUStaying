package com.example.rustaying;
import java.time.LocalDate;

public class ResInfo {
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String [] roomTypes = new String[3]; //Hold types of rooms guest wants

    //Constructor
    public ResInfo(LocalDate checkIn, LocalDate checkOut, String[] roomTypes)
    {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        for(int i = 0; i < roomTypes.length; i++)
        {
            this.roomTypes[i] = roomTypes[i];
        }
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public String[] getRoomTypes() {
        return roomTypes;
    }

    public void setRoomTypes(String[] roomTypes) {
        this.roomTypes = roomTypes;
    }

}
