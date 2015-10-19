package vehicles;

import core.Weapon;
import stopovers.Stopover;

public class MilitaryShip extends Ship {
  private Weapon weapon;

  public MilitaryShip(double velocity, Weapon.WeaponType weaponType) {
    super(velocity);
    weapon = new Weapon(weaponType);
  }

  private void spawnMilitaryAirplane() {

  }

  @Override
  public void gotAccommodatedAt(Stopover stopover) {

  }

  @Override
  public void gotReleasedFrom(Stopover stopover) {

  }
}
