package gui.worldpanel;

import gui.WorldDrawer;
import gui.buttons.VehicleCreationButtons;
import gui.objectdetails.ObjectDetails;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import stopovers.Stopover;
import vehicles.Vehicle;
import world.World;
import world.WorldBuilder;

import java.io.IOException;
import java.util.HashMap;

public class WorldPanel {
  private World world;
  private WorldDrawer drawer;
  private ObjectDetails objectDetailsController;
  @FXML private Canvas worldCanvas;
  @FXML Group objectDetails;
  @FXML Group vehicleCreationButtons;

  public WorldPanel() {
  }

  public void initialize() {
    world = WorldBuilder.build();
    drawer = new WorldDrawer(world, worldCanvas.getGraphicsContext2D(), getImages(), getColors(), 800, 640);
    start();

    FXMLLoader loader1 = new FXMLLoader();
    loader1.setLocation(VehicleCreationButtons.class.getResource("vehicle-creation-buttons.fxml"));
    try {
      vehicleCreationButtons.getChildren().add(loader1.load());
    } catch (IOException e) {
      e.printStackTrace();
    }
    VehicleCreationButtons vehicleCreationButtonsController = loader1.getController();
    vehicleCreationButtonsController.setWorld(world);


    FXMLLoader loader2 = new FXMLLoader();
    loader2.setLocation(ObjectDetails.class.getResource("object-details.fxml"));
    try {
      objectDetails.getChildren().add(loader2.load());
    } catch (IOException e) {
      e.printStackTrace();
    }
    objectDetailsController = loader2.getController();
    objectDetailsController.setObject(null);

    worldCanvas.setOnMouseClicked(e -> worldCanvasClicked(e));
  }

  public void start() {
    new AnimationTimer() {
      public void handle(long currentNanoTime)
      {
        drawer.draw();
        objectDetailsController.refresh();
      }
    }.start();
  }

  public void shutDown() {
    world.shutDown();
  }

  public void worldCanvasClicked(MouseEvent e) {
    double clickErrorMargin = 15;

    Vehicle vehicle = world.findVehicleAtCoordinates(e.getX(), e.getY(), clickErrorMargin);
    Stopover stopover = world.findStopoverAtCoordinates(e.getX(), e.getY(), clickErrorMargin);

    if (vehicle != null) {
      objectDetailsController.setObject(vehicle);
    }
    else if (stopover != null){
      objectDetailsController.setObject(stopover);
    }

    objectDetailsController.refresh();
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
