package destinations;

import static org.junit.Assert.*;

import core.Coordinates;
import org.junit.Test;
import org.mockito.Mockito;
import vehicles.Airplane;
import vehicles.MilitaryAirplane;

public class MilitaryAirportTest {
  @Test
  public void shouldAccommodateMilitaryAirplane() throws InvalidVehicleAtDestinationException {
    MilitaryAirplane militaryAirplane = new MilitaryAirplane(new Coordinates(1, 1), 5);
    MilitaryAirport militaryAirport = new MilitaryAirport(new Coordinates(1, 1), 5);

    assertTrue(militaryAirport.accommodateVehicle(militaryAirplane));
  }

  @Test(expected=InvalidVehicleAtDestinationException.class)
  public void shouldNotAccommodateAirplane() throws InvalidVehicleAtDestinationException {
    Airplane airplane = Mockito.mock(Airplane.class, Mockito.CALLS_REAL_METHODS);
    MilitaryAirport militaryAirport = new MilitaryAirport(new Coordinates(1, 1), 5);

    militaryAirport.accommodateVehicle(airplane);
  }
}