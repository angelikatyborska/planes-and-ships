package vehicles;

import core.Weapon;

public class MilitaryAirplane extends Airplane {
  private Weapon weapon;

  public MilitaryAirplane(double velocity, int fuelCapacity, Weapon.WeaponType weaponType) {
    super(velocity, fuelCapacity);
    weapon = new Weapon(weaponType);
  }
}
