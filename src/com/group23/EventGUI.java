package com.group23;

import com.group23.MainPackage.FileReaderMain;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EventGUI extends Application {  @Override
  public void start(Stage stage) throws IOException {
    FileReaderMain.readFiles();
    FXMLLoader fxmlLoader = new FXMLLoader(EventGUI.class.getResource("main-view.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
    stage.setTitle("Event Manager");
    stage.setScene(scene);
    stage.show();
  }
}
