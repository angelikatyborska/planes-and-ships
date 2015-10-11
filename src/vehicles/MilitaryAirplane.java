package vehicles;

import core.Coordinates;
import core.Weapon;

public class MilitaryAirplane extends Airplane {
  private Weapon weapon;

  public MilitaryAirplane(Coordinates coordinates, int fuelCapacity) {
    super(coordinates, fuelCapacity);
    weapon = new Weapon(Weapon.WeaponType.BOMB);
  }
}
