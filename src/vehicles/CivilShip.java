package vehicles;

import core.PassengerZone;
import gui.WorldDrawer;
import stopovers.CivilDestination;
import stopovers.InvalidVehicleAtStopoverException;
import stopovers.Stopover;

public class CivilShip extends Ship implements CivilVehicle {
  private final PassengerZone passengerZone;

  public CivilShip(double velocity, int passengerCapacity) {
    super(velocity);
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

  @Override
  public void draw(WorldDrawer drawer) {
    drawer.draw(this);
  }

  @Override
  public void arrivedAtStopover(Stopover stopover) throws InvalidVehicleAtStopoverException, InterruptedException {
    while (!stopover.accommodateVehicle(this)) {}
    stopover.prepareVehicleForTravel(this);
    stopover.releaseVehicle(this);
  }
}
