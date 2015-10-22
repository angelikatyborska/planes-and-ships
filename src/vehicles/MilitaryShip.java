package vehicles;

import core.Weapon;

public class MilitaryShip extends Ship {
  public Weapon getWeapon() {
    return weapon;
  }

  private Weapon weapon;

  public MilitaryShip(double velocity, Weapon.WeaponType weaponType) {
    super(velocity);
    weapon = new Weapon(weaponType);
  }
}
