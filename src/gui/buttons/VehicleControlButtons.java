package gui.buttons;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import vehicles.Airplane;
import vehicles.MilitaryShip;
import vehicles.Vehicle;
import world.World;

import java.util.function.Consumer;

/**
 * A group of buttons for removing and changing parameters of a vehicle.
 */
public class VehicleControlButtons extends Group {
  private World world;
  private Vehicle vehicle;
  private Consumer<Vehicle> actionBeforeRemovingVehicle;
  @FXML private Button addMilitaryAirplaneButton;
  @FXML private Button removeVehicleButton;
  @FXML private Button randomizeCurrentRouteButton;
  @FXML private Button crashLanding;

  public VehicleControlButtons() {}

  public void initialize() {
    removeVehicleButton.setVisible(false);
    addMilitaryAirplaneButton.setVisible(false);
    randomizeCurrentRouteButton.setVisible(false);
    crashLanding.setVisible(false);
  }

  public void setActionBeforeRemovingVehicle(Consumer<Vehicle> action) {
    actionBeforeRemovingVehicle = action;
  }

  /**
   *
   * @param world the World object from which vehicles will be removed.
   */
  public void setWorld(World world) {
    this.world = world;
  }

  /**
   *
   * @param vehicle the vehicle to be controlled by the buttons.
   */
  public void setVehicle(Vehicle vehicle) {
    this.vehicle = vehicle;

    if (this.vehicle == null) {
      removeVehicleButton.setVisible(false);
      addMilitaryAirplaneButton.setVisible(false);
      randomizeCurrentRouteButton.setVisible(false);
      crashLanding.setVisible(false);
    }
    else {
      removeVehicleButton.setVisible(true);

      if (this.vehicle instanceof MilitaryShip) {
        addMilitaryAirplaneButton.setVisible(true);
        randomizeCurrentRouteButton.setVisible(false);
      }
      else {
        addMilitaryAirplaneButton.setVisible(false);
        randomizeCurrentRouteButton.setVisible(true);
      }

      if (this.vehicle instanceof Airplane) {
        crashLanding.setVisible(!((Airplane) this.vehicle).shouldCrash());
        randomizeCurrentRouteButton.setVisible(!((Airplane) this.vehicle).shouldCrash());
      }
      else {
        crashLanding.setVisible(false);
      }
    }
  }

  @FXML
  private void removeVehicle() {
    if (vehicle != null) {
      try {
        actionBeforeRemovingVehicle.accept(vehicle);
        world.removeVehicle(vehicle);
      }
      catch (InterruptedException e) {
        e.printStackTrace();
      }

      setVehicle(null);
    }
  }

  @FXML
  private void randomizeCurrentRoute() {
    vehicle.randomizeCurrentRoute();
  }

  @FXML
  private void addMilitaryAirplane() {
    if (vehicle instanceof MilitaryShip) {
      world.addMilitaryAirplane((MilitaryShip) vehicle);
    }
  }

  @FXML
  private void crashLanding() {
    if (vehicle instanceof Airplane) {
      ((Airplane) vehicle).crashLanding();
    }

    crashLanding.setVisible(false);
    randomizeCurrentRouteButton.setVisible(false);
  }
}
