package vehicles;

import core.Coordinates;
import core.Weapon;

public class MilitaryShip extends Ship {
  private Weapon weapon;

  public MilitaryShip(Coordinates coordinates) {
    super(coordinates);
    weapon = new Weapon(Weapon.WeaponType.MISSILE);
  }
}
