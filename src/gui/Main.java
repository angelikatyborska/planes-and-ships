package gui;

import javafx.application.Application;
import javafx.scene.*;
import javafx.stage.Stage;


public class Main extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("Planes and Ships");
    primaryStage.setWidth(1100);
    primaryStage.setHeight(740);

    WorldPanel root = new WorldPanel();

    primaryStage.setScene(new Scene(root));

    root.start();

    primaryStage.setOnCloseRequest(we -> { root.shutDown(); });
    primaryStage.show();

  }
}
