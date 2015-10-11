package vehicles;

import core.Coordinates;
import core.PassengerZone;

public class CivilAirplane extends Airplane {
  public final PassengerZone passengerZone;

  public CivilAirplane(Coordinates coordinates, int fuelCapacity, int passengerCapacity) {
    super(coordinates, fuelCapacity);
    passengerZone = new PassengerZone(passengerCapacity);
  }
}
