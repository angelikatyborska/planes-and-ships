package stopovers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import core.Coordinates;
import org.junit.Test;
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
    Airplane airplane = mock(Airplane.class, CALLS_REAL_METHODS);
    Airport airport = new StubAirport(new Coordinates(1,1), 5);

    assertTrue(airport.accommodateVehicle(airplane));
  }

  @Test(expected=InvalidVehicleAtDestinationException.class)
  public void shouldNotAccommodateVehicles() throws InvalidVehicleAtDestinationException {
    Vehicle vehicle = mock(Vehicle.class, CALLS_REAL_METHODS);
    Airport airport = new StubAirport(new Coordinates(1,1), 5);

    airport.accommodateVehicle(vehicle);
  }
}
