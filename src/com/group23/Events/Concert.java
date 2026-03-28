package com.group23.Events;

public class Concert extends Event {
    //private variable for age
    private String ageRestriction; //changed to string because it was taking in "+" in 18+ and crashing

    public Concert(String eventID, String title, String dateTime, String location, int capacity, String status, String ageRestriction) {
        //inherits parent class attributes
        super(eventID, title, dateTime, location, capacity, status);

        if (ageRestriction == null || ageRestriction.isBlank()) {
            throw new IllegalArgumentException("Age restriction is required.");
        }

        //concert specific variable
        this.ageRestriction = ageRestriction;
    }

    //getter
    public String getAgeRestriction() {
        return ageRestriction;
    }
    public void setAgeRestriction(String ageRestriction) { this.ageRestriction = ageRestriction; }
    public void setSpecific(String specific) {
        setAgeRestriction(specific);
    }

    //overrides csv format for a concert event
    @Override
    public String toCsvFormat() {
        return String.format("%s,%s,%s,%s,%d,%s,Concert,,,%s",
                getEventID(), getTitle(), getDateTime(), getLocation(), getCapacity(), getStatus(), ageRestriction);
    }
        
    @Override
    public EventType getEventType() {
        return EventType.CONCERT;
    }

    @Override
    public String getTypeSpecificDetails() {
        return "Age Restriction: " + ageRestriction;
    }
}
