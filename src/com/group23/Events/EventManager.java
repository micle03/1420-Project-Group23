package com.group23.Events;

import com.group23.Bookings.BookingService;

import java.util.*;
import java.util.stream.Collectors;

public class EventManager {
    private Map<String, Event> events = new HashMap<>();
    private static final EventManager instance = new EventManager();
    public static EventManager getInstance() {
        return instance;
    }
    BookingService bookingService = BookingService.getInstance();

    // create event
    public void addEvent(Event event) {
        if (events.containsKey(event.getEventID())) {
            throw new IllegalArgumentException("Duplicate event ID.");
        }
        bookingService.registerEvent(event.getEventID(), event.getCapacity(), event.getStatus());
        events.put(event.getEventID(), event);

        //saves new event to csv file
        saveEventsToFile();
    }

    // update event
    public void updateEvent(String eventId, String title,
                            String location, int capacity, String dateTime, String specific) {

        Event event = getEvent(eventId);

        event.setTitle(title);
        event.setLocation(location);
        event.setCapacity(capacity);
        event.setDateTime(dateTime);
        event.setSpecific(specific);

        //saves changes to an event to csv file
        saveEventsToFile();
    }

    // cancel event
    public void cancelEvent(String eventId) {
        Event event = getEvent(eventId);
        event.cancelEvent();

        //saves csv file without the deleted event
        saveEventsToFile();
    }

    // listing event
    public List<Event> listAllEvents() {
        return new ArrayList<>(events.values());
    }

    // search by title (case-insensitive)
    public List<Event> searchByTitle(String keyword) {
        if (keyword == null){
            return Collections.emptyList();
        }
        return events.values().stream()
                .filter(e -> e.getTitle().toLowerCase()
                        .contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    // filter by type
    public List<Event> filterByType(EventType type) {
        return events.values().stream()
                .filter(e -> e.getEventType() == type)
                .collect(Collectors.toList());
    }

    public Event getEvent(String eventId) {
        if (!events.containsKey(eventId)) {
            throw new IllegalArgumentException("model.Event not found.");
        }
        return events.get(eventId);
    }

    //file writer
    public void saveEventsToFile() {
        //creates a writer and tries to write to the events.csv
        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter("events.csv"))) {
            //header of the csv
            writer.println("eventID,title,dateTime,location,capacity,status,eventType,topic,speakerName,ageRestriction");
            for (Event event : events.values()) {
                writer.println(event.toCsvFormat());
            }
        } catch (java.io.IOException e) {
            System.out.println("Error saving something in events: " + e.getMessage());
        }
    }
}
