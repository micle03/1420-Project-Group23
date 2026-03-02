package com.group23.Bookings;

public class Booking {
    private String bookingID, userID, eventID, createdAt, bookingStatus;

    //creates the booking
    public Booking(String bookingID, String userID, String eventID, String createdAt, String bookingStatus) {
        this.bookingID = bookingID;
        this.userID = userID;
        this.eventID = eventID;
        this.createdAt = createdAt;
        this.bookingStatus = bookingStatus;
    }

    //getters
    public String getBookingID() { return bookingID; }

    public String getUserID() {
        return userID;
    }

    public String getEventID(){ return eventID; }

    public String getCreatedAt() { return createdAt; }

    public String getBookingStatus() { return bookingStatus; }
}
