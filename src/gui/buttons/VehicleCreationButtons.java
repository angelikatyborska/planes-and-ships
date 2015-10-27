package gui.buttons;

import javafx.fxml.FXML;
import javafx.scene.Group;
import world.World;

public class VehicleCreationButtons extends Group {
  private World world;

  public VehicleCreationButtons() {
  }

  public void setWorld(World world) {
    this.world = world;
  }

  @FXML
  private void addCivilAirplane() {
    world.addCivilAirplane();
  }

  @FXML
  private void addCivilShip() {
    world.addCivilShip();
  }

  @FXML
  private void addMilitaryShip() {
    world.addMilitaryShip();
  }
}
