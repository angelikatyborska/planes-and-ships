package vehicles;

import static org.junit.Assert.*;

import core.Weapon;
import org.junit.Test;

public class VehicleGeneratorTest {
  private static VehicleGenerator generator = new VehicleGenerator();

  @Test
  public void shouldGenerateCivilAirplane() {
    CivilAirplane civilAirplane = generator.newCivilAirplane();

    assertTrue(civilAirplane != null);
  }

  @Test
  public void shouldGenerateMilitaryAirplane() {
    MilitaryAirplane militaryAirplane = generator.newMilitaryAirplane(Weapon.WeaponType.BOMB);

    assertTrue(militaryAirplane != null);
  }

  @Test
  public void shouldGenerateCivilShip() {
    CivilShip civilShip = generator.newCivilShip();

    assertTrue(civilShip != null);
  }

  @Test
  public void shouldGenerateMilitaryShip() {
    MilitaryShip militaryShip = generator.newMilitaryShip();

    assertTrue(militaryShip != null);
  }
}
