package gui;

import gui.buttons.VehicleControlButtons;
import gui.buttons.VehicleCreationButtons;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import vehicles.Vehicle;
import world.World;

import java.io.IOException;
import java.util.function.Consumer;

public class VehicleButtons extends Group {
  private final World world;
  private final double x;
  private final double y;
  private final double maxX;
  private VehicleControlButtons vehicleControlButtons;

  public VehicleButtons(World world, double x, double y, double maxX) {
    this.world = world;
    this.x = x;
    this.y = y;
    this.maxX = maxX;

    GridPane vehicleCreationButtonsPane = new GridPane();

    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(VehicleCreationButtons.class.getResource("vehicle-creation-buttons.fxml"));
    try {
      vehicleCreationButtonsPane = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    VehicleCreationButtons vehicleCreationButtons = loader.getController();
    vehicleCreationButtons.setWorld(world);

    getChildren().add(vehicleCreationButtonsPane);


    GridPane vehicleControlButtonsPane = new GridPane();

    FXMLLoader loader2 = new FXMLLoader();
    loader2.setLocation(VehicleControlButtons.class.getResource("vehicle-control-buttons.fxml"));
    try {
      vehicleControlButtonsPane = loader2.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    vehicleControlButtons = loader2.getController();
    vehicleControlButtons.setWorld(world);

    getChildren().add(vehicleControlButtonsPane);


  }

  public void setVehicle(Vehicle vehicle) {
    vehicleControlButtons.setVehicle(vehicle);
  }

  public void setActionBeforeRemovingVehicle(Consumer<Vehicle> action) {
    vehicleControlButtons.setActionBeforeRemovingVehicle(action);
  }
}
