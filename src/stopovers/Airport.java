package stopovers;

import core.Coordinates;
import vehicles.Airplane;
import vehicles.Vehicle;

public abstract class Airport extends Destination {
  public Airport(Coordinates coordinates, int vehicleCapacity) {
    super(coordinates, vehicleCapacity);
  }

  @Override
  public boolean accommodateVehicle(Vehicle vehicle) throws InvalidVehicleAtStopoverException {
    if (vehicle instanceof Airplane) {
      return super.accommodateVehicle(vehicle);
    }
    else {
      throw new InvalidVehicleAtStopoverException(vehicle, this);
    }
  }
}
