package vehicles;

import core.Coordinates;
import core.Weapon;

public class MilitaryShip extends Ship {
  private Weapon weapon;

  public MilitaryShip(Coordinates coordinates, Weapon.WeaponType weaponType) {
    super(coordinates);
    weapon = new Weapon(weaponType);
  }

  private void spawnMilitaryAirplane() {

  }
}
