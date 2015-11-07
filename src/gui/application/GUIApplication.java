package gui.application;

import gui.worldpanel.WorldPanel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;


public class GUIApplication extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("Planes and Ships");

    GridPane worldPanelPane = new GridPane();

    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(WorldPanel.class.getResource("world-panel.fxml"));
    try {
      worldPanelPane = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    Scene scene = new Scene(worldPanelPane);

    primaryStage.setScene(scene);
    //primaryStage.setResizable(false);

    primaryStage.setOnCloseRequest(we -> ((WorldPanel) loader.getController()).shutDown());
    primaryStage.show();
  }
}
