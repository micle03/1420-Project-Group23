package com.group23.controllers.bookings;

import com.group23.Bookings.Booking;
import com.group23.Bookings.BookingService;
import com.group23.Events.Event;
import com.group23.Events.EventManager;
import com.group23.Users.model.User;
import com.group23.Users.service.UserManager;
import com.group23.controllers.MainViewController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BookingViewController implements Initializable {
  @FXML private ChoiceBox<User> bookUser; //User choice for booking
  @FXML private ChoiceBox<Event> bookEvent; //Event choice for booking
  @FXML private ChoiceBox<User> cancelUser; //User choice for cancellation
  @FXML private ChoiceBox<Booking> cancelEvent; //Event choice for cancellation
  @FXML private ChoiceBox<User> userBookings; //Box to choose users to view bookings of
  @FXML private ChoiceBox<Event> eventBookings; //Box to choose event to view bookings of

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    //get instances and create lists of events and users
    UserManager userManager = UserManager.getInstance();
    EventManager eventManager = EventManager.getInstance();
    BookingService bookingService = BookingService.getInstance();
    List<User> users = userManager.getAllUsers();
    List<Event> events = eventManager.listAllEvents();

    //convert User and Event objects to strings with info
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
    userBookings.setConverter(userNameConverter);
    eventBookings.setConverter(eventNameConverter);

    //set options for choiceboxes
    bookUser.getItems().setAll(users);
    cancelUser.getItems().setAll(users);
    bookEvent.getItems().setAll(events);
    userBookings.getItems().setAll(users);
    eventBookings.getItems().setAll(events);

    //add listener for cancellation to only show cancellable events for user
    cancelUser.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      if(newValue == null) {
        cancelEvent.getItems().clear();
        return;
      }
      List<Booking> bookings = bookingService.getUserBookings(newValue.getUserId());
      cancelEvent.getItems().setAll(bookings);
    });
  }

  //method to call bookEvent from booking service, show alert if error
  @FXML private void bookEvent() {
    BookingService bookingService = BookingService.getInstance();
    try {
      bookingService.bookEvent(bookUser.getValue().getUserId(), bookEvent.getValue().getEventID());
      Alert alert =  new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Event Booking Created");
      alert.setHeaderText(null);
      alert.setContentText("Booked Event: "+bookUser.getValue().getName()+" | "+bookEvent.getValue().getTitle());
      alert.showAndWait();
    } catch (IllegalArgumentException | IllegalStateException e) {
      Alert alert =  new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Booking Creation Error");
      alert.setHeaderText(null);
      alert.setContentText(e.getMessage());
      alert.showAndWait();
    }
  }

  //cancel a booking and shows alert showing successful cancellation
  @FXML private void cancelBooking() {
    BookingService bookingService = BookingService.getInstance();
    bookingService.cancelBooking(cancelEvent.getValue().getBookingId());
    Alert alert =  new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Booking Cancellation");
    alert.setHeaderText(null);
    alert.setContentText("Cancelled Event: "+cancelUser.getValue().getName()+" | "+cancelEvent.getValue().getEventId());
    alert.showAndWait();
  }

 //methods to go to user bookings and event bookings page
  @FXML private void viewUserBookings() {
    MainViewController.getInstance().loadViewUserBookingsScreen(userBookings.getValue());
  }
  @FXML private void viewEventBookings() {
    MainViewController.getInstance().loadEventRosterScreen(eventBookings.getValue());
  }
}
