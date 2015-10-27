package gui;

import gui.buttons.VehicleCreationButtons;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import vehicles.MilitaryShip;
import vehicles.Vehicle;
import world.World;

import java.io.IOException;
import java.util.function.Consumer;

public class VehicleControlButtons extends Group {
  private final World world;
//  private Button newCivilAirplaneButton;
//  private Button newCivilShipButton;
//  private Button newMilitaryShipButton;
  private Button newMilitaryAirplaneButton;
  private Button removeVehicleButton;
  private Button changeVehicleRouteButton;
  private final double x;
  private final double y;
  private final double maxX;
  private Vehicle currentVehicle;
  private Consumer<Vehicle> actionBeforeRemovingVehicle;

  public VehicleControlButtons(World world, double x, double y, double maxX) {
    this.world = world;
    this.x = x;
    this.y = y;
    this.maxX = maxX;
    addButtons();
    addEventHandlers();

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
  }

  public void setCurrentVehicle(Vehicle vehicle) {
    currentVehicle = vehicle;

    if (currentVehicle == null) {
      removeVehicleButton.setVisible(false);
      newMilitaryAirplaneButton.setVisible(false);
      changeVehicleRouteButton.setVisible(false);
    }
    else {
      removeVehicleButton.setVisible(true);

      if (currentVehicle instanceof MilitaryShip) {
        newMilitaryAirplaneButton.setVisible(true);
        changeVehicleRouteButton.setVisible(false);
      }
      else {
        newMilitaryAirplaneButton.setVisible(false);
        changeVehicleRouteButton.setVisible(true);
      }
    }
  }

  public void setActionBeforeRemovingVehicle(Consumer<Vehicle> action) {
    actionBeforeRemovingVehicle = action;
  }

  private void removeCurrentVehicle() {
    if (currentVehicle != null) {
      try {
        actionBeforeRemovingVehicle.accept(currentVehicle);
        world.removeVehicle(currentVehicle);
      }
      catch (InterruptedException e) {
        e.printStackTrace();
      }

      setCurrentVehicle(null);
    }
  }

  private void addEventHandlers() {
//    newCivilAirplaneButton.setOnAction(e -> world.addCivilAirplane());
//
//    newCivilShipButton.setOnAction(e -> world.addCivilShip());
//
//    newMilitaryShipButton.setOnAction(e -> world.addMilitaryShip());

    removeVehicleButton.setOnAction(e -> removeCurrentVehicle());

    newMilitaryAirplaneButton.setOnAction(e -> world.addMilitaryAirplane((MilitaryShip) currentVehicle));

    changeVehicleRouteButton.setOnAction(e -> currentVehicle.randomizeCurrentRoute());
  }

  private void addButtons() {
    // TODO: section out visually buttons for adding vehicles so that users do not think that those buttons are connected to the current location
//    newCivilAirplaneButton = new Button();
//    newCivilShipButton = new Button();
//    newMilitaryShipButton = new Button();
    newMilitaryAirplaneButton = new Button();
    removeVehicleButton = new Button();
    changeVehicleRouteButton = new Button();

//    newCivilAirplaneButton.setTooltip(new Tooltip("new civil airplane"));
//    newCivilShipButton.setTooltip(new Tooltip("new civil ship"));
//    newMilitaryShipButton.setTooltip(new Tooltip("new military ship"));
    newMilitaryAirplaneButton.setTooltip(new Tooltip("new military airplane"));
    removeVehicleButton.setTooltip(new Tooltip("remove vehicle"));
    changeVehicleRouteButton.setTooltip(new Tooltip("randomize route"));

    double buttonSpacing = 40;

//    newCivilAirplaneButton.setLayoutX(x + 220 - 2 * buttonSpacing);
//    newCivilAirplaneButton.setLayoutY(y - 40);
//
//    newCivilShipButton.setLayoutX(x + 220 - buttonSpacing);
//    newCivilShipButton.setLayoutY(y - 40);
//
//    newMilitaryShipButton.setLayoutX(x + 220);
//    newMilitaryShipButton.setLayoutY(y - 40);

    removeVehicleButton.setLayoutX(maxX - 50);
    removeVehicleButton.setLayoutY(27);

    changeVehicleRouteButton.setLayoutX(maxX - 50);
    changeVehicleRouteButton.setLayoutY(27 + buttonSpacing);

    newMilitaryAirplaneButton.setLayoutX(maxX - 50);
    newMilitaryAirplaneButton.setLayoutY(27 + 2 * buttonSpacing);
//
//    newCivilAirplaneButton.getStyleClass().add("button");
//    newCivilAirplaneButton.getStyleClass().add("add-airplane-button");
//    newCivilAirplaneButton.getStyleClass().add("civil-airplane");
//
//    newCivilShipButton.getStyleClass().add("button");
//    newCivilShipButton.getStyleClass().add("add-ship-button");
//    newCivilShipButton.getStyleClass().add("civil-ship");
//
//    newMilitaryShipButton.getStyleClass().add("button");
//    newMilitaryShipButton.getStyleClass().add("add-ship-button");
//    newMilitaryShipButton.getStyleClass().add("military");

    newMilitaryAirplaneButton.getStyleClass().add("button");
    newMilitaryAirplaneButton.getStyleClass().add("add-airplane-button");
    newMilitaryAirplaneButton.getStyleClass().add("military");

    removeVehicleButton.getStyleClass().add("button");
    removeVehicleButton.getStyleClass().add("remove-vehicle-button");

    changeVehicleRouteButton.getStyleClass().add("button");
    changeVehicleRouteButton.getStyleClass().add("change-route-button");

//    getChildren().add(newCivilAirplaneButton);
//    getChildren().add(newCivilShipButton);
//    getChildren().add(newMilitaryShipButton);
    getChildren().add(newMilitaryAirplaneButton);
    getChildren().add(removeVehicleButton);
    getChildren().add(changeVehicleRouteButton);
    removeVehicleButton.setVisible(false);
    newMilitaryAirplaneButton.setVisible(false);
    changeVehicleRouteButton.setVisible(false);
  }
}
