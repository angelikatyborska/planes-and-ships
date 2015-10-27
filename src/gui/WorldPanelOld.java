package gui;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import stopovers.*;
import vehicles.CivilVehicle;
import vehicles.Vehicle;
import world.*;

import java.util.HashMap;

public class WorldPanelOld extends Group {
  private Canvas worldCanvas;
  private Canvas detailCanvas;
  private VehicleButtons vehicleButtons;
  private PassengerList passengerList;
  private PassengerList passengerListHotel;
  private Label passengerListLabel;
  private Label passengerListHotelLabel;
  private World world;
  private WorldDrawer drawer;
  private ObjectDetailDrawer detailDrawer;
  private final double clickErrorMargin = 15;
  private final double worldWidth;
  private final double worldHeight;
  private final double detailPanelWidth;


  public WorldPanelOld() {
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

    vehicleButtons = new VehicleButtons(world, worldWidth, worldHeight, worldWidth + detailPanelWidth);
    vehicleButtons.setActionBeforeRemovingVehicle(v -> {
      detailDrawer.setObject(null);
      passengerList.setPassengerZone(null);
      passengerListHotel.setPassengerZone(null);
    });

    passengerListLabel = new Label();
    passengerListHotelLabel = new Label("Sleeping at the hotel:");

    passengerListLabel.setFont(Font.font("Courier", 15));
    passengerListHotelLabel.setFont(Font.font("Courier", 15));

    passengerListLabel.setLayoutX(worldWidth + 15);
    passengerListLabel.setLayoutY(247);
    passengerListHotelLabel.setLayoutX(worldWidth + 15);
    passengerListHotelLabel.setLayoutY(387);
    passengerList = new PassengerList(detailPanelWidth - 52, worldWidth + 15, 263);
    passengerListHotel = new PassengerList(detailPanelWidth - 52, worldWidth + 15, 406);

    getChildren().add(worldCanvas);
    getChildren().add(detailCanvas);
    getChildren().add(vehicleButtons);
    getChildren().add(passengerList);
    getChildren().add(passengerListHotel);
    getChildren().add(passengerListLabel);
    getChildren().add(passengerListHotelLabel);
    passengerListLabel.setVisible(false);
    passengerListHotelLabel.setVisible(false);

    addEventHandlers();
  }

  public void start() {

    new AnimationTimer() {
      public void handle(long currentNanoTime)
      {
        drawer.draw();
        detailDrawer.draw();
        passengerList.refresh();
        passengerListHotel.refresh();
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
          vehicleButtons.setVehicle(vehicle);
          detailDrawer.setObject(vehicle);
          detailDrawer.notify();
          passengerListHotel.setPassengerZone(null);
          passengerListHotelLabel.setVisible(false);

          if (vehicle instanceof CivilVehicle) {
            passengerList.setPassengerZone(((CivilVehicle) vehicle).passengerZone());
            passengerListLabel.setVisible(true);
            passengerListLabel.setText("On board:");
          }
          else {
            passengerList.setPassengerZone(null);
            passengerListLabel.setVisible(false);
          }
        }
      }
      else if (stopover != null){
        synchronized (detailDrawer) {
          vehicleButtons.setVehicle(null);
          detailDrawer.setObject(stopover);
          detailDrawer.notify();
          detailDrawer.notify();


          if (stopover instanceof CivilDestination) {
            passengerList.setPassengerZone(((CivilDestination) stopover).passengerZone());
            passengerListHotel.setPassengerZone(((CivilDestination) stopover).hotel());
            passengerListLabel.setVisible(true);
            passengerListLabel.setText("Waiting for departure:");
            passengerListHotelLabel.setVisible(true);
          }
          else {
            passengerList.setPassengerZone(null);
            passengerListHotel.setPassengerZone(null);
            passengerListLabel.setVisible(false);
            passengerListHotelLabel.setVisible(false);
          }
        }
      }
    });

    worldCanvas.getStyleClass().add("world-canvas");
    worldCanvas.setOnMouseMoved(e -> {
      drawer.setCursorX(e.getX());
      drawer.setCursorY(e.getY());
    });
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
