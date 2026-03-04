package com.group23.controllers;

import com.group23.Bookings.BookingService;
import com.group23.Users.model.User;
import com.group23.Users.service.UserManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.util.StringConverter;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BookingViewController implements Initializable {
  @FXML private ChoiceBox<User> bookUser;
  @FXML private ChoiceBox<String> bookEvent;
  @FXML private ChoiceBox<User> cancelUser;
  @FXML private ChoiceBox<String> cancelEvent;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    UserManager userManager = UserManager.getInstance();
    List<User> users = userManager.getAllUsers();

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

    bookUser.getItems().setAll(users);
    cancelUser.getItems().setAll(users);
    //Need to add events
  }

  @FXML
  private void bookEvent() {
    BookingService bookingService = BookingService.getInstance();
    bookingService.bookEvent(bookUser.getValue().getUserId(), bookEvent.getValue());
  }

  @FXML
  private void cancelBooking() {
    BookingService bookingService = BookingService.getInstance();
    bookingService.cancelBooking(cancelEvent.getValue());
  }
}
