package vehicles;

import core.PassengerZone;
import stopovers.CivilDestination;

public class CivilAirplane extends Airplane implements CivilVehicle {
  private final PassengerZone passengerZone;

  public CivilAirplane(double velocity, int fuelCapacity, int passengerCapacity) {
    super(velocity, fuelCapacity);
    passengerZone = new PassengerZone(passengerCapacity);
  }

  // TODO: show to the teacher - using instanceof
  public CivilDestination getNextCivilDestination() {
    for (int i = previousStopoverNumber + 1; i < route.size(); i++) {
      if (route.get(i) instanceof CivilDestination) {
        return (CivilDestination) route.get(i);
      }
    }
    return null;
  }

  @Override
  public PassengerZone passengerZone() {
    return passengerZone;
  }
}
