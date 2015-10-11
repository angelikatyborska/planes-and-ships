package destinations;

import static org.junit.Assert.*;

import core.Coordinates;
import org.junit.Test;
import org.mockito.Mockito;
import vehicles.Airplane;
import vehicles.Vehicle;


public class AirportTest {
  private class StubAirport extends Airport {
    public StubAirport(Coordinates coordinates, int vehicleCapacity) {
      super(coordinates, vehicleCapacity);
    }
  }

  @Test
  public void shouldAccommodateAirplanes() throws InvalidVehicleAtDestinationException {
    Airplane airplane = Mockito.mock(Airplane.class, Mockito.CALLS_REAL_METHODS);
    Airport airport = new StubAirport(new Coordinates(1,1), 5);

    assertTrue(airport.accommodateVehicle(airplane));
  }

  @Test(expected=InvalidVehicleAtDestinationException.class)
  public void shouldNotAccommodateVehicles() throws InvalidVehicleAtDestinationException {
    Vehicle vehicle = Mockito.mock(Vehicle.class, Mockito.CALLS_REAL_METHODS);
    Airport airport = new StubAirport(new Coordinates(1,1), 5);

    airport.accommodateVehicle(vehicle);
  }
}
