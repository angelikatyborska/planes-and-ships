package destinations;

import core.Coordinates;
import core.PassengerZone;
import vehicles.CivilAirplane;
import vehicles.Vehicle;

public class CivilAirport extends Airport {
  public final PassengerZone passengerZone;

  public CivilAirport(Coordinates coordinates, int vehicleCapacity) {
    super(coordinates, vehicleCapacity);
    passengerZone = new PassengerZone(Integer.MAX_VALUE);
  }

  @Override
  public boolean accommodateVehicle(Vehicle vehicle) throws InvalidVehicleAtDestinationException {
    if (vehicle instanceof CivilAirplane) {
      return super.accommodateVehicle(vehicle);
    }
    else {
      throw new InvalidVehicleAtDestinationException(vehicle, this);
    }
  }
}
