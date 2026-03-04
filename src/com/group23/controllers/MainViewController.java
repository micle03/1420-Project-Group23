package com.group23.controllers;

import com.group23.Bookings.Booking;
import com.group23.Events.Event;
import com.group23.Users.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainViewController {
  @FXML private StackPane contentArea;
  private static MainViewController instance;

  @FXML
  public void initialize() {
    instance = this;
  }

  public static MainViewController getInstance() {
    return instance;
  }
  public void loadUserScreen() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group23/user-view.fxml"));
      Parent page = loader.load();

      UserViewController controller = loader.getController();
      controller.reloadTable();

      contentArea.getChildren().setAll(page);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  public void loadEventScreen() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group23/event-view.fxml"));
      Parent page = loader.load();

      EventViewController controller = loader.getController();
      controller.reloadTable();

      contentArea.getChildren().setAll(page);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  public void loadBookingScreen() {
    loadPage("/com/group23/booking-view.fxml");
  }
  public void loadWaitlistScreen() {
    loadPage("/com/group23/waitlist-view.fxml");
  }

  public void loadCreateUserScreen() {
    loadPage("/com/group23/create-user-view.fxml");
  }
  public void loadUserDetailScreen(User user) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group23/user-detail-view.fxml"));
      Parent page = loader.load();

      UserDetailViewController controller = loader.getController();
      controller.setUser(user);

      contentArea.getChildren().setAll(page);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void loadCreateEventScreen() {
    loadPage("/com/group23/create-event-view.fxml");
  }
  public void loadUpdateEventScreen(Event event) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group23/update-event-view.fxml"));
      Parent page = loader.load();

      UpdateEventViewController controller = loader.getController();
      controller.setEvent(event);

      contentArea.getChildren().setAll(page);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void loadViewUserBookingsScreen(User user) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group23/user-bookings-view.fxml"));
      Parent page = loader.load();

      ViewUserBookingsController controller = loader.getController();
      controller.setUser(user);

      contentArea.getChildren().setAll(page);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void loadPage(String fxmlFile) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
      Parent page = loader.load();
      contentArea.getChildren().setAll(page);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}