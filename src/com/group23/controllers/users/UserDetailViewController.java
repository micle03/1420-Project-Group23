package com.group23.controllers.users;

import com.group23.Users.model.User;
import com.group23.controllers.MainViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class UserDetailViewController {
  private User user;

  @FXML private Label idLabel;
  @FXML private Label nameLabel;
  @FXML private Label emailLabel;
  @FXML private Label typeLabel;

  public void setUser(User user) {
    //set user to view details of
    this.user = user;
    displayUserInfo();
  }

  private void displayUserInfo() {
    //show text from user info
    if (user != null) {
      idLabel.setText(user.getUserId());
      nameLabel.setText(user.getName());
      emailLabel.setText(user.getEmail());
      typeLabel.setText(user.getUserType());
    }
  }

  @FXML
  private void goBack() {
    //go to user screen
    MainViewController.getInstance().loadUserScreen();
  }
}
