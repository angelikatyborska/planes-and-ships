package gui.buttons;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import world.World;

import java.io.*;
import java.util.function.Consumer;

/**
 * A group of buttons that can add various types of vehicles that do not require being added by another vehicle.
 */
public class WorldControlButtons extends Group {
  private World world;
  private Consumer<World> loadWorldCallback;
  private final FileChooser fileChooser = new FileChooser();


  public WorldControlButtons() {
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Java Serialized Object (.ser)", "*.ser"));
  }

  /**
   *
   * @param world the World object to which vehicles will be added.
   */
  public void setWorld(World world) {
    this.world = world;
  }

  public void setActionAfterLoadingWorld(Consumer<World> action) {
    loadWorldCallback = action;
  }

  @FXML
  private void addCivilAirplane() {
    world.addCivilAirplane();
  }

  @FXML
  private void addCivilShip() {
    world.addCivilShip();
  }

  @FXML
  private void addMilitaryShip() {
    world.addMilitaryShip();
  }

  @FXML
  private void saveWorld() throws IOException {
    Stage stage = new Stage();
    File file = fileChooser.showSaveDialog(stage);

    if (file != null) {
      FileOutputStream fileOut = new FileOutputStream(file);
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(world);
      out.close();
      fileOut.close();
gi    }
  }

  @FXML
  private void loadWorld() {
    try {
      Stage stage = new Stage();
      File file = fileChooser.showOpenDialog(stage);

      if (file != null) {
        FileInputStream fileIn = new FileInputStream(file);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        world = (World) in.readObject();

        loadWorldCallback.accept(world);

        in.close();
        fileIn.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
}
