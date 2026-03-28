package com.group23.Events;

public class Seminar extends Event {
    //private variable for speaker
    private String speakerName;

    public Seminar(String eventID, String title, String dateTime, String location, int capacity, String status, String speakerName) {
        //inherits parent class attributes
        super(eventID, title, dateTime, location, capacity, status);

        //seminar specific variable
        this.speakerName = speakerName;
    }

    //getter
    public String getSpeakerName() {
        return speakerName;
    }
    public void setSpeakerName(String speakerName) { this.speakerName = speakerName; }
    public void setSpecific(String specific) {
        setSpeakerName(specific);
    }

    //overrides csv format for a seminar event
    @Override
    public String toCsvFormat() {
        return String.format("%s,%s,%s,%s,%d,%s,Seminar,,%s,",
                getEventID(), getTitle(), getDateTime(), getLocation(), getCapacity(), getStatus(), speakerName);
    }

     @Override
    public EventType getEventType() {
        return EventType.SEMINAR;
    }
    @Override
    public String getTypeSpecificDetails() {
        return "Speaker: " + speakerName;
    }
    
}
