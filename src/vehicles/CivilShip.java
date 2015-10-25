package vehicles;

import core.PassengerZone;
import gui.Drawer;
import stopovers.CivilDestination;
import stopovers.InvalidVehicleAtStopoverException;
import stopovers.Port;
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
  public void draw(Drawer drawer) {
    drawer.drawCivilShip(this);
  }

  @Override
  public void arrivedAtStopover(Stopover stopover) throws InvalidVehicleAtStopoverException, InterruptedException {
    while (!stopover.accommodateCivilShip(this)) {}
    stopover.prepareCivilShipForTravel(this);
    stopover.releaseVehicle(this);
  }

  public Port getNextPort() {
    for (int i = previousStopoverNumber; i < route.size(); i++) {
      if (route.get(i) instanceof Port) {
        return (Port) route.get(i);
      }
    }

    return null;
  }
}
