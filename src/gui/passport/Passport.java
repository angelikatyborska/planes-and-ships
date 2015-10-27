package gui.passport;

import core.Passenger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class Passport extends Group {
  private Passenger passenger;

  public Passport(Passenger passenger) {
    this.passenger = passenger;

    PassportController passportController = new PassportController(passenger);
    this.getChildren().add(passportController.getGridPane());
  }
}
