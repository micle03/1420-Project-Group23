package com.group23.controllers;

import com.group23.Events.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class EventViewController implements Initializable {
  @FXML private TableView<Event> eventTable;
  @FXML private TableColumn<Event, String> idColumn;
  @FXML private TableColumn<Event, String> titleColumn;
  @FXML private TableColumn<Event, String> dateColumn;
  @FXML private TableColumn<Event, String> locationColumn;
  @FXML private TableColumn<Event, String> capacityColumn;
  @FXML private TableColumn<Event, String> typeColumn;
  @FXML private TableColumn<Event, String> statusColumn;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    idColumn.setCellValueFactory(new PropertyValueFactory<>("eventID"));
    titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
    dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
    locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
    capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
  }

  @FXML
  private void viewCreateEventForm() {
    MainViewController.getInstance().loadCreateEventScreen();
  }
}
