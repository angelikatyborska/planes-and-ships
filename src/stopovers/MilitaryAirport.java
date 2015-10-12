package stopovers;

import core.Coordinates;
import vehicles.MilitaryAirplane;
import vehicles.Vehicle;

public class MilitaryAirport extends Airport {
  public MilitaryAirport(Coordinates coordinates, int vehicleCapacity) {
    super(coordinates, vehicleCapacity);
  }

  @Override
  public boolean accommodateVehicle(Vehicle vehicle) throws InvalidVehicleAtDestinationException {
    if (vehicle instanceof MilitaryAirplane) {
      return super.accommodateVehicle(vehicle);
    }
    else {
      throw new InvalidVehicleAtDestinationException(vehicle, this);
    }
  }
}
