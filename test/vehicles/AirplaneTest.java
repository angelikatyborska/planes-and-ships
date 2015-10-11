package vehicles;

import static org.junit.Assert.*;
import core.Coordinates;
import destinations.Airport;
import destinations.InvalidVehicleAtDestinationException;
import org.junit.Test;

public class AirplaneTest {
  private class StubAirplane extends Airplane {
    public StubAirplane(Coordinates coordinates, double fuelCapacity) {
      super(coordinates, fuelCapacity);
    }
  }

  private class StubAirport extends Airport {
    public StubAirport(Coordinates coordinates, int vehicleCapacity) {
      super(coordinates, vehicleCapacity);
    }
  }

  @Test
  public void shouldBurnAndRefillFuel() {
    Airplane airplane = new StubAirplane(new Coordinates(1,1), 100);
    airplane.burnFuel(50);

    assertTrue(airplane.getFuel() < 100);

    airplane.refillFuel();

    assertEquals(100, airplane.getFuel(), 0.000001);
  }

  @Test
  public void shouldNotBurnFuelWhenEmpty() {
    Airplane airplane = new StubAirplane(new Coordinates(1,1), 0);
    airplane.burnFuel(50);

    assertEquals(0, airplane.getFuel(), 0.000001);
  }

  @Test
  public void shouldRefillFuelAtAirport() throws InvalidVehicleAtDestinationException {
    Airport airport = new StubAirport(new Coordinates(1,1), 5);
    Airplane airplane = new StubAirplane(new Coordinates(1,1), 100);

    airplane.burnFuel(50);
    airport.accommodateVehicle(airplane);

    assertEquals(100, airplane.getFuel(), 0.00001);
  }
}
