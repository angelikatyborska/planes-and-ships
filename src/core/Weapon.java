package core;

public class Weapon {
  public enum WeaponType { SCISSORS, INK_BOOMB, ERASER };
  private WeaponType type;

  public Weapon(WeaponType type) {
    this.type = type;
  }

  public String fire() {
    return "PewPewPew";
  }

  public WeaponType getType() {
    return type;
  }

  @Override
  public String toString() {
    switch (type) {
      case SCISSORS:
        return "Scissors";
      case INK_BOOMB:
        return "Ink Bomb";
      case ERASER:
        return "Eraser";
      default:
        return "Secret Weapon";
    }
  }
}
