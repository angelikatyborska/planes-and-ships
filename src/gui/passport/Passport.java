package gui.passport;

import core.Passenger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * A group of labels to print passenger's information.
 * @see Passenger
 */
public class Passport {
  @FXML private Passenger passenger;
  @FXML private Label firstName;
  @FXML private Label lastName;
  @FXML private Label age;
  @FXML private Label PESEL;
  @FXML private Label hometown;

  public Passport() {
    this.passenger = null;
  }

  /**
   *
   * @param passenger The passenger whose information should be shown
   */
  public void setPassenger(Passenger passenger) {
    this.passenger = passenger;
    firstName.setText(passenger.getFirstName());
    lastName.setText(passenger.getLastName());
    age.setText(passenger.getAge() + " years old");
    PESEL.setText(passenger.getPESEL());
    hometown.setText(passenger.getHometown());
  }
}
