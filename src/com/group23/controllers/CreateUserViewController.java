package com.group23.controllers;

import com.group23.Users.service.UserManager;
import javafx.fxml.FXML;
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
    userManager.createUser(userId, name, email, type);

    idField.clear();
    nameField.clear();
    emailField.clear();
    userTypeBox.setValue(null);

    MainViewController.getInstance().loadUserScreen();
  }
}
