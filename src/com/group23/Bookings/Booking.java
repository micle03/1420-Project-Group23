package com.group23.Bookings;

import java.time.LocalDateTime;

public class Booking {
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

    //this formats the booking for a csv file
    public String toCsvFormat() {
        return String.format("%s,%s,%s,%s,%s", bookingId, userId, eventId, createdAt, status);
    }
}
