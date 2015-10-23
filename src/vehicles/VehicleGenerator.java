package vehicles;

import core.Weapon;

// TODO: implement
public class VehicleGenerator {
  // TODO: come up with some funny names
  private static String[] CivilShipsCompanies = {"LaIsla Cruises", "Happy Travel", "Waves", "Polskie Rejsy Morskie", "Good German Ships"};
  private static double minVelocity = 1;
  private static double maxVelocity = 3;

  public CivilAirplane newCivilAirplane() {
    return new CivilAirplane(randomVelocity(), Integer.MAX_VALUE, randomPassengerCapacity());
  }

  public MilitaryAirplane newMilitaryAirplane(Weapon.WeaponType weaponType) {
    return new MilitaryAirplane(randomVelocity(), Integer.MAX_VALUE, weaponType);
  }

  public CivilShip newCivilShip() {
    return new CivilShip(randomVelocity(), randomPassengerCapacity());
  }

  public MilitaryShip newMilitaryShip() {
    return new MilitaryShip(randomVelocity(), randomWeaponType());
  }

  private static double randomVelocity() {
    return Math.random() * (maxVelocity - minVelocity) + minVelocity;
  }

  private static int randomPassengerCapacity() {
    return (int) Math.floor(Math.random() * 10 + 4);
  }

  private static Weapon.WeaponType randomWeaponType() {
    int randomNumber = (int) Math.round(Math.random() * 3 + 1);

    switch (randomNumber) {
      case 1:
        return Weapon.WeaponType.MISSILE;
      case 2:
        return Weapon.WeaponType.BOMB;
      default:
        return Weapon.WeaponType.LASER_GUN;
    }
  }
}
