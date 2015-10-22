package stopovers;

import core.Coordinates;
import core.PassengerZone;
import vehicles.CivilShip;
import vehicles.Ship;
import vehicles.Vehicle;

public class Port extends Stopover implements CivilDestination {
  private final PassengerZone passengerZone;

  public Port(Coordinates coordinates, int vehicleCapacity) {
    super(coordinates, vehicleCapacity);
    passengerZone = new PassengerZone(Integer.MAX_VALUE);
  }

  public boolean accommodateVehicle(Vehicle vehicle) throws InvalidVehicleAtStopoverException {
    throw new InvalidVehicleAtStopoverException(vehicle, this);
  }

  public boolean accommodateVehicle(CivilShip civilShip) throws InvalidVehicleAtStopoverException {
    return super.accommodateVehicle((Vehicle) civilShip);
  }

  public void vehicleMaintenance(CivilShip vehicle) throws InterruptedException {
    super.vehicleMaintenance(vehicle);
    vehicle.passengerZone().moveAllTo(passengerZone);
    Thread.sleep(1000);
    passengerZone.moveAllWithMatchingDestinationTo(vehicle.passengerZone(), vehicle.getNextCivilDestination());
  }

  @Override
  public PassengerZone passengerZone() {
    return passengerZone;
  }
}
