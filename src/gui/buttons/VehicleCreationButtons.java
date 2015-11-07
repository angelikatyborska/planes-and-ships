package gui.buttons;

import javafx.fxml.FXML;
import javafx.scene.Group;
import world.World;

/**
 * A group of buttons that can add various types of vehicles that do not require being added by another vehicle.
 */
public class VehicleCreationButtons extends Group {
  private World world;

  public VehicleCreationButtons() {
  }

  /**
   *
   * @param world the World object to which vehicles will be added.
   */
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
