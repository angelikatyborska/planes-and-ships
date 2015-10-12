package stopovers;

import core.Coordinates;
import core.PassengerZone;
import vehicles.CivilShip;
import vehicles.Vehicle;

public class Port extends Destination {
  public final PassengerZone passengerZone;

  public Port(Coordinates coordinates, int vehicleCapacity) {
    super(coordinates, vehicleCapacity);
    passengerZone = new PassengerZone(Integer.MAX_VALUE);
  }

  @Override
  public boolean accommodateVehicle(Vehicle vehicle) throws InvalidVehicleAtStopoverException {
    if (vehicle instanceof CivilShip) {
      return super.accommodateVehicle(vehicle);
    }
    else {
      throw new InvalidVehicleAtStopoverException(vehicle, this);
    }
  }
}
