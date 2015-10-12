package stopovers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import core.Coordinates;
import core.Weapon;
import org.junit.Test;
import vehicles.Airplane;
import vehicles.MilitaryAirplane;

public class MilitaryAirportTest {
  @Test
  public void shouldAccommodateMilitaryAirplane() throws InvalidVehicleAtStopoverException {
    MilitaryAirplane militaryAirplane = new MilitaryAirplane(new Coordinates(1, 1), 5, Weapon.WeaponType.BOMB);
    MilitaryAirport militaryAirport = new MilitaryAirport(new Coordinates(1, 1), 5);

    assertTrue(militaryAirport.accommodateVehicle(militaryAirplane));
  }

  @Test(expected= InvalidVehicleAtStopoverException.class)
  public void shouldNotAccommodateAirplane() throws InvalidVehicleAtStopoverException {
    Airplane airplane = mock(Airplane.class, CALLS_REAL_METHODS);
    MilitaryAirport militaryAirport = new MilitaryAirport(new Coordinates(1, 1), 5);

    militaryAirport.accommodateVehicle(airplane);
  }
}
