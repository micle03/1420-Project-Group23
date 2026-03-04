package com.group23.controllers;

import com.group23.Bookings.Booking;
import com.group23.Bookings.BookingService;
import com.group23.Events.Event;
import com.group23.Events.EventManager;
import com.group23.Users.model.User;
import com.group23.Users.service.UserManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BookingViewController implements Initializable {
  @FXML private ChoiceBox<User> bookUser;
  @FXML private ChoiceBox<Event> bookEvent;
  @FXML private ChoiceBox<User> cancelUser;
  @FXML private ChoiceBox<Booking> cancelEvent;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    UserManager userManager = UserManager.getInstance();
    EventManager eventManager = EventManager.getInstance();
    BookingService bookingService = BookingService.getInstance();
    List<User> users = userManager.getAllUsers();
    List<Event> events = eventManager.listAllEvents();

    StringConverter<User> userNameConverter = new StringConverter<>() {
      @Override
      public String toString(User user) {
        if (user == null) return null;
        return user.getName() + " | " + user.getUserId();
      }

      @Override
      public User fromString(String s) {
        return null;
      }
    };
    bookUser.setConverter(userNameConverter);
    cancelUser.setConverter(userNameConverter);
    StringConverter<Event> eventNameConverter = new StringConverter<>() {
      @Override
      public String toString(Event event) {
        if (event == null) return null;
        return event.getTitle() + " | " + event.getEventID();
      }

      @Override
      public Event fromString(String s) {
        return null;
      }
    };
    bookEvent.setConverter(eventNameConverter);
    StringConverter<Booking> bookNameConverter = new StringConverter<>() {
      @Override
      public String toString(Booking booking) {
        if (booking == null) return null;
        return eventManager.getEvent(booking.getEventId()).getTitle() + " | " + booking.getEventId();
      }

      @Override
      public Booking fromString(String s) {
        return null;
      }
    };
    cancelEvent.setConverter(bookNameConverter);

    bookUser.getItems().setAll(users);
    cancelUser.getItems().setAll(users);
    bookEvent.getItems().setAll(events);

    cancelUser.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      if(newValue == null) {
        cancelEvent.getItems().clear();
        return;
      }
      List<Booking> bookings = bookingService.getUserBookings(newValue.getUserId());
      cancelEvent.getItems().setAll(bookings);
    });
  }

  @FXML
  private void bookEvent() {
    BookingService bookingService = BookingService.getInstance();
    bookingService.bookEvent(bookUser.getValue().getUserId(), bookEvent.getValue().getEventID());
    System.out.println("Booked Event: "+bookUser.getValue().getName()+" | "+bookEvent.getValue().getTitle());
  }

  @FXML
  private void cancelBooking() {
    BookingService bookingService = BookingService.getInstance();
    bookingService.cancelBooking(cancelEvent.getValue().getBookingId());
    System.out.println("Cancelled Event: "+cancelUser.getValue().getName()+" | "+cancelEvent.getValue().getEventId());
  }
}
