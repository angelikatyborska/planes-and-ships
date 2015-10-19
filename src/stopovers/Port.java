package stopovers;

import core.Coordinates;
import core.PassengerZone;
import vehicles.CivilShip;
import vehicles.Vehicle;

public class Port extends Destination implements CivilDestination {
  private final PassengerZone passengerZone;

  public Port(Coordinates coordinates, int vehicleCapacity) {
    super(coordinates, vehicleCapacity);
    passengerZone = new PassengerZone(Integer.MAX_VALUE);
  }

  public boolean accommodateVehicle(Vehicle vehicle) throws InvalidVehicleAtStopoverException {
    throw new InvalidVehicleAtStopoverException(vehicle, this);
  }

  public boolean accommodateVehicle(CivilShip civilShip) throws InvalidVehicleAtStopoverException {
    return super.accommodateVehicle(civilShip);
  }

  @Override
  public PassengerZone passengerZone() {
    return passengerZone;
  }
}
