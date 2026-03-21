package com.group23.controllers.events;

import com.group23.Events.*;
import com.group23.controllers.MainViewController;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class UpdateEventViewController {
  private Event event;
  EventManager eventManager = EventManager.getInstance();

  @FXML private TextField idField;
  @FXML private TextField titleField;
  @FXML private TextField dateField;
  @FXML private TextField locationField;
  @FXML private Spinner<Integer> capacitySpinner;
  @FXML private TextField specificField;
  @FXML private Label specificLabel;

  public void setEvent(Event event) {
    //set event to update
    this.event = event;
    displayEventInfo();
  }

  private void displayEventInfo() {
    //set all the fields with the event information
    SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
    capacitySpinner.setValueFactory(valueFactory);
    if (event != null) {
      idField.setText(event.getEventID());
      idField.setEditable(false);
      titleField.setText(event.getTitle());
      dateField.setText(event.getDateTime());
      locationField.setText(event.getLocation());
      capacitySpinner.getValueFactory().setValue(event.getCapacity());
      if(event.getEventType() == EventType.CONCERT) {
        Concert concert = (Concert) event;
        specificLabel.setText("Age Restriction:");
        specificField.setText(concert.getAgeRestriction());
      } else if (event.getEventType() == EventType.SEMINAR) {
        Seminar seminar = (Seminar) event;
        specificLabel.setText("Speaker Name:");
        specificField.setText(seminar.getSpeakerName());
      } else if (event.getEventType() == EventType.WORKSHOP) {
        Workshop workshop = (Workshop) event;
        specificLabel.setText("Topic:");
        specificField.setText(workshop.getTopic());
      }
    }
  }

  @FXML private void updateEvent() {
    //update event based on info in fields and show success alert
    eventManager.updateEvent(idField.getText(), titleField.getText(), locationField.getText(), capacitySpinner.getValue(), specificField.getText(), specificLabel.getText());
    Alert alert =  new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Event Updated");
    alert.setHeaderText(null);
    alert.setContentText("Updated Event: "+titleField.getText()+" | "+idField.getText());
    alert.showAndWait();
    MainViewController.getInstance().loadEventScreen();
  }
}
