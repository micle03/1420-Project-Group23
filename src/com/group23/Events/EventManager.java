package com.group23.Events;

import java.util.*;
import java.util.stream.Collectors;

public class EventManager {
    private Map<String, Event> events = new HashMap<>();
    private static final EventManager instance = new EventManager();
    public static EventManager getInstance() {
        return instance;
    }

    // create event
    public void addEvent(Event event) {
        if (events.containsKey(event.getEventID())) {
            throw new IllegalArgumentException("Duplicate event ID.");
        }
        events.put(event.getEventID(), event);
    }

    // update event
    public void updateEvent(String eventId, String title,
                            String location, int capacity, String dateTime) {

        Event event = getEvent(eventId);

        event.setTitle(title);
        event.setLocation(location);
        event.setCapacity(capacity);
        event.setDateTime(dateTime);
    }

    // cancel event
    public void cancelEvent(String eventId) {
        Event event = getEvent(eventId);
        event.cancelEvent();
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
}
