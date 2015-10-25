package stopovers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import core.Coordinates;
import core.Weapon;
import org.junit.Test;
import vehicles.Airplane;
import vehicles.CivilAirplane;
import vehicles.CivilShip;
import vehicles.MilitaryAirplane;

public class MilitaryAirportTest {
  @Test
  public void shouldAccommodateMilitaryAirplane() throws InvalidVehicleAtStopoverException {
    MilitaryAirplane militaryAirplane = new MilitaryAirplane(1, 5, Weapon.WeaponType.BOMB);
    Stopover militaryAirport = new MilitaryAirport(new Coordinates(1, 1), 5);

    assertTrue(militaryAirport.accommodateMilitaryAirplane(militaryAirplane));
  }

  @Test(expected= InvalidVehicleAtStopoverException.class)
  public void shouldNotAccommodateCivilAirplane() throws InvalidVehicleAtStopoverException {
    CivilAirplane airplane = mock(CivilAirplane.class, CALLS_REAL_METHODS);
    Stopover militaryAirport = new MilitaryAirport(new Coordinates(1, 1), 5);

    militaryAirport.accommodateCivilAirplane(airplane);
  }

  @Test(expected= InvalidVehicleAtStopoverException.class)
  public void shouldNotAccommodateCivilShip() throws InvalidVehicleAtStopoverException {
    CivilShip vehicle = mock(CivilShip.class, CALLS_REAL_METHODS);
    Stopover militaryAirport = new MilitaryAirport(new Coordinates(1, 1), 5);

    militaryAirport.accommodateCivilShip(vehicle);
  }
}
