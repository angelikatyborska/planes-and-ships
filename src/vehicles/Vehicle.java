package vehicles;

import core.Coordinates;
import core.IdGenerator;
import stopovers.Destination;
import stopovers.Stopover;
import world.WorldMap;

public abstract class Vehicle implements Runnable {
  private final int id;
  private final double velocity;
  private WorldMap worldMap;

  public Vehicle(double velocity) {
    this.id = IdGenerator.getId();
    this.velocity = velocity;
  }

  public double getVelocity() {
    return velocity;
  }

  public void setWorldMap(WorldMap worldMap) {
    this.worldMap = worldMap;
  }

  public Coordinates getCoordinates() {
    return worldMap.getVehicleCoordinates(this);
  }

  public Stopover getNextStopover() {
    return null;
  }

  public Stopover getPreviousStopover() {
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
