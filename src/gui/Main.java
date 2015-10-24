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

    WorldPanel root = new WorldPanel();

    primaryStage.setScene(new Scene(root));

    root.start();

    primaryStage.setOnCloseRequest(we -> { root.shutDown(); });
    primaryStage.show();

    // TODO: change reentrant locks to synchronized blocks in the whole project?
  }
}
