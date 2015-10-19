package world;

import core.PassengerGenerator;
import core.PassengerZone;
import stopovers.Destination;
import vehicles.CivilVehicle;
import vehicles.Vehicle;

public class World {
  private WorldClock clock;
  private WorldMap map;

  public World(WorldClock clock, WorldMap map) {
    this.clock = clock;
    this.map = map;
  }

  public void registerVehicle(Vehicle vehicle, Destination destination) {
    map.registerVehicle(vehicle, destination.getCoordinates());
  }

  public void registerVehicle(CivilVehicle civilVehicle, Destination destination) {
    registerVehicle((Vehicle) civilVehicle, destination);
    fillWithPassengers(civilVehicle.passengerZone(), destination);
  }

  private void fillWithPassengers(PassengerZone zone, Destination hometown) {
    for (int i = 0; i < zone.getCapacity()/2; i++) {
      zone.accommodate(PassengerGenerator.randomPassenger(map, hometown));
    }
  }
}
