package com.group23.controllers.bookings;

import com.group23.Bookings.Booking;
import com.group23.Bookings.BookingService;
import com.group23.Events.Event;
import com.group23.Users.model.User;
import com.group23.Users.service.UserManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class EventRosterViewController implements Initializable {
  //get instances of user and booking and get confirmed and waitlisted tables
  private Event event;
  BookingService bookingService = BookingService.getInstance();
  UserManager userManager = UserManager.getInstance();
  @FXML private Label eventRosterLabel;
  @FXML private TableView<User> confirmedTable;
  @FXML private TableColumn<User, String> idColumn;
  @FXML private TableColumn<User, String> nameColumn;
  @FXML private TableColumn<User, String> emailColumn;
  @FXML private TableColumn<User, String> typeColumn;
  @FXML private TableView<User> waitlistedTable;
  @FXML private TableColumn<User, String> idWaitColumn;
  @FXML private TableColumn<User, String> nameWaitColumn;
  @FXML private TableColumn<User, String> emailWaitColumn;
  @FXML private TableColumn<User, String> typeWaitColumn;

  //sets event to show bookings of
  public void setEvent(Event event) {
    this.event = event;
    eventRosterLabel.setText(event.getTitle()+" Roster");
    reloadTable();
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    //initialize confirmed and waitlisted tables
    idColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
    typeColumn.setCellValueFactory(new PropertyValueFactory<>("userType"));
    idWaitColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
    nameWaitColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    emailWaitColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
    typeWaitColumn.setCellValueFactory(new PropertyValueFactory<>("userType"));
  }

  private void reloadTable() {
    //reload both tables to show users that booked event
    ObservableList<Booking> confirmedBookings = FXCollections.observableArrayList(bookingService.getConfirmedBookings(event.getEventID()));
    ObservableList<User> confirmedUsers = confirmedBookings.stream()
      .map(booking -> userManager.getUserById(booking.getUserId()))
      .filter(Objects::nonNull)
      .collect(Collectors.toCollection(FXCollections::observableArrayList));
    ObservableList<Booking> waitlistedBookings = FXCollections.observableArrayList(bookingService.getWaitlist(event.getEventID()));
    ObservableList<User> waitlistedUsers = waitlistedBookings.stream()
      .map(booking -> userManager.getUserById(booking.getUserId()))
      .filter(Objects::nonNull)
      .collect(Collectors.toCollection(FXCollections::observableArrayList));
    confirmedTable.setItems(confirmedUsers);
    waitlistedTable.setItems(waitlistedUsers);
  }
}
