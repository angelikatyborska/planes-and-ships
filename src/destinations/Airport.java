package destinations;

import core.Coordinates;
import vehicles.Airplane;
import vehicles.Vehicle;

public abstract class Airport extends Stopover {
  public Airport(Coordinates coordinates, int vehicleCapacity) {
    super(coordinates, vehicleCapacity);
  }

  @Override
  public boolean accommodateVehicle(Vehicle vehicle) throws InvalidVehicleAtDestinationException {
    if (vehicle instanceof Airplane) {
      return super.accommodateVehicle(vehicle);
    }
    else {
      throw new InvalidVehicleAtDestinationException(vehicle, this);
    }
  }
}
