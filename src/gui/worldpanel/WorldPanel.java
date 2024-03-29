package gui.worldpanel;

import gui.canvas.WorldDrawer;
import gui.buttons.VehicleControlButtons;
import gui.buttons.WorldControlButtons;
import gui.objectdetails.ObjectDetails;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import stopovers.Stopover;
import vehicles.Vehicle;
import world.World;
import world.WorldBuilder;

import java.io.IOException;
import java.util.HashMap;

/**
 * Binds together all GUI items needed to print and control the world.
 */
public class WorldPanel {
  private World world;
  private WorldDrawer drawer;
  private ObjectDetails objectDetailsController;
  private VehicleControlButtons vehicleControlButtonsController;
  private WorldControlButtons worldControlButtonsController;
  @FXML private Canvas worldCanvas;
  @FXML private VBox objectDetails;
  @FXML private VBox worldControlButtons;
  @FXML private VBox vehicleControlButtons;

  public WorldPanel() {
  }

  public void initialize() {
    initializeAll(WorldBuilder.build());
  }

  public void initializeAll(World world) {
    this.world = world;

    drawer = new WorldDrawer(world, worldCanvas.getGraphicsContext2D(), getImages(), getColors(), 800, 640);
    start();

    FXMLLoader loader1 = new FXMLLoader();
    loader1.setLocation(WorldControlButtons.class.getResource("world-control-buttons.fxml"));

    try {
      worldControlButtons.getChildren().clear();
      worldControlButtons.getChildren().add(loader1.load());
    } catch (IOException e) {
      e.printStackTrace();
    }
    worldControlButtonsController = loader1.getController();
    worldControlButtonsController.setWorld(world);


    FXMLLoader loader2 = new FXMLLoader();
    loader2.setLocation(ObjectDetails.class.getResource("object-details.fxml"));

    try {
      objectDetails.getChildren().clear();
      objectDetails.getChildren().add(loader2.load());
    } catch (IOException e) {
      e.printStackTrace();
    }
    objectDetailsController = loader2.getController();
    objectDetailsController.setObject(null);


    FXMLLoader loader3 = new FXMLLoader();
    loader3.setLocation(VehicleControlButtons.class.getResource("vehicle-control-buttons.fxml"));
    try {
      vehicleControlButtons.getChildren().clear();
      vehicleControlButtons.getChildren().add(loader3.load());
    } catch (IOException e) {
      e.printStackTrace();
    }
    vehicleControlButtonsController = loader3.getController();
    vehicleControlButtonsController.setWorld(world);
    vehicleControlButtonsController.setVehicle(null);
    vehicleControlButtonsController.setActionBeforeRemovingVehicle(v -> objectDetailsController.setObject(null));

    worldCanvas.setOnMouseClicked(this::worldCanvasClicked);
    worldControlButtonsController.setActionAfterLoadingWorld(newWorld -> {
      world.shutDown();
      initializeAll(newWorld);
    });
  }

  /**
   * Start animating the world canvas.
   */
  public void start() {
    new AnimationTimer() {
      public void handle(long currentNanoTime)
      {
        drawer.draw();
        objectDetailsController.refresh();
      }
    }.start();
  }

  /**
   * Stop all the threads inside the World (passengers and vehicles threads)
   */
  public void shutDown() {
    world.shutDown();
  }

  private void worldCanvasClicked(MouseEvent e) {
    double clickErrorMargin = 15;

    Vehicle vehicle = world.findVehicleAtCoordinates(e.getX(), e.getY(), clickErrorMargin);
    Stopover stopover = world.findStopoverAtCoordinates(e.getX(), e.getY(), clickErrorMargin);

    if (vehicle != null) {
      objectDetailsController.setObject(vehicle);
      vehicleControlButtonsController.setVehicle(vehicle);
      drawer.setVehicle(vehicle);
    }
    else if (stopover != null){
      drawer.setVehicle(null);
      objectDetailsController.setObject(stopover);
      vehicleControlButtonsController.setVehicle(null);
    }

    objectDetailsController.refresh();
  }

  private HashMap<String, Color> getColors() {
    HashMap<String, Color> colors = new HashMap<>();

    colors.put("civilNavy", Color.web("#00549b"));
    colors.put("civilGreen", Color.web("#007928"));
    colors.put("military", Color.web("#5e6f38"));
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
