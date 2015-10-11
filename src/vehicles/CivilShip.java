package vehicles;

import core.Coordinates;
import core.PassengerZone;

public class CivilShip extends Ship {
  public final PassengerZone passengerZone;

  public CivilShip(Coordinates coordinates, int passengerCapacity) {
    super(coordinates);
    passengerZone = new PassengerZone(passengerCapacity);
  }
}
