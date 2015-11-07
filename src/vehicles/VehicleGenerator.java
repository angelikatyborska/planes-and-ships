package vehicles;

import core.Weapon;

public class VehicleGenerator {
  private static String[] CivilShipsCompanies = {"Paper Ships Inc.", "Mr Clippy's Cruises", "Polish State Cruises"};
  private static double minVelocity = 0.3;
  private static double maxVelocity = 2;

  public CivilAirplane newCivilAirplane() {
    return new CivilAirplane(randomVelocity(), 5000, randomPassengerCapacity());
  }

  public MilitaryAirplane newMilitaryAirplane(Weapon.WeaponType weaponType) {
    return new MilitaryAirplane(randomVelocity(), 5000, weaponType);
  }

  public CivilShip newCivilShip() {
    return new CivilShip(randomVelocity(), randomPassengerCapacity(), randomCompany());
  }

  public MilitaryShip newMilitaryShip() {
    return new MilitaryShip(randomVelocity(), randomWeaponType());
  }

  private static String randomCompany() {
    return CivilShipsCompanies[(int) Math.floor(Math.random() * CivilShipsCompanies.length)];
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
        return Weapon.WeaponType.SCISSORS;
      case 2:
        return Weapon.WeaponType.INK_BOMB;
      default:
        return Weapon.WeaponType.ERASER;
    }
  }
}
