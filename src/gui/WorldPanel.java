package gui;

import core.Coordinates;
import javafx.animation.AnimationTimer;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import stopovers.*;
import vehicles.Vehicle;
import world.*;

import java.util.HashMap;

public class WorldPanel extends Group {
  private Canvas worldCanvas;
  private Canvas detailCanvas;
  private Button newCivilAirplaneButton;
  private Button newCivilShipButton;
  private Button newMilitaryShipButton;
  private Button newMilitaryAirplaneButton;
  private Button removeVehicleButton;
  private World world;
  private Vehicle currentVehicle;
  private WorldMap map;
  private WorldDrawer drawer;
  private ObjectDetailDrawer detailDrawer;
  private final double clickErrorMargin = 15;
  private final double worldWidth;
  private final double worldHeight;
  private final double detailPanelWidth;


  public WorldPanel() {
    this.worldWidth = 800;
    this.detailPanelWidth = 300;
    this.worldHeight = 640;
    worldCanvas = new Canvas(worldWidth, worldHeight);
    detailCanvas = new Canvas(detailPanelWidth, worldHeight);

    world = WorldBuilder.build();

    drawer = new WorldDrawer(world, worldCanvas.getGraphicsContext2D(), getImages(), getColors(),  worldWidth, worldHeight);
    detailDrawer = new ObjectDetailDrawer(detailCanvas.getGraphicsContext2D(), getImages(), getColors(), detailPanelWidth, worldHeight);

    // TODO: maybe this stuff below can be done in a xml file?
    worldCanvas.setLayoutX(0);
    worldCanvas.setLayoutY(0);

    detailCanvas.setLayoutX(worldWidth);
    detailCanvas.setLayoutY(0);

    getChildren().add(worldCanvas);
    getChildren().add(detailCanvas);

    addButtons();
    addEventHandlers();
  }

  public void start() {

    new AnimationTimer() {
      public void handle(long currentNanoTime)
      {
        drawer.draw();
      }
    }.start();

    new AnimationTimer() {
      public void handle(long currentNanoTime)
      {
        detailDrawer.draw();
      }
    }.start();
  }

  public void shutDown() {
    world.shutDown();
  }

  private void setCurrentVehicle(Vehicle vehicle) {
    currentVehicle = vehicle;

    if (currentVehicle == null) {
      removeVehicleButton.setVisible(false);
    }
    else {
      removeVehicleButton.setVisible(true);
    }
  }

  private void addEventHandlers() {
    addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
      Vehicle vehicle = world.findVehicleAtCoordinates(e.getX(), e.getY(), clickErrorMargin);
      Stopover stopover = world.findStopoverAtCoordinates(e.getX(), e.getY(), clickErrorMargin);

      if (vehicle != null) {
        // not sure if this synchronized block is needed
        synchronized (detailDrawer) {
          setCurrentVehicle(vehicle);
          detailDrawer.setObject(vehicle);
          detailDrawer.notify();
        }
      }
      else if (stopover != null){
        synchronized (detailDrawer) {
          setCurrentVehicle(null);
          detailDrawer.setObject(stopover);
          detailDrawer.notify();
        }
      }
    });

    newCivilAirplaneButton.setOnAction(e -> world.addCivilAirplane());
    newCivilAirplaneButton.setOnMouseEntered(e -> getScene().setCursor(Cursor.HAND));
    newCivilAirplaneButton.setOnMouseExited(e -> getScene().setCursor(Cursor.DEFAULT));

    newCivilShipButton.setOnAction(e -> world.addCivilShip());
    newCivilShipButton.setOnMouseEntered(e -> getScene().setCursor(Cursor.HAND));
    newCivilShipButton.setOnMouseExited(e -> getScene().setCursor(Cursor.DEFAULT));

    newMilitaryShipButton.setOnAction(e -> world.addMilitaryShip());
    newMilitaryShipButton.setOnMouseEntered(e -> getScene().setCursor(Cursor.HAND));
    newMilitaryShipButton.setOnMouseExited(e -> getScene().setCursor(Cursor.DEFAULT));

    removeVehicleButton.setOnAction(e -> removeCurrentVehicle());
    removeVehicleButton.setOnMouseEntered(e -> getScene().setCursor(Cursor.HAND));
    removeVehicleButton.setOnMouseExited(e -> getScene().setCursor(Cursor.DEFAULT));

    worldCanvas.setOnMouseEntered(e -> getScene().setCursor(Cursor.CROSSHAIR));
    worldCanvas.setOnMouseExited(e -> getScene().setCursor(Cursor.DEFAULT));
    worldCanvas.setOnMouseMoved(e -> {
      drawer.setCursorX(e.getX());
      drawer.setCursorY(e.getY());
    });
  }

  private void removeCurrentVehicle() {
    if (currentVehicle != null) {
      detailDrawer.setObject(null);

      try {
        world.removeVehicle(currentVehicle);
      }
      catch (InterruptedException e) {
        e.printStackTrace();
      }

      setCurrentVehicle(null);
    }
  }

  private void addButtons() {
    // TODO: section out buttons for adding vehicles so that users do not think that those buttons are connected to the current location
    newCivilAirplaneButton = new Button();
    newCivilShipButton = new Button();
    newMilitaryShipButton = new Button();
    newMilitaryAirplaneButton = new Button();
    removeVehicleButton = new Button();

    newCivilAirplaneButton.setTooltip(new Tooltip("new civil airplane"));
    newCivilShipButton.setTooltip(new Tooltip("new civil ship"));
    newMilitaryShipButton.setTooltip(new Tooltip("new military ship"));
    newMilitaryAirplaneButton.setTooltip(new Tooltip("new military airplane"));
    removeVehicleButton.setTooltip(new Tooltip("remove vehicle"));


    newCivilAirplaneButton.setLayoutX(worldWidth + 120);
    newCivilAirplaneButton.setLayoutY(worldHeight - 40);

    newCivilShipButton.setLayoutX(worldWidth + 170);
    newCivilShipButton.setLayoutY(worldHeight - 40);

    newMilitaryShipButton.setLayoutX(worldWidth + 220);
    newMilitaryShipButton.setLayoutY(worldHeight - 40);

    removeVehicleButton.setLayoutX(worldWidth + detailPanelWidth - 50);
    removeVehicleButton.setLayoutY(27);

    newCivilAirplaneButton.getStyleClass().add("button");
    newCivilAirplaneButton.getStyleClass().add("add-airplane-button");
    newCivilAirplaneButton.getStyleClass().add("civil-airplane");

    newCivilShipButton.getStyleClass().add("button");
    newCivilShipButton.getStyleClass().add("add-ship-button");
    newCivilShipButton.getStyleClass().add("civil-ship");

    newMilitaryShipButton.getStyleClass().add("button");
    newMilitaryShipButton.getStyleClass().add("add-ship-button");
    newMilitaryShipButton.getStyleClass().add("military");

    newMilitaryAirplaneButton.getStyleClass().add("button");
    newMilitaryAirplaneButton.getStyleClass().add("add-airplane-button");
    newMilitaryAirplaneButton.getStyleClass().add("military");

    removeVehicleButton.getStyleClass().add("button");
    removeVehicleButton.getStyleClass().add("remove-vehicle-button");

    Group buttons = new Group();

    buttons.getChildren().add(newCivilAirplaneButton);
    buttons.getChildren().add(newCivilShipButton);
    buttons.getChildren().add(newMilitaryShipButton);
    buttons.getChildren().add(removeVehicleButton);
    removeVehicleButton.setVisible(false);

    getChildren().add(buttons);
  }

  private HashMap<String, Color> getColors() {
    HashMap<String, Color> colors = new HashMap<>();

    colors.put("civilNavy", Color.web("#0e3a5f"));
    colors.put("civilGreen", Color.web("#065525"));
    colors.put("military", Color.DARKOLIVEGREEN);
    colors.put("junctionBeige", Color.BEIGE);

    return colors;
  }

  private HashMap<String, Image> getImages() {
    HashMap<String, Image> images = new HashMap<>();

    images.put("airplane", new Image("/images/airplane.png"));
    images.put("ship", new Image("/images/ship.png"));
    images.put("paper", new Image("/images/paper.png"));
    images.put("terrain", new Image("/images/terrain.png"));

    return images;
  }
}
