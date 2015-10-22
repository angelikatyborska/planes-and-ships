package stopovers;

import core.Coordinates;
import core.PassengerZone;
import vehicles.Airplane;
import vehicles.CivilAirplane;

public class CivilAirport extends Airport implements CivilDestination {
  private final PassengerZone passengerZone;

  public CivilAirport(Coordinates coordinates, int vehicleCapacity) {
    super(coordinates, vehicleCapacity);
    passengerZone = new PassengerZone(Integer.MAX_VALUE);
  }

  public boolean accommodateVehicle(Airplane airplane) throws InvalidVehicleAtStopoverException {
    throw new InvalidVehicleAtStopoverException(airplane, this);
  }

  public boolean accommodateVehicle(CivilAirplane civilAirplane) throws InvalidVehicleAtStopoverException {
    return super.accommodateVehicle((Airplane) civilAirplane);
  }

  public void prepareVehicleForTravel(CivilAirplane vehicle) throws InterruptedException {
    super.prepareVehicleForTravel((Airplane) vehicle);
    vehicle.passengerZone().moveAllTo(passengerZone);
    Thread.sleep(1000);
    passengerZone.moveAllWithMatchingDestinationTo(vehicle.passengerZone(), vehicle.getNextCivilDestination());
  }

  @Override
  public PassengerZone passengerZone() {
    return passengerZone;
  }
}
