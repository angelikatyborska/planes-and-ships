package vehicles;

import core.Weapon;
import gui.WorldDrawer;
import stopovers.InvalidVehicleAtStopoverException;
import stopovers.Stopover;

import static java.lang.Thread.sleep;

public class MilitaryShip extends Ship {
  public Weapon getWeapon() {
    return weapon;
  }

  private Weapon weapon;

  public MilitaryShip(double velocity, Weapon.WeaponType weaponType) {
    super(velocity);
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
