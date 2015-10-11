package destinations;
import static org.junit.Assert.*;

import core.Coordinates;
import org.junit.Test;
import org.mockito.Mockito;
import vehicles.CivilAirplane;
import vehicles.Airplane;

public class CivilAirportTest {
  @Test
  public void shouldAccommodateCivilAirplane() throws InvalidVehicleAtDestinationException {
    CivilAirplane civilAirplane = new CivilAirplane(new Coordinates(1,1), 5);
    CivilAirport civilAirport = new CivilAirport(new Coordinates(1, 1), 5);

    assertTrue(civilAirport.accommodateVehicle(civilAirplane));
  }

  @Test(expected=InvalidVehicleAtDestinationException.class)
  public void shouldNotAccommodateAirplane() throws InvalidVehicleAtDestinationException {
    Airplane airplane = Mockito.mock(Airplane.class, Mockito.CALLS_REAL_METHODS);
    CivilAirport civilAirport = new CivilAirport(new Coordinates(1, 1), 5);

    civilAirport.accommodateVehicle(airplane);
  }
}
