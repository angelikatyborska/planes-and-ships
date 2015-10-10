package destinations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import core.Coordinates;
import org.junit.Test;
import vehicles.Airplane;


public class AirportTest {
  @Test
  public void shouldRefillFuel() {
    Airplane mockedAirplane = mock(Airplane.class);
    Airport airport = new Airport(new Coordinates(1,1), 5);

    airport.accommodateVehicle(mockedAirplane);

    verify(mockedAirplane).refillFuel();
  }
}
