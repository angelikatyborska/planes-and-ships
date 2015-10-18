package world;

import core.PassengerGenerator;
import core.PassengerZone;
import stopovers.Destination;
import vehicles.CivilAirplane;
import vehicles.CivilShip;
import vehicles.Vehicle;

public class World {
  private WorldClock clock;
  private WorldMap map;

  public World(WorldClock clock, WorldMap map) {
    this.clock = clock;
    this.map = map;
  }

  // TODO: DRY this code
  public void registerVehicle(Vehicle vehicle, Destination destination) {
    map.registerVehicle(vehicle, destination.getCoordinates());
  }

  public void registerVehicle(CivilAirplane civilAirplane, Destination destination) {
    registerVehicle((Vehicle) civilAirplane, destination);
    // TODO: rethink passenger and vehicle generation
    fillWithPassengers(civilAirplane.passengerZone, destination);
  }

  public void registerVehicle(CivilShip civilShip, Destination destination) {
    registerVehicle((Vehicle) civilShip, destination);
    fillWithPassengers(civilShip.passengerZone, destination);
  }

  private void fillWithPassengers(PassengerZone zone, Destination hometown) {
    for (int i = 0; i < zone.getCapacity()/2; i++) {
      zone.accommodate(PassengerGenerator.randomPassenger(map, hometown));
    }
  }
}
