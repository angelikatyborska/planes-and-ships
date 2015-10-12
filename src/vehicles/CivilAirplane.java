package vehicles;

import core.Coordinates;
import core.PassengerZone;
import stopovers.CivilAirport;
import stopovers.Stopover;

public class CivilAirplane extends Airplane {
  public final PassengerZone passengerZone;

  public CivilAirplane(Coordinates coordinates, int fuelCapacity, int passengerCapacity) {
    super(coordinates, fuelCapacity);
    passengerZone = new PassengerZone(passengerCapacity);
  }

  public void gotAccommodatedAt(Stopover stopover) {
    if (stopover instanceof CivilAirport) {
      passengerZone.moveAllTo(((CivilAirport) stopover).passengerZone);
    }
  }

  public void gotReleasedFrom(Stopover stopover) {
    if (stopover instanceof CivilAirport) {
      ((CivilAirport) stopover).passengerZone.moveAllWithMatchingDestinationTo(passengerZone, getNextDestination());
    }
  }
}
