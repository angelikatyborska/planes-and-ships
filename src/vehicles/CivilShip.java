package vehicles;

import core.Coordinates;
import core.PassengerZone;

public class CivilShip extends Ship {
  public final PassengerZone passengerZone;

  public CivilShip(int passengerCapacity) {
    super();
    passengerZone = new PassengerZone(passengerCapacity);
  }
}
