package vehicles;

import core.PassengerZone;

public class CivilShip extends Ship {
  public final PassengerZone passengerZone;

  public CivilShip(double velocity, int passengerCapacity) {
    super(velocity);
    passengerZone = new PassengerZone(passengerCapacity);
  }
}
