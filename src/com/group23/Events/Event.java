package com.group23.Events;

public abstract class Event {

    //private variables for the class
    private String eventID, title, dateTime, location, status;
    private int capacity;

    public Event(String eventID, String title, String dateTime, String location, int capacity, String status) {

        if (capacity <= 0) {
            throw new IllegalArgumentException("The capacity must be greater than 0.");
        }
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
    //setters
    public void setTitle(String title) { this.title = title; }
    public void setDateTime(String dateTime) { this.dateTime = dateTime; }
    public void setLocation(String location) { this.location = location; }

    
    public void setCapacity(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0.");
        }
        this.capacity = capacity;
    }

    public void cancelEvent() {
        //this.status = EventStatus.CANCELLED;
        this.status = "Cancelled";
    }


    public abstract EventType getEventType();
    public abstract String getTypeSpecificDetails();
    public abstract void setSpecific(String specific);

     @Override
    public String toString() {
         return "ID: " + eventID +
           " | Title: " + title +
           " | Date: " + dateTime +
           " | Location: " + location +
           " | Capacity: " + capacity +
           " | Status: " + status +
           " | Type: " + getEventType() +
           " | " + getTypeSpecificDetails();
     }

     //calls the csv format so event can be formatted for a csv file
    public abstract String toCsvFormat();
}
    
