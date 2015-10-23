package vehicles;

import core.Weapon;
import gui.WorldDrawer;
import stopovers.InvalidVehicleAtStopoverException;
import stopovers.Stopover;

import static java.lang.Thread.sleep;

public class MilitaryAirplane extends Airplane {
  private Weapon weapon;

  public MilitaryAirplane(double velocity, int fuelCapacity, Weapon.WeaponType weaponType) {
    super(velocity, fuelCapacity);
    weapon = new Weapon(weaponType);
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
