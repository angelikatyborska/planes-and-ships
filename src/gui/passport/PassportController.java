package gui.passport;

import core.Passenger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class PassportController {
  @FXML private Passenger passenger;
  @FXML public String name = "Basia";
  private GridPane gridPane;
  public Label firstName;
  public Label lastName;
  public Label age;
  public Label PESEL;
  public Label hometown;

  public PassportController(Passenger passenger) {
    this.passenger = passenger;

    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(Passport.class.getResource("passport.fxml"));
    loader.setController(this);
    try {
      gridPane = (GridPane) loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public GridPane getGridPane() {
    return gridPane;
  }

  public void initialize() {
    firstName.setText(passenger.getFirstName());
    lastName.setText(passenger.getLastName());
    age.setText(passenger.getAge() + " years old");
    PESEL.setText(passenger.getPESEL());
    hometown.setText(passenger.getHometown());
  }
}
