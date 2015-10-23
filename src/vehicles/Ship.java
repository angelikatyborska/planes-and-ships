package vehicles;

import gui.WorldDrawer;
import stopovers.InvalidVehicleAtStopoverException;
import stopovers.Stopover;

public abstract class Ship extends Vehicle {
  public Ship(double velocity) {
    super(velocity);
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
