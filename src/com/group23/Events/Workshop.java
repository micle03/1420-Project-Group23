package com.group23.Events;

import java.time.LocalDateTime;

public class Workshop extends Event {
    //private variable for topic
    private String topic;

    public Workshop(String eventID, String title, String dateTime, String location, int capacity, String status, String topic) {
        //inherits parent class attributes
        super(eventID, title, dateTime, location, capacity, status);

         if (topic == null || topic.isBlank()) {
            throw new IllegalArgumentException("model.Workshop topic is required.");
        }

        //workshop specific variable
        this.topic = topic;
    }

    //getter
    public String getTopic() {
        return topic;
    }
    public void setTopic(String topic) { this.topic = topic; }


    @Override
    public EventType getEventType() {
        return EventType.WORKSHOP;
    }

    @Override
    public String getTypeSpecificDetails() {
        return "Topic: " + topic;
    }
}
