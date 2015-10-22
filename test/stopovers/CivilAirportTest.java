package stopovers;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import core.Coordinates;
import org.junit.Test;
import vehicles.CivilAirplane;
import vehicles.Airplane;
import vehicles.MilitaryAirplane;
import vehicles.Vehicle;

public class CivilAirportTest {
  @Test
  public void shouldAccommodateCivilAirplane() throws InvalidVehicleAtStopoverException {
    CivilAirplane civilAirplane = new CivilAirplane(1, 5, 100);
    Stopover civilAirport = new CivilAirport(new Coordinates(1, 1), 5);

    assertTrue(civilAirport.accommodateVehicle(civilAirplane));
  }

  @Test(expected= InvalidVehicleAtStopoverException.class)
  public void shouldNotAccommodateAirplane() throws InvalidVehicleAtStopoverException {
    Airplane airplane = mock(Airplane.class, CALLS_REAL_METHODS);
    Stopover civilAirport = new CivilAirport(new Coordinates(1, 1), 5);

    civilAirport.accommodateVehicle(airplane);
  }

  @Test(expected= InvalidVehicleAtStopoverException.class)
  public void shouldNotAccommodateMilitaryAirplane() throws InvalidVehicleAtStopoverException {
    MilitaryAirplane airplane = mock(MilitaryAirplane.class, CALLS_REAL_METHODS);
    Stopover civilAirport = new CivilAirport(new Coordinates(1, 1), 5);

    civilAirport.accommodateVehicle(airplane);
  }
}
