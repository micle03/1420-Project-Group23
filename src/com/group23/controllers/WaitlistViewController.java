package com.group23.controllers;

import com.group23.Bookings.Booking;
import com.group23.Bookings.BookingService;
import com.group23.Events.Event;
import com.group23.Events.EventManager;
import com.group23.Users.model.User;
import com.group23.Waitlist.waitlistManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class WaitlistViewController implements Initializable {
  private Event event;
  private BookingService bookingService = BookingService.getInstance();
  waitlistManager WaitlistManager = waitlistManager.getInstance();
  private EventManager eventManager = EventManager.getInstance();
  @FXML private TableView<User> userTable;
  @FXML private TableColumn<User, String> idColumn;
  @FXML private TableColumn<User, String> nameColumn;
  @FXML private TableColumn<User, String> emailColumn;
  @FXML private TableColumn<User, String> typeColumn;
  @FXML private ChoiceBox<Event> eventBox;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    idColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
    typeColumn.setCellValueFactory(new PropertyValueFactory<>("userType"));
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
    eventBox.setConverter(eventNameConverter);

    List<Event> events = eventManager.listAllEvents();
    eventBox.getItems().setAll(events);

    eventBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      if(newValue == null) {
        userTable.getItems().clear();
        return;
      }
      event = newValue;
      try {
        reloadTable();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }

  private void reloadTable() throws IOException {
    ObservableList<User> users = FXCollections.observableArrayList(WaitlistManager.getWaitlistedUsers(event.getEventID()));
    userTable.setItems(users);
  }

  @FXML private void removeBooking() throws IOException {
    User selectedUser = userTable.getSelectionModel().getSelectedItem();
    if (selectedUser == null) return;
    WaitlistManager.removeFromWaitlist(event.getEventID(), selectedUser.getUserId());
  }
}
