package com.group23.Events;

public class Concert extends Event {
    //private variable for age
    private String ageRestriction; //changed to string because it was taking in "+" in 18+ and crashing

    public Concert(String eventID, String title, String dateTime, String location, int capacity, String status, String ageRestriction) {
        //inherits parent class attributes
        super(eventID, title, dateTime, location, capacity, status);

        //concert specific variable
        this.ageRestriction = ageRestriction;
    }

    //getter
    public String getAgeRestriction() {
        return ageRestriction;
    }
}
