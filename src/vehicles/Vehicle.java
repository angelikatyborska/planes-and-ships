package vehicles;

import core.Coordinates;
import core.IdGenerator;
import stopovers.Destination;
import stopovers.Stopover;
import world.WorldMap;

public abstract class Vehicle implements Runnable {
  private final int id;
  private WorldMap worldMap;

  public Vehicle() {
    this.id = IdGenerator.getId();
  }

  public void setWorldMap(WorldMap worldMap) {
    this.worldMap = worldMap;
  }

  public Coordinates getCoordinates() {
    return null;
  }

  public Stopover getNextStopover() {
    return null;
  }

  public Destination getNextDestination() {
    return null;
  }

  public abstract void gotAccommodatedAt(Stopover stopover);

  public abstract void gotReleasedFrom(Stopover stopover);

  @Override
  public void run() {

  }
}
