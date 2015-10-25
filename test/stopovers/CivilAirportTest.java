package stopovers;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import core.Coordinates;
import org.junit.Test;
import vehicles.CivilAirplane;
import vehicles.Airplane;
import vehicles.CivilShip;
import vehicles.MilitaryAirplane;


public class CivilAirportTest {
  @Test
  public void shouldAccommodateCivilAirplane() throws InvalidVehicleAtStopoverException {
    CivilAirplane civilAirplane = new CivilAirplane(1, 5, 100);
    Stopover civilAirport = new CivilAirport(new Coordinates(1, 1), 5);

    assertTrue(civilAirport.accommodateCivilAirplane(civilAirplane));
  }

  @Test(expected= InvalidVehicleAtStopoverException.class)
  public void shouldNotAccommodateCivilShip() throws InvalidVehicleAtStopoverException {
    CivilShip ship = mock(CivilShip.class, CALLS_REAL_METHODS);
    Stopover civilAirport = new CivilAirport(new Coordinates(1, 1), 5);

    civilAirport.accommodateCivilShip(ship);
  }

  @Test(expected= InvalidVehicleAtStopoverException.class)
  public void shouldNotAccommodateMilitaryAirplane() throws InvalidVehicleAtStopoverException {
    MilitaryAirplane airplane = mock(MilitaryAirplane.class, CALLS_REAL_METHODS);
    Stopover civilAirport = new CivilAirport(new Coordinates(1, 1), 5);

    civilAirport.accommodateMilitaryAirplane(airplane);
  }
}
