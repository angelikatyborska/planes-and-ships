package core;

import java.io.Serializable;

/**
 * Represents a weapon that military vehicles can have.
 * @see vehicles.MilitaryAirplane
 * @see vehicles.MilitaryShip
 */
public class Weapon implements Serializable {
  public enum WeaponType { SCISSORS, INK_BOMB, ERASER }
  private WeaponType type;

  public Weapon(WeaponType type) {
    this.type = type;
  }

  public WeaponType getType() {
    return type;
  }

  @Override
  public String toString() {
    switch (type) {
      case SCISSORS:
        return "Scissors";
      case INK_BOMB:
        return "Ink Bomb";
      case ERASER:
        return "Eraser";
      default:
        return "Secret Weapon";
    }
  }
}
