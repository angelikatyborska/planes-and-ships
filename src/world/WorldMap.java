package world;

import core.Coordinates;
import vehicles.Vehicle;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class WorldMap {
  private final StopoverNetwork stopoverNetwork;
  private final Map<Vehicle, Coordinates> vehicles;

  public WorldMap(StopoverNetwork stopoverNetwork) {
    this.stopoverNetwork = stopoverNetwork;
    this.vehicles = new HashMap<>();
  }

  public void registerVehicle(Vehicle vehicle, Coordinates coordinates) {
    vehicle.setWorldMap(this);
    vehicles.put(vehicle, coordinates);
  }

  public Coordinates getVehicleCoordinates(Vehicle vehicle) {
    return vehicles.get(vehicle);
  }
}
