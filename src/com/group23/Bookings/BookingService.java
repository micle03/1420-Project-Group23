package com.group23.Bookings;

import com.group23.Users.service.UserManager;
import com.group23.Waitlist.waitlistManager;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

//ENUMS PRIVATE

enum UserType {
    STUDENT,
    STAFF,
    GUEST
}

//booking model PRIVATE

// Booking Class
public class BookingService {
    private static final BookingService instance = new BookingService();
    public static BookingService getInstance() {
        return instance;
    }
    waitlistManager WaitlistManager = waitlistManager.instance;

    // Internal storage
    private final Map<String, Booking> bookingsById = new HashMap<>();
    private final Map<String, Integer> eventCapacity = new HashMap<>();
    private final Map<String, Boolean> eventActive = new HashMap<>();
    private final Map<String, UserType> userTypes = new HashMap<>();
    private int bookingCounter = 9000;


    // Setup helpers
    public void registerUser(String userId, String type) {
        UserType userType = null;
        if(type.equals("Staff")) {
            userType = UserType.STAFF;
        } else if (type.equals("Guest")) {
            userType = UserType.GUEST;
        } else if (type.equals("Student")) {
            userType = UserType.STUDENT;
        }
        userTypes.put(userId, userType);
    }

    public void registerEvent(String eventId, int capacity, String status) {
        boolean active = false;
        if(status.equals("Active")) active = true;
        eventCapacity.put(eventId, capacity);
        eventActive.put(eventId, active);
    }

    // Book Event
    public Booking bookEvent(String userId, String eventId) throws IOException {

        if (!userTypes.containsKey(userId))
            throw new IllegalArgumentException("User not found");

        if (!eventCapacity.containsKey(eventId))
            throw new IllegalArgumentException("Event not found");

        if (!eventActive.get(eventId))
            throw new IllegalStateException("Event is cancelled");

        // No duplicate bookings
        if (hasActiveBooking(userId, eventId))
            throw new IllegalStateException("User already booked this event");

        // Check user limit
        int confirmedCount = (int) getUserBookings(userId).stream()
                .filter(b -> b.getStatus() == BookingStatus.CONFIRMED)
                .count();

        int maxAllowed = getMaxAllowed(userTypes.get(userId));
        if (confirmedCount >= maxAllowed) {
            throw new IllegalStateException("User can't book more events");
        }

        // Capacity check
        int confirmedForEvent = getConfirmedBookings(eventId).size();

        BookingStatus status =
                (confirmedForEvent < eventCapacity.get(eventId))
                        ? BookingStatus.CONFIRMED
                        : BookingStatus.WAITLISTED;

        if(status.equals(BookingStatus.WAITLISTED)) {
            WaitlistManager.addToWaitlist(eventId, UserManager.getInstance().getUserById(userId));
        }

        Booking booking = new Booking(
                generateBookingId(),
                userId,
                eventId,
                LocalDateTime.now(),
                status
        );

        bookingsById.put(booking.getBookingId(), booking);
        return booking;
    }

    // Cancel Bookings
    public Booking cancelBooking(String bookingId) {

        Booking booking = bookingsById.get(bookingId);
        if (booking == null)
            throw new IllegalArgumentException("Booking not found");

        if (booking.getStatus() == BookingStatus.CANCELLED)
            return booking;

        BookingStatus previous = booking.getStatus();
        booking.setStatus(BookingStatus.CANCELLED);

        // Promote waitlist if needed
        if (previous == BookingStatus.CONFIRMED) {
            promoteFirstWaitlisted(booking.getEventId());
            try {
                WaitlistManager.promoteUser(booking.getEventId());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return booking;
    }

    // Methods

    public List<Booking> getUserBookings(String userId) {
        return bookingsById.values().stream()
                .filter(b -> b.getUserId().equals(userId))
                .sorted(Comparator.comparing(Booking::getCreatedAt))
                .collect(Collectors.toList());
    }

    public List<Booking> getConfirmedBookings(String eventId) {
        return bookingsById.values().stream()
                .filter(b -> b.getEventId().equals(eventId))
                .filter(b -> b.getStatus() == BookingStatus.CONFIRMED)
                .sorted(Comparator.comparing(Booking::getCreatedAt))
                .collect(Collectors.toList());
    }

    public List<Booking> getWaitlist(String eventId) {
        return bookingsById.values().stream()
                .filter(b -> b.getEventId().equals(eventId))
                .filter(b -> b.getStatus() == BookingStatus.WAITLISTED)
                .sorted(Comparator.comparing(Booking::getCreatedAt))
                .collect(Collectors.toList());
    }


    // helpers

    private boolean hasActiveBooking(String userId, String eventId) {
        return bookingsById.values().stream()
                .anyMatch(b ->
                        b.getUserId().equals(userId) &&
                                b.getEventId().equals(eventId) &&
                                b.getStatus() != BookingStatus.CANCELLED);
    }

    private int getMaxAllowed(UserType type) {
        return switch (type) {
            case STUDENT -> 3;
            case STAFF -> 5;
            case GUEST -> 1;
        };
    }

    private void promoteFirstWaitlisted(String eventId) {
        List<Booking> waitlist = getWaitlist(eventId);
        if (!waitlist.isEmpty()) {
            Booking next = waitlist.get(0);
            next.setStatus(BookingStatus.CONFIRMED);
        }
    }

    private String generateBookingId() {
        return "B" + (bookingCounter++);
    }
}

/*
bookingService.registerUser("U001", UserType.STUDENT);
bookingService.registerUser("U002", UserType.STAFF);

bookingService.registerEvent("E101", 2, true);

Booking b = bookingService.bookEvent("U001", "E101");
System.out.println(b.getStatus());

bookingService.cancelBooking(b.getBookingId());






 */