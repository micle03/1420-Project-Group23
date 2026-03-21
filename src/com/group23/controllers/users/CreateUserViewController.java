package com.group23.controllers.users;

import com.group23.Users.service.UserManager;
import com.group23.controllers.MainViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class CreateUserViewController {
  //fields for user info
  @FXML private TextField idField;
  @FXML private TextField nameField;
  @FXML private TextField emailField;
  @FXML private ChoiceBox<String> userTypeBox;

  @FXML
  private void addUser() {
    //get user info from fields
    String userId = idField.getText();
    String name = nameField.getText();
    String email = emailField.getText();
    String type = userTypeBox.getValue();

    //run create user and catch errors and show either sucesss or fail alert
    UserManager userManager = UserManager.getInstance();
    try{
      userManager.createUser(userId, name, email, type);

      Alert alert =  new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("User Created");
      alert.setHeaderText(null);
      alert.setContentText("Created User: "+nameField.getText()+" | "+idField.getText());
      alert.showAndWait();
    }catch(IllegalArgumentException e){
      Alert alert =  new Alert(Alert.AlertType.ERROR);
      alert.setTitle("User Creation Error");
      alert.setHeaderText(null);
      alert.setContentText("A user with this ID already exists!");
      alert.showAndWait();
    }
    //clear fields then go to user screen
    idField.clear();
    nameField.clear();
    emailField.clear();
    userTypeBox.setValue(null);

    MainViewController.getInstance().loadUserScreen();
  }
}
