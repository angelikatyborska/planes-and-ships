package stopovers;

import core.Coordinates;
import vehicles.Airplane;
import vehicles.Vehicle;

public abstract class Airport extends Destination {
  public Airport(Coordinates coordinates, int vehicleCapacity) {
    super(coordinates, vehicleCapacity);
  }

  public boolean accommodateVehicle(Vehicle vehicle) throws InvalidVehicleAtStopoverException {
    throw new InvalidVehicleAtStopoverException(vehicle, this);
  }

  public boolean accommodateVehicle(Airplane airplane) throws InvalidVehicleAtStopoverException {
    return super.accommodateVehicle(airplane);
  }
}
