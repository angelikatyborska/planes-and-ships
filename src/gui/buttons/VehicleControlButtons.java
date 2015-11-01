package gui.buttons;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import vehicles.Airplane;
import vehicles.MilitaryShip;
import vehicles.Vehicle;
import world.World;

import java.util.function.Consumer;

public class VehicleControlButtons extends Group {
  private World world;
  private Vehicle vehicle;
  private Consumer<Vehicle> actionBeforeRemovingVehicle;
  @FXML private Button addMilitaryAirplaneButton;
  @FXML private Button removeVehicleButton;
  @FXML private Button randomizeCurrentRouteButton;
  @FXML private Button crashLanding;

  public VehicleControlButtons() {
  }


  public void initialize() {
    removeVehicleButton.setVisible(false);
    addMilitaryAirplaneButton.setVisible(false);
    randomizeCurrentRouteButton.setVisible(false);
    crashLanding.setVisible(false);
  }

  public void setActionBeforeRemovingVehicle(Consumer<Vehicle> action) {
    actionBeforeRemovingVehicle = action;
  }

  public void setWorld(World world) {
    this.world = world;
  }

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
        crashLanding.setVisible(true);
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
     private void addMilitaryAirlane() {
    if (vehicle instanceof MilitaryShip) {
      world.addMilitaryAirplane((MilitaryShip) vehicle);
    }
  }

  @FXML
  private void crashLanding() {
    if (vehicle instanceof Airplane) {
      ((Airplane) vehicle).crashLanding();
    }
  }
}
