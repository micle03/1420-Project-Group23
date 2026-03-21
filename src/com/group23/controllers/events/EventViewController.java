package com.group23.controllers.events;

import com.group23.Events.Event;
import com.group23.Events.EventManager;
import com.group23.Events.EventType;
import com.group23.controllers.MainViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class EventViewController implements Initializable {
  //initialize event table and search stuff
  @FXML private TableView<Event> eventTable;
  @FXML private TableColumn<Event, String> idColumn;
  @FXML private TableColumn<Event, String> titleColumn;
  @FXML private TableColumn<Event, String> dateColumn;
  @FXML private TableColumn<Event, String> locationColumn;
  @FXML private TableColumn<Event, String> capacityColumn;
  @FXML private TableColumn<Event, String> statusColumn;
  @FXML private ChoiceBox<String> eventTypeBox;
  @FXML private TextField searchBox;
  private ObservableList<Event> eventList;

  EventManager eventManager = EventManager.getInstance();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    //initialize tables and set up dropdown
    eventList = FXCollections.observableArrayList(eventManager.listAllEvents());
    idColumn.setCellValueFactory(new PropertyValueFactory<>("eventID"));
    titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
    dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
    locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
    capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    eventTypeBox.setValue("Any");
    searchBox.setText("");
    eventTypeBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
      if(newValue.equals("Concert")) {
        eventList = FXCollections.observableArrayList(eventManager.filterByType(EventType.CONCERT));
      } else if(newValue.equals("Seminar")) {
        eventList = FXCollections.observableArrayList(eventManager.filterByType(EventType.SEMINAR));
      } else if(newValue.equals("Workshop")) {
        eventList = FXCollections.observableArrayList(eventManager.filterByType(EventType.WORKSHOP));
      } else {
        eventList = FXCollections.observableArrayList(eventManager.listAllEvents());
      }
      reloadTable();
    });
    searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
      eventList = FXCollections.observableArrayList(eventManager.searchByTitle(newValue));
      reloadTable();
    });
  }

  @FXML
  private void viewCreateEventForm() {
    //go to create event page
    MainViewController.getInstance().loadCreateEventScreen();
  }

  @FXML
  private void viewUpdateEventForm() {
    //go to update event page
    Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
    if (selectedEvent == null) return;
    MainViewController.getInstance().loadUpdateEventScreen(selectedEvent);
  }

  @FXML
  private void cancelEvent() {
    //on event cancel, check if event is selected, then show success
    Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
    if (selectedEvent == null) return;
    eventManager.cancelEvent(selectedEvent.getEventID());
    Alert alert =  new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Event Cancellation");
    alert.setHeaderText(null);
    alert.setContentText("Cancelled Event: "+selectedEvent.getTitle()+" | "+selectedEvent.getEventID());
    alert.showAndWait();
    reloadTable();
  }

  public void reloadTable() {
    //reload event table
    eventTable.setItems(eventList);
  }
}
