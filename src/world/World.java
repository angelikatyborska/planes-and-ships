package world;

import stopovers.Destination;
import vehicles.Vehicle;

public class World {
  private WorldClock clock;
  private WorldMap map;

  public World(WorldClock clock, WorldMap map) {
    this.clock = clock;
    this.map = map;
  }

  public void addVehicle(Destination destination, Vehicle vehicle) {

  }
}
