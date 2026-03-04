package com.group23.controllers;

import com.group23.Events.Event;
import com.group23.Events.EventManager;
import com.group23.Users.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

  EventManager eventManager = EventManager.getInstance();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    idColumn.setCellValueFactory(new PropertyValueFactory<>("eventID"));
    titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
    dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
    locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
    capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
  }

  @FXML
  private void viewCreateEventForm() {
    MainViewController.getInstance().loadCreateEventScreen();
  }

  public void reloadTable() {
    ObservableList<Event> observableEvents =
      FXCollections.observableArrayList(eventManager.listAllEvents());
    eventTable.setItems(observableEvents);
  }
}
