package com.group23.controllers.users;

import com.group23.Users.service.UserManager;
import com.group23.controllers.MainViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class CreateUserViewController {
  @FXML private TextField idField;
  @FXML private TextField nameField;
  @FXML private TextField emailField;
  @FXML private ChoiceBox<String> userTypeBox;

  @FXML
  private void addUser() {
    String userId = idField.getText();
    String name = nameField.getText();
    String email = emailField.getText();
    String type = userTypeBox.getValue();

    UserManager userManager = UserManager.getInstance();
    try{
      userManager.createUser(userId, name, email, type);
    }catch(IllegalArgumentException e){
      Alert alert =  new Alert(Alert.AlertType.ERROR);
      alert.setTitle("User Creation Error");
      alert.setHeaderText(null);
      alert.setContentText("A user with this ID already exists!");
      alert.showAndWait();
    }

    idField.clear();
    nameField.clear();
    emailField.clear();
    userTypeBox.setValue(null);

    MainViewController.getInstance().loadUserScreen();
  }
}
