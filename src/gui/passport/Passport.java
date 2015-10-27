package gui.passport;

import core.Passenger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.Label;

import java.io.IOException;

public class Passport extends Group {
  @FXML private Passenger passenger;
  @FXML private Label firstName;
  @FXML private Label lastName;
  @FXML private Label age;
  @FXML private Label PESEL;
  @FXML private Label hometown;

  public Passport(Passenger passenger) {
    this.passenger = passenger;

    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(Passport.class.getResource("passport.fxml"));
    loader.setController(this);
    try {
      getChildren().add(loader.load());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void initialize() {
    firstName.setText(passenger.getFirstName());
    lastName.setText(passenger.getLastName());
    age.setText(passenger.getAge() + " years old");
    PESEL.setText(passenger.getPESEL());
    hometown.setText(passenger.getHometown());
  }
}
