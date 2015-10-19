package vehicles;

import core.Weapon;
import stopovers.Stopover;

public class MilitaryAirplane extends Airplane {
  private Weapon weapon;

  public MilitaryAirplane(double velocity, int fuelCapacity, Weapon.WeaponType weaponType) {
    super(velocity, fuelCapacity);
    weapon = new Weapon(weaponType);
  }

  @Override
  public void gotAccommodatedAt(Stopover stopover) {

  }
}
