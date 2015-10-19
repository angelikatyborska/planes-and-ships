package vehicles;

import core.PassengerZone;
import stopovers.CivilAirport;
import stopovers.Stopover;

public class CivilAirplane extends Airplane implements CivilVehicle {
  private final PassengerZone passengerZone;

  public CivilAirplane(double velocity, int fuelCapacity, int passengerCapacity) {
    super(velocity, fuelCapacity);
    passengerZone = new PassengerZone(passengerCapacity);
  }

  public void gotAccommodatedAt(Stopover stopover) {
    if (stopover instanceof CivilAirport) {
      passengerZone.moveAllTo(((CivilAirport) stopover).passengerZone());
    }
  }

  public void gotReleasedFrom(Stopover stopover) {
    if (stopover instanceof CivilAirport) {
      ((CivilAirport) stopover).passengerZone().moveAllWithMatchingDestinationTo(passengerZone, getNextCivilDestination());
    }
  }

  @Override
  public PassengerZone passengerZone() {
    return passengerZone;
  }
}
