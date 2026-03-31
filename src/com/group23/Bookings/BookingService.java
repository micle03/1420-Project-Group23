package com.group23;

import com.group23.Waitlist.waitlistManager;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/*
 * BookingStatus defines the possible states of a booking.
 * Each booking can only be in one of these states.
 */
enum BookingStatus {
    CONFIRMED,
    WAITLISTED,
    CANCELLED
}

/*
 * UserType determines booking limits for users.
 * Different user types have different maximum confirmed bookings.
 */
enum UserType {
    STUDENT,
    STAFF,
    GUEST
}

/*
 * Booking represents a single reservation linking a user to an event.
 * It stores identifying information and the current booking status.
 */
class Booking {
    private final String bookingId;
    private final String userId;
    private final String eventId;
    private final LocalDateTime createdAt;
    private BookingStatus status;

    public Booking(String bookingId, String userId, String eventId,
                   LocalDateTime createdAt, BookingStatus status) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.eventId = eventId;
        this.createdAt = createdAt;
        this.status = status;
    }

    public String getBookingId() { return bookingId; }
    public String getUserId() { return userId; }
    public String getEventId() { return eventId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public BookingStatus getStatus() { return status; }

    public void setStatus(BookingStatus status) { this.status = status; }
}

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
    private final waitlistManager waitlistManager = new waitlistManager();

    /*
     * Registers a user and their type.
     */
    public void registerUser(String userId, UserType type) {
        userTypes.put(userId, type);
    }

    /*
     * Registers an event with its capacity and active status.
     */
    public void registerEvent(String eventId, int capacity, boolean active) {
        eventCapacity.put(eventId, capacity);
        eventActive.put(eventId, active);

        // Create a waitlist file for the event
        waitlistManager.createWaitlist(eventId);
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
                waitlistManager.addToWaitlist(eventId, userId + ",temp");
            } catch (Exception e) {
                System.out.println("Error adding user to waitlist.");
            }
        }

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
            try {
                String promotedUser = waitlistManager.promoteUser(booking.getEventId());

                if (promotedUser != null) {
                    bookingsById.values().stream()
                            .filter(b -> promotedUser.contains(b.getUserId()))
                            .filter(b -> b.getEventId().equals(booking.getEventId()))
                            .filter(b -> b.getStatus() == BookingStatus.WAITLISTED)
                            .findFirst()
                            .ifPresent(b -> b.setStatus(BookingStatus.CONFIRMED));
                }

            } catch (Exception e) {
                System.out.println("Error promoting waitlisted user.");
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
}