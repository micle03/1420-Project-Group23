package com.group23.controllers.events;

import com.group23.Events.Concert;
import com.group23.Events.EventManager;
import com.group23.Events.Seminar;
import com.group23.Events.Workshop;
import com.group23.controllers.MainViewController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateEventViewController implements Initializable {
  //Fields for event information
  @FXML private TextField idField;
  @FXML private TextField titleField;
  @FXML private TextField dateField;
  @FXML private TextField locationField;
  @FXML private Spinner<Integer> capacitySpinner;
  @FXML private ChoiceBox<String> typeBox;
  @FXML private TextField specificField;
  @FXML private Label specificLabel;

  @FXML
  private void addEvent() {
    //on button press, get text from the fields
    String eventId = idField.getText();
    String title = titleField.getText();
    String date = dateField.getText();
    String location = locationField.getText();
    int capacity = capacitySpinner.getValue();
    String type = typeBox.getValue();
    String specific = specificField.getText();
    EventManager eventManager = EventManager.getInstance();
    //create specific event based on type
    try{
      if(type.equals("Concert")) {
        Concert concert = new Concert(eventId, title, date, location, capacity, "Active",  specific);
        eventManager.addEvent(concert);
      } else if (type.equals("Seminar")) {
        Seminar seminar = new Seminar(eventId, title, date, location, capacity, "Active",  specific);
        eventManager.addEvent(seminar);
      } else if (type.equals("Workshop")) {
        Workshop workshop = new Workshop(eventId, title, date, location, capacity, "Active", specific);
        eventManager.addEvent(workshop);
      }
      Alert alert =  new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Event Created");
      alert.setHeaderText(null);
      alert.setContentText("Created Event: "+title+" | "+eventId);
      alert.showAndWait();
    }catch(IllegalArgumentException e){
      //catch error, make alert
      Alert alert =  new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Event Creation Error");
      alert.setHeaderText(null);
      alert.setContentText("An event with this ID already exists!");
      alert.showAndWait();
    }

    //clear fields then show alert for success
    idField.clear();
    titleField.clear();
    dateField.clear();
    locationField.clear();
    typeBox.setValue(null);
    specificField.clear();


    //go back to event screen
    MainViewController.getInstance().loadEventScreen();
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    //limit spinner values, initialize dropdown
    SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 999, 0);
    capacitySpinner.setValueFactory(valueFactory);
    typeBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
      if(newValue == null) {
        specificLabel.setText("Specific:");
      } else if(newValue.equals("Concert")) {
        specificLabel.setText("Age Restriction:");
      } else if(newValue.equals("Seminar")) {
        specificLabel.setText("Speaker Name:");
      } else if(newValue.equals("Workshop")) {
        specificLabel.setText("Topic:");
      }
    });
  }
}
