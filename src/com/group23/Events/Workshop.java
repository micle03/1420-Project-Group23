package com.group23.Events;

public class Workshop extends Event {
    //private variable for topic
    private String topic;

    public Workshop(String eventID, String title, String dateTime, String location, int capacity, String status, String topic) {
        //inherits parent class attributes
        super(eventID, title, dateTime, location, capacity, status);

        //workshop specific variable
        this.topic = topic;
    }

    //getter
    public String getTopic() {
        return topic;
    }
}
