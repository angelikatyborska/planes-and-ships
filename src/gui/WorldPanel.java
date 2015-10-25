package gui;

import core.Coordinates;
import javafx.animation.AnimationTimer;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
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
  private World world;
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
    newCivilAirplaneButton = new Button();
    newCivilShipButton = new Button();
    newMilitaryShipButton = new Button();
    newMilitaryAirplaneButton = new Button();

    buildWorld();

    HashMap<String, Image> images = new HashMap<>();

    images.put("airplane", new Image("/images/airplane.png"));
    images.put("ship", new Image("/images/ship.png"));
    images.put("paper", new Image("/images/paper.png"));
    images.put("terrain", new Image("/images/terrain.png"));

    HashMap<String, Color> colors = new HashMap<>();

    colors.put("civilNavy", Color.web("#0e3a5f"));
    colors.put("civilGreen", Color.web("#065525"));
    colors.put("military", Color.DARKOLIVEGREEN);

    drawer = new WorldDrawer(map, worldCanvas.getGraphicsContext2D(), images, colors,  worldWidth, worldHeight);
    detailDrawer = new ObjectDetailDrawer(detailCanvas.getGraphicsContext2D(), images, colors, detailPanelWidth, worldHeight);

    addEventHandlers();

    // TODO: maybe this stuff below can be done in a xml file?
    worldCanvas.setLayoutX(0);
    worldCanvas.setLayoutY(0);

    detailCanvas.setLayoutX(worldWidth);
    detailCanvas.setLayoutY(0);

    newCivilAirplaneButton.setLayoutX(worldWidth + 120);
    newCivilAirplaneButton.setLayoutY(worldHeight - 40);

    newCivilShipButton.setLayoutX(worldWidth + 170);
    newCivilShipButton.setLayoutY(worldHeight - 40);

    newMilitaryShipButton.setLayoutX(worldWidth + 220);
    newMilitaryShipButton.setLayoutY(worldHeight - 40);

    newCivilAirplaneButton.getStyleClass().add("add-vehicle-button");
    newCivilAirplaneButton.getStyleClass().add("add-airplane-button");
    newCivilAirplaneButton.getStyleClass().add("civil-airplane");

    newCivilShipButton.getStyleClass().add("add-vehicle-button");
    newCivilShipButton.getStyleClass().add("add-ship-button");
    newCivilShipButton.getStyleClass().add("civil-ship");

    newMilitaryShipButton.getStyleClass().add("add-vehicle-button");
    newMilitaryShipButton.getStyleClass().add("add-ship-button");
    newMilitaryShipButton.getStyleClass().add("military");

    newMilitaryAirplaneButton.getStyleClass().add("add-vehicle-button");
    newMilitaryAirplaneButton.getStyleClass().add("add-airplane-button");
    newMilitaryAirplaneButton.getStyleClass().add("military");

    getChildren().add(worldCanvas);
    getChildren().add(detailCanvas);
    getChildren().add(newCivilAirplaneButton);
    getChildren().add(newCivilShipButton);
    getChildren().add(newMilitaryShipButton);
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

  private void addEventHandlers() {
    addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
      Vehicle vehicle = world.findVehicleAtCoordinates(e.getX(), e.getY(), clickErrorMargin);
      Stopover stopover = world.findStopoverAtCoordinates(e.getX(), e.getY(), clickErrorMargin);

      if (vehicle != null) {
        // not sure if this synchronized block is needed
        synchronized (detailDrawer) {
          detailDrawer.setObject(vehicle);
          detailDrawer.notify();
        }
      }
      else if (stopover != null){
        synchronized (detailDrawer) {
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

    worldCanvas.setOnMouseEntered(e -> getScene().setCursor(Cursor.CROSSHAIR));
    worldCanvas.setOnMouseExited(e -> getScene().setCursor(Cursor.DEFAULT));
    worldCanvas.setOnMouseMoved(e -> {
      drawer.setCursorX(e.getX());
      drawer.setCursorY(e.getY());
    });
  }

  private void buildWorld() {
    try {
      StopoverNetwork network = new StopoverNetwork();

      CivilAirport civilAirport1 = new CivilAirport("Inkville", new Coordinates(450, 40), 1);
      CivilAirport civilAirport2 = new CivilAirport("Paper Town", new Coordinates(60, 45), 1);
      CivilAirport civilAirport3 = new CivilAirport("Penborg", new Coordinates(170, 200), 1);
      CivilAirport civilAirport4 = new CivilAirport("New Folder", new Coordinates(620, 230), 1);
      CivilAirport civilAirport5 = new CivilAirport("Office City", new Coordinates(490, 450), 1);
      CivilAirport civilAirport6 = new CivilAirport("Paperville", new Coordinates(730, 570), 1);
      CivilAirport civilAirport7 = new CivilAirport("Scribbleton", new Coordinates(230, 600), 1);

      network.add(civilAirport1);
      network.add(civilAirport2);
      network.add(civilAirport3);
      network.add(civilAirport4);
      network.add(civilAirport5);
      network.add(civilAirport6);
      network.add(civilAirport7);

      Junction airJunction1 = new Junction(new Coordinates(230, 100));
      Junction airJunction2 = new Junction(new Coordinates(620, 110));
      Junction airJunction3 = new Junction(new Coordinates(460, 180));
      Junction airJunction4 = new Junction(new Coordinates(720, 220));
      Junction airJunction5 = new Junction(new Coordinates(550, 330));
      Junction airJunction6 = new Junction(new Coordinates(650, 470));
      Junction airJunction7 = new Junction(new Coordinates(400, 550));

      network.add(airJunction1);
      network.add(airJunction2);
      network.add(airJunction3);
      network.add(airJunction4);
      network.add(airJunction5);
      network.add(airJunction6);
      network.add(airJunction7);

      network.connect(airJunction1, airJunction3);
      network.connect(airJunction2, airJunction3);
      network.connect(airJunction2, airJunction4);
      network.connect(airJunction3, airJunction5);
      network.connect(airJunction5, airJunction6);
      network.connect(airJunction6, airJunction7);


      network.connect(airJunction1, civilAirport1);

      network.connect(airJunction1, civilAirport2);
      network.connect(airJunction1, civilAirport3);

      network.connect(airJunction2, civilAirport1);
      network.connect(airJunction2, civilAirport4);

      network.connect(airJunction3, civilAirport1);
      network.connect(airJunction3, civilAirport4);

      network.connect(airJunction4, civilAirport4);

      network.connect(airJunction5, civilAirport4);
      network.connect(airJunction5, civilAirport5);

      network.connect(airJunction6, civilAirport5);
      network.connect(airJunction6, civilAirport6);

      network.connect(airJunction7, civilAirport5);
      network.connect(airJunction7, civilAirport7);

      MilitaryAirport militaryAirport1 = new MilitaryAirport("TOP SECRET", new Coordinates(760, 40), 1);
      MilitaryAirport militaryAirport2 = new MilitaryAirport("TOP SECRET", new Coordinates(340, 260), 1);
      MilitaryAirport militaryAirport3 = new MilitaryAirport("TOP SECRET", new Coordinates(770, 380), 1);
      MilitaryAirport militaryAirport4 = new MilitaryAirport("TOP SECRET", new Coordinates(500, 610), 1);

      network.add(militaryAirport1);
      network.add(militaryAirport2);
      network.add(militaryAirport3);
      network.add(militaryAirport4);

      network.connect(militaryAirport1, airJunction2);
      network.connect(militaryAirport1, airJunction4);

      network.connect(militaryAirport2, airJunction1);
      network.connect(militaryAirport2, airJunction3);
      network.connect(militaryAirport2, airJunction5);

      network.connect(militaryAirport3, airJunction4);
      network.connect(militaryAirport3, airJunction5);
      network.connect(militaryAirport3, airJunction6);

      network.connect(militaryAirport4, airJunction6);
      network.connect(militaryAirport4, airJunction7);

      Port port1 = new Port("Lettergrad", new Coordinates(50, 230), 1);
      Port port2 = new Port("New Notebook", new Coordinates(230, 280), 1);
      Port port3 = new Port("Inkton", new Coordinates(430, 350), 1);
      Port port4 = new Port("Stylos", new Coordinates(390, 490), 1);
      Port port5 = new Port("Tryckfärg", new Coordinates(230, 530), 1);
      Port port6 = new Port("Kartkosławów", new Coordinates(125, 575), 1);

      network.add(port1);
      network.add(port2);
      network.add(port3);
      network.add(port4);
      network.add(port5);
      network.add(port6);

      Junction seaJunction1 = new Junction(new Coordinates(120, 330));
      Junction seaJunction2 = new Junction(new Coordinates(320, 370));
      Junction seaJunction3 = new Junction(new Coordinates(170, 430));
      Junction seaJunction4 = new Junction(new Coordinates(70, 470));
      Junction seaJunction5 = new Junction(new Coordinates(300, 480));
      Junction seaJunction6 = new Junction(new Coordinates(20, 340));

      network.add(seaJunction1);
      network.add(seaJunction2);
      network.add(seaJunction3);
      network.add(seaJunction4);
      network.add(seaJunction5);
      network.add(seaJunction6);

      network.connect(seaJunction1, seaJunction2);
      network.connect(seaJunction1, seaJunction3);
      network.connect(seaJunction1, seaJunction4);
      network.connect(seaJunction1, seaJunction6);

      network.connect(seaJunction2, seaJunction3);
      network.connect(seaJunction2, seaJunction5);

      network.connect(seaJunction3, seaJunction4);

      network.connect(seaJunction4, seaJunction6);

      network.connect(seaJunction1, port1);
      network.connect(seaJunction1, port2);

      network.connect(seaJunction2, port2);
      network.connect(seaJunction2, port3);

      network.connect(seaJunction3, port5);

      network.connect(seaJunction4, port5);
      network.connect(seaJunction4, port6);

      network.connect(seaJunction5, port4);
      network.connect(seaJunction5, port5);

      network.connect(seaJunction6, port1);

      network.connect(port1, civilAirport3);
      network.connect(port2, civilAirport3);
      network.connect(port3, civilAirport5);
      network.connect(port4, civilAirport5);
      network.connect(port5, civilAirport7);
      network.connect(port6, civilAirport7);


      map = new WorldMap(network);
      world = new World(new WorldClock(50), map);
    }
    catch (StopoverNotFoundInStopoverNetworkException e) {
      e.printStackTrace();
    }
  }
}
