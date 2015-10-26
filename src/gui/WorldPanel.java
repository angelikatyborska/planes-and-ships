package gui;

import javafx.animation.AnimationTimer;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
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
  private VehicleControlButtons vehicleControlButtons;
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

    vehicleControlButtons = new VehicleControlButtons(world, worldWidth, worldHeight, worldWidth + detailPanelWidth);
    vehicleControlButtons.setActionBeforeRemovingVehicle(v -> detailDrawer.setObject(null));

    getChildren().add(worldCanvas);
    getChildren().add(detailCanvas);
    getChildren().add(vehicleControlButtons);

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

  private void addEventHandlers() {
    addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
      Vehicle vehicle = world.findVehicleAtCoordinates(e.getX(), e.getY(), clickErrorMargin);
      Stopover stopover = world.findStopoverAtCoordinates(e.getX(), e.getY(), clickErrorMargin);

      if (vehicle != null) {
        // not sure if this synchronized block is needed
        synchronized (detailDrawer) {
          vehicleControlButtons.setCurrentVehicle(vehicle);
          detailDrawer.setObject(vehicle);
          detailDrawer.notify();
        }
      }
      else if (stopover != null){
        synchronized (detailDrawer) {
          vehicleControlButtons.setCurrentVehicle(null);
          detailDrawer.setObject(stopover);
          detailDrawer.notify();
        }
      }
    });

    worldCanvas.setOnMouseEntered(e -> getScene().setCursor(Cursor.CROSSHAIR));
    worldCanvas.setOnMouseExited(e -> getScene().setCursor(Cursor.DEFAULT));
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
