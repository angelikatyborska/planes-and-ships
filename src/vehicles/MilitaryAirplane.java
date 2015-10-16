package vehicles;

import core.Weapon;

public class MilitaryAirplane extends Airplane {
  private Weapon weapon;

  public MilitaryAirplane(int fuelCapacity, Weapon.WeaponType weaponType) {
    super(fuelCapacity);
    weapon = new Weapon(weaponType);
  }
}
