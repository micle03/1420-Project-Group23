package com.group23.Events;

public class Event {

    //private variables for the class
    private String eventID, title, dateTime, location;
    private int capacity;
    private String status;

    public Event(String eventID, String title, String dateTime, String location, int capacity, String status) {
        //default for parent class
        this.eventID = eventID;
        this.title = title;
        this.dateTime = dateTime;
        this.location = location;
        this.capacity = capacity;
        this.status = status;
    }

    //getters
    public String getEventID() {
        return eventID;
    }
    public String getTitle() {
        return title;
    }
    public String getDateTime() {
        return dateTime;
    }
    public String getLocation() {
        return location;
    }
    public int getCapacity() {
        return capacity;
    }
    public String getStatus() {
        return status;
    }
}
