package com.group23.controllers;

import com.group23.Users.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class UserDetailViewController {
  private User user;

  @FXML private Label idLabel;
  @FXML private Label nameLabel;
  @FXML private Label emailLabel;
  @FXML private Label typeLabel;

  public void setUser(User user) {
    this.user = user;
    displayUserInfo();
  }

  private void displayUserInfo() {
    if (user != null) {
      idLabel.setText(user.getUserId());
      nameLabel.setText(user.getName());
      emailLabel.setText(user.getEmail());
      typeLabel.setText(user.getUserType());
    }
  }

  @FXML
  private void goBack() {
    MainViewController.getInstance().loadUserScreen();
  }
}
