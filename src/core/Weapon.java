package core;

public class Weapon {
  public static enum WeaponType { MISSILE, BOMB };
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
}
