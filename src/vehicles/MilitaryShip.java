package vehicles;

import core.Weapon;

public class MilitaryShip extends Ship {
  private Weapon weapon;

  public MilitaryShip(Weapon.WeaponType weaponType) {
    super();
    weapon = new Weapon(weaponType);
  }

  private void spawnMilitaryAirplane() {

  }
}
