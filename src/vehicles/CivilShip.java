package vehicles;

import core.PassengerZone;
import stopovers.Stopover;

public class CivilShip extends Ship {
  public final PassengerZone passengerZone;

  public CivilShip(double velocity, int passengerCapacity) {
    super(velocity);
    passengerZone = new PassengerZone(passengerCapacity);
  }

  @Override
  public void gotAccommodatedAt(Stopover stopover) {

  }

  @Override
  public void gotReleasedFrom(Stopover stopover) {

  }
}
