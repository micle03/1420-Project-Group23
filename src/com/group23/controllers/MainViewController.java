package com.group23.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainViewController {
  @FXML
  private StackPane contentArea;

  @FXML
  public void loadUserScreen() {
    loadPage("/com/group23/user-view.fxml");
  }

  @FXML
  public void loadEventScreen() {
    loadPage("/com/group23/event-view.fxml");
  }

  @FXML
  public void loadBookingScreen() {
    loadPage("/com/group23/booking-view.fxml");
  }

  @FXML
  public void loadWaitlistScreen() {
    loadPage("/com/group23/waitlist-view.fxml");
  }

  private void loadPage(String fxmlFile) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
      Parent page = loader.load();
      contentArea.getChildren().setAll(page);
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Error loading: " + fxmlFile);
    }
  }
}