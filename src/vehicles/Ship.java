package vehicles;

import gui.Drawer;
import stopovers.InvalidVehicleAtStopoverException;
import stopovers.Stopover;

public abstract class Ship extends Vehicle {
  public Ship(double velocity) {
    super(velocity);
  }

  @Override
  public void draw(Drawer drawer) {
    drawer.drawShip(this);
  }

  @Override
  public void arrivedAtStopover(Stopover stopover) throws InvalidVehicleAtStopoverException, InterruptedException {
    while (!stopover.accommodateVehicle(this)) {}
    stopover.prepareVehicleForTravel(this);
    stopover.releaseVehicle(this);
  }
}
