package stopovers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import core.Coordinates;
import org.junit.Test;
import vehicles.Airplane;
import vehicles.MilitaryAirplane;
import vehicles.Vehicle;


public class AirportTest {
  private class StubAirplane extends Airplane {
    public StubAirplane(double velocity, double fuelCapacity) {
      super(velocity, fuelCapacity);
    }

    public void updateRoute() {}
  }

  private class StubAirport extends Airport {
    public StubAirport(Coordinates coordinates, int vehicleCapacity) {
      super(coordinates, vehicleCapacity);
    }
  }

  @Test
  public void shouldAccommodateAirplanes() throws InvalidVehicleAtStopoverException {
    Airplane airplane = new StubAirplane(1, 100);
    Stopover airport = new StubAirport(new Coordinates(1,1), 5);

    assertTrue(airport.accommodateVehicle(airplane));
  }

  @Test(expected= InvalidVehicleAtStopoverException.class)
  public void shouldNotAccommodateVehicles() throws InvalidVehicleAtStopoverException {
    Vehicle vehicle = mock(Vehicle.class, CALLS_REAL_METHODS);
    Stopover airport = new StubAirport(new Coordinates(1,1), 5);

    airport.accommodateVehicle(vehicle);
  }

  @Test
  public void shouldRefillAirplaneFuelDuringMaintenance() throws InvalidVehicleAtStopoverException, InterruptedException {
    Airport airportAsAirport = new StubAirport(new Coordinates(1,1), 5);
    Stopover airtportAsStopover = airportAsAirport;
    Airplane airplane = new StubAirplane(1, 100);

    airplane.burnFuel(50);
    airtportAsStopover.vehicleMaintenance(airplane);

    assertEquals(100, airplane.getFuel(), 0.00001);
  }

  @Test
  public void shouldNotAccommodateAirplaneWhenFull() throws InvalidVehicleAtStopoverException {
    Airport airport = new StubAirport(new Coordinates(1,1), 0);
    Airplane airplane = new StubAirplane(1, 100);

    airplane.burnFuel(50);

    assertFalse(airport.accommodateVehicle(airplane));
    assertEquals(50, airplane.getFuel(), 0.00001);
  }
}
