package com.group23.Bookings;

import com.group23.Users.service.UserManager;
import com.group23.Waitlist.waitlistManager;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;



/*
 * BookingService handles all booking-related operations.
 * It enforces rules such as capacity, duplicate prevention,
 * booking limits, and waitlist integration.
 */
public class BookingService {

    // In-memory storage for booking data
    private final Map<String, Booking> bookingsById = new HashMap<>();
    private final Map<String, Integer> eventCapacity = new HashMap<>();
    private final Map<String, Boolean> eventActive = new HashMap<>();
    private final Map<String, UserType> userTypes = new HashMap<>();
    private int bookingCounter = 9000;

    // External waitlist manager
    private final waitlistManager WaitlistManager = waitlistManager.getInstance();
    private final UserManager userManager = UserManager.getInstance();
    private static final BookingService instance = new BookingService();
    public static BookingService getInstance() {
        return instance;
    }

    /*
     * Registers a user and their type.
     */
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

    /*
     * Registers an event with its capacity and active status.
     */
    public void registerEvent(String eventId, int capacity, boolean active) {
        eventCapacity.put(eventId, capacity);
        eventActive.put(eventId, active);

        // Create a waitlist file for the event
        WaitlistManager.createWaitlist(eventId);
    }

    /*
     * Creates a booking while enforcing all booking rules.
     */
    public Booking bookEvent(String userId, String eventId) {

        if (!userTypes.containsKey(userId))
            throw new IllegalArgumentException("User not found");

        if (!eventCapacity.containsKey(eventId))
            throw new IllegalArgumentException("Event not found");

        if (!eventActive.get(eventId))
            throw new IllegalStateException("Event is cancelled");

        // Prevent duplicate bookings for the same user and event
        if (hasActiveBooking(userId, eventId))
            throw new IllegalStateException("User already booked this event");

        // Count only confirmed bookings for this user
        int confirmedCount = (int) getUserBookings(userId).stream()
                .filter(b -> b.getStatus() == BookingStatus.CONFIRMED)
                .count();

        // Enforce booking limit based on user type
        int maxAllowed = getMaxAllowed(userTypes.get(userId));
        if (confirmedCount >= maxAllowed)
            throw new IllegalStateException("User reached confirmed booking limit");

        // Check if the event still has space
        int confirmedForEvent = getConfirmedBookings(eventId).size();

        BookingStatus status =
                (confirmedForEvent < eventCapacity.get(eventId))
                        ? BookingStatus.CONFIRMED
                        : BookingStatus.WAITLISTED;

        Booking booking = new Booking(
                generateBookingId(),
                userId,
                eventId,
                LocalDateTime.now(),
                status
        );

        bookingsById.put(booking.getBookingId(), booking);
        // If event is full, also add this booking to the external waitlist file
        if (status == BookingStatus.WAITLISTED) {
            try {
                WaitlistManager.addToWaitlist(eventId, UserManager.getInstance().getUserById(userId));
            } catch (Exception e) {
                System.out.println("Error adding user to waitlist.");
            }
        }
        saveBookingsToFile();

        return booking;
    }

    /*
     * Cancels a booking.
     * If the booking was confirmed, the first waitlisted user is promoted.
     */
    public Booking cancelBooking(String bookingId) {

        Booking booking = bookingsById.get(bookingId);
        if (booking == null)
            throw new IllegalArgumentException("Booking not found");

        if (booking.getStatus() == BookingStatus.CANCELLED)
            return booking;

        BookingStatus previous = booking.getStatus();
        booking.setStatus(BookingStatus.CANCELLED);

        // If a confirmed booking is cancelled, promote from waitlist
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

    /*
     * Returns all bookings for a user, sorted by creation time.
     */
    public List<Booking> getUserBookings(String userId) {
        return bookingsById.values().stream()
                .filter(b -> b.getUserId().equals(userId))
                .sorted(Comparator.comparing(Booking::getCreatedAt))
                .collect(Collectors.toList());
    }

    /*
     * Returns all confirmed bookings for an event.
     */
    public List<Booking> getConfirmedBookings(String eventId) {
        return bookingsById.values().stream()
                .filter(b -> b.getEventId().equals(eventId))
                .filter(b -> b.getStatus() == BookingStatus.CONFIRMED)
                .sorted(Comparator.comparing(Booking::getCreatedAt))
                .collect(Collectors.toList());
    }

    /*
     * Returns all waitlisted bookings for an event.
     */
    public List<Booking> getWaitlist(String eventId) {
        return bookingsById.values().stream()
                .filter(b -> b.getEventId().equals(eventId))
                .filter(b -> b.getStatus() == BookingStatus.WAITLISTED)
                .sorted(Comparator.comparing(Booking::getCreatedAt))
                .collect(Collectors.toList());
    }

    /*
     * Checks whether the user already has an active booking for the event.
     */
    private boolean hasActiveBooking(String userId, String eventId) {
        return bookingsById.values().stream()
                .anyMatch(b ->
                        b.getUserId().equals(userId) &&
                                b.getEventId().equals(eventId) &&
                                b.getStatus() != BookingStatus.CANCELLED);
    }

    /*
     * Returns the booking limit for the given user type.
     */
    private int getMaxAllowed(UserType type) {
        return switch (type) {
            case STUDENT -> 3;
            case STAFF -> 5;
            case GUEST -> 1;
        };
    }

    /*
     * Generates a unique booking ID.
     */
    private String generateBookingId() {
        return "B" + (bookingCounter++);
    }

    private void promoteFirstWaitlisted(String eventId) {
        List<Booking> waitlist = getWaitlist(eventId);
        if (!waitlist.isEmpty()) {
            Booking next = waitlist.get(0);
            next.setStatus(BookingStatus.CONFIRMED);
        }
    }

    public void saveBookingsToFile() {
        //creates a writer and tries to write to the bookings.csv
        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter("bookings.csv"))) {
            //header of the csv
            writer.println("bookingID,userID,eventID,createdAt,bookingStatus");
            for (Booking booking : bookingsById.values()) {
                writer.println(booking.toCsvFormat());
            }
        } catch (java.io.IOException e) {
            System.out.println("Error saving something in bookings: " + e.getMessage());
        }
    }
}