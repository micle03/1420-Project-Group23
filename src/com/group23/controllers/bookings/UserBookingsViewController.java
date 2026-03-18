package com.group23.controllers.bookings;

import com.group23.Bookings.Booking;
import com.group23.Bookings.BookingService;
import com.group23.Bookings.BookingStatus;
import com.group23.Events.EventManager;
import com.group23.Users.model.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class UserBookingsViewController implements Initializable {
  private User user;
  @FXML private Label userBookingsLabel; //label to show chosen event
  @FXML private TableView<Booking> bookingTable;
  @FXML private TableColumn<Booking, String> idColumn;
  @FXML private TableColumn<Booking, String> nameColumn;
  @FXML private TableColumn<Booking, String> dateColumn;
  @FXML private TableColumn<Booking, String> statusColumn;
  //instances for events and bookings
  EventManager eventManager = EventManager.getInstance();
  BookingService bookingService = BookingService.getInstance();

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    //initialize booking table
    idColumn.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
    nameColumn.setCellValueFactory(cellData -> {
      Booking booking = cellData.getValue();
      return new SimpleStringProperty(eventManager.getEvent(booking.getEventId()).getTitle());
    });
    dateColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
    statusColumn.setCellValueFactory(cellData -> {
      Booking booking = cellData.getValue();
      BookingStatus bookingStatus = booking.getStatus();
      String status = "";
      if(bookingStatus == BookingStatus.CONFIRMED) {
        status = "Confirmed";
      } else if(bookingStatus == BookingStatus.CANCELLED) {
        status = "Cancelled";
      } else if (bookingStatus == BookingStatus.WAITLISTED) {
        status = "Waitlisted";
      }
      return new SimpleStringProperty(status);
    });
  }

  //controller is given user to view bookings of, this sets the user
  public void setUser(User user) {
    this.user = user;
    userBookingsLabel.setText(user.getName()+"'s bookings");
    reloadTable();
  }

  //reload table to show given user info
  private void reloadTable() {
    ObservableList<Booking> bookings = FXCollections.observableArrayList(bookingService.getUserBookings(user.getUserId()));
    bookingTable.setItems(bookings);
  }
}
