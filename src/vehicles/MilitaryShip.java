package vehicles;

import core.Weapon;

public class MilitaryShip extends Ship {
  private Weapon weapon;

  public MilitaryShip(double velocity, Weapon.WeaponType weaponType) {
    super(velocity);
    weapon = new Weapon(weaponType);
  }

  private void spawnMilitaryAirplane() {

  }
}
