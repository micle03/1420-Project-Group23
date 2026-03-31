package com.group23.Bookings; // This must match the src package name

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookingServiceTest {

  private BookingService bookingService;

  @BeforeEach
  void setUp() {
    bookingService = new BookingService();

    // Users
    bookingService.registerUser("U001", "Student");
    bookingService.registerUser("U002", "Student");
    bookingService.registerUser("U003", "Staff");
    bookingService.registerUser("U004", "Guest");
    bookingService.registerUser("U005", "Student");

    // Events
    bookingService.registerEvent("E101", 2, true);
    bookingService.registerEvent("E102", 1, true);
    bookingService.registerEvent("E103", 3, false);
    bookingService.registerEvent("E104", 5, true);
    bookingService.registerEvent("E105", 5, true);
    bookingService.registerEvent("E106", 5, true);
    bookingService.registerEvent("E107", 5, true);
  }

  @Test
  void bookingUnderCapacityShouldBeConfirmed() {
    Booking booking = bookingService.bookEvent("U001", "E101");

    assertNotNull(booking);
    assertEquals(BookingStatus.CONFIRMED, booking.getStatus());
    assertEquals("U001", booking.getUserId());
    assertEquals("E101", booking.getEventId());
  }

  @Test
  void bookingWhenEventIsFullShouldBeWaitlisted() {
    Booking first = bookingService.bookEvent("U001", "E102");
    Booking second = bookingService.bookEvent("U002", "E102");

    assertEquals(BookingStatus.CONFIRMED, first.getStatus());
    assertEquals(BookingStatus.WAITLISTED, second.getStatus());
  }

  @Test
  void duplicateBookingShouldThrowException() {
    bookingService.bookEvent("U001", "E101");

    assertThrows(IllegalStateException.class, () -> {
      bookingService.bookEvent("U001", "E101");
    });
  }

  /*    @Test
      void cancelledEventShouldNotAllowBooking() {
          assertThrows(IllegalStateException.class, () -> {
              bookingService.bookEvent("U001", "E103");
          });
      }
   */
  @Test
  void studentBookingLimitShouldBeEnforced() {
    bookingService.bookEvent("U001", "E104");
    bookingService.bookEvent("U001", "E105");
    bookingService.bookEvent("U001", "E106");

    assertThrows(IllegalStateException.class, () -> {
      bookingService.bookEvent("U001", "E107");
    });
  }

  @Test
  void guestBookingLimitShouldBeEnforced() {
    bookingService.bookEvent("U004", "E104");

    assertThrows(IllegalStateException.class, () -> {
      bookingService.bookEvent("U004", "E105");
    });
  }

  @Test
  void cancelBookingShouldChangeStatusToCancelled() {
    Booking booking = bookingService.bookEvent("U001", "E101");
    Booking cancelled = bookingService.cancelBooking(booking.getBookingId());

    assertEquals(BookingStatus.CANCELLED, cancelled.getStatus());
  }

  @Test
  void cancelConfirmedBookingShouldPromoteFirstWaitlistedUser() {
    Booking first = bookingService.bookEvent("U001", "E102");
    Booking second = bookingService.bookEvent("U002", "E102");

    bookingService.cancelBooking(first.getBookingId());

    List<Booking> confirmed = bookingService.getConfirmedBookings("E102");
    List<Booking> waitlist = bookingService.getWaitlist("E102");

    assertTrue(confirmed.stream().anyMatch(b ->
            b.getUserId().equals("U002") &&
                    b.getStatus() == BookingStatus.CONFIRMED));

    assertFalse(waitlist.stream().anyMatch(b ->
            b.getUserId().equals("U002") &&
                    b.getStatus() == BookingStatus.WAITLISTED));

    assertEquals(BookingStatus.CONFIRMED, second.getStatus());
  }

  @Test
  void getUserBookingsShouldReturnOnlyThatUsersBookings() {
    bookingService.bookEvent("U001", "E101");
    bookingService.bookEvent("U001", "E104");
    bookingService.bookEvent("U002", "E105");

    List<Booking> userBookings = bookingService.getUserBookings("U001");

    assertEquals(2, userBookings.size());
    assertTrue(userBookings.stream().allMatch(b -> b.getUserId().equals("U001")));
  }

  @Test
  void getConfirmedBookingsShouldReturnOnlyConfirmedBookings() {
    bookingService.bookEvent("U001", "E102");
    bookingService.bookEvent("U002", "E102");

    List<Booking> confirmed = bookingService.getConfirmedBookings("E102");

    assertEquals(1, confirmed.size());
    assertTrue(confirmed.stream().allMatch(b -> b.getStatus() == BookingStatus.CONFIRMED));
  }

  @Test
  void getWaitlistShouldReturnOnlyWaitlistedBookings() {
    bookingService.bookEvent("U001", "E102");
    bookingService.bookEvent("U002", "E102");

    List<Booking> waitlist = bookingService.getWaitlist("E102");

    assertEquals(1, waitlist.size());
    assertTrue(waitlist.stream().allMatch(b -> b.getStatus() == BookingStatus.WAITLISTED));
  }
}