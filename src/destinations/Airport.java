package destinations;

import core.Coordinates;
import vehicles.Vehicle;

public class Airport extends Destination {
  public Airport(Coordinates coordinates, int vehicleCapacity) {
    super(coordinates, vehicleCapacity);
  }

  public boolean accommodateVehicle(Vehicle airplane) {
    // if vehicle is an airplane
    return super.accommodateVehicle(airplane);
  }
}
