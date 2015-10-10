package destinations;

import core.Coordinates;
import vehicles.Airplane;

public class Airport extends Destination {
  public Airport(Coordinates coordinates, int vehicleCapacity) {
    super(coordinates, vehicleCapacity);
  }

  public boolean accommodateVehicle(Airplane airplane) {
    boolean accommodatingSuccessful;

    accommodatingSuccessful = super.accommodateVehicle(airplane);
    airplane.refillFuel();

    return accommodatingSuccessful;
  }
}
