package com.group23.controllers;

import com.group23.Users.model.User;
import com.group23.Users.service.UserManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class UserViewController implements Initializable {
  @FXML private TableView<User> userTable;
  @FXML private TableColumn<User, String> idColumn;
  @FXML private TableColumn<User, String> nameColumn;
  @FXML private TableColumn<User, String> emailColumn;
  @FXML private TableColumn<User, String> typeColumn;

  private UserManager userManager = UserManager.getInstance();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    idColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
    typeColumn.setCellValueFactory(new PropertyValueFactory<>("userType"));
  }

  public void reloadTable() {
    ObservableList<User> observableUsers =
      FXCollections.observableArrayList(userManager.getAllUsers());
    userTable.setItems(observableUsers);
  }

  @FXML
  private void viewCreateUserForm() {
    MainViewController.getInstance().loadCreateUserScreen();
  }

  @FXML
  private void handleViewUser() {
    User selectedUser = userTable.getSelectionModel().getSelectedItem();
    if (selectedUser == null) return;
    MainViewController.getInstance().loadUserDetailScreen(selectedUser);
  }
}
