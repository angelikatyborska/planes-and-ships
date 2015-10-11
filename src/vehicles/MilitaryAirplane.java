package vehicles;

import core.Coordinates;
import core.Weapon;

public class MilitaryAirplane extends Airplane {
  private Weapon weapon;

  public MilitaryAirplane(Coordinates coordinates, int fuelCapacity, Weapon.WeaponType weaponType) {
    super(coordinates, fuelCapacity);
    weapon = new Weapon(weaponType);
  }
}
