package gui.buttons;

import javafx.fxml.FXML;
import javafx.scene.Group;
import world.World;

import java.io.*;
import java.util.function.Consumer;

/**
 * A group of buttons that can add various types of vehicles that do not require being added by another vehicle.
 */
public class WorldControlButtons extends Group {
  private World world;
  private Consumer<World> loadWorldCallback;

  public WorldControlButtons() {}

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
    FileOutputStream fileOut =
      new FileOutputStream("/tmp/world.ser");
    ObjectOutputStream out = new ObjectOutputStream(fileOut);
    out.writeObject(world);
    out.close();
    fileOut.close();
    System.out.printf("Serialized data is saved in /tmp/world.ser");
  }

  @FXML
  private void loadWorld() {
    try {
      FileInputStream fileIn = new FileInputStream("/tmp/world.ser");
      ObjectInputStream in = new ObjectInputStream(fileIn);
      world = (World) in.readObject();

      loadWorldCallback.accept(world);

      System.out.printf("Serialized data loaded form in /tmp/world.ser");

      in.close();
      fileIn.close();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
}
