package vehicles;

import core.Coordinates;
import core.IdGenerator;
import stopovers.Destination;
import stopovers.Stopover;
import world.Map;

public abstract class Vehicle implements Runnable {
  private Coordinates coordinates;
  private final int id;
  private Map map;

  public Vehicle(Coordinates coordinates) {
    this.coordinates = coordinates;
    this.id = IdGenerator.getId();
  }

  public void setMap(Map map) {
    this.map = map;
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
