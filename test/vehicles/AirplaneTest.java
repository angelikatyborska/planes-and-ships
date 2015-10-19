package vehicles;

import static org.junit.Assert.*;
import core.Coordinates;
import stopovers.Airport;
import stopovers.Stopover;
import stopovers.InvalidVehicleAtStopoverException;
import org.junit.Test;

public class AirplaneTest {
  private class StubAirplane extends Airplane {
    public StubAirplane(double velocity, double fuelCapacity) {
      super(velocity, fuelCapacity);
    }
  }

  private class StubAirport extends Airport {
    public StubAirport(Coordinates coordinates, int vehicleCapacity) {
      super(coordinates, vehicleCapacity);
    }
  }

  @Test
  public void shouldBurnAndRefillFuel() {
    Airplane airplane = new StubAirplane(1, 100);
    airplane.burnFuel(50);

    assertTrue(airplane.getFuel() < 100);

    airplane.refillFuel();

    assertEquals(100, airplane.getFuel(), 0.000001);
  }

  @Test
  public void shouldNotBurnFuelWhenEmpty() {
    Airplane airplane = new StubAirplane(1, 0);
    airplane.burnFuel(50);

    assertEquals(0, airplane.getFuel(), 0.000001);
  }

  @Test
  public void shouldRefillFuelAtAirport() throws InvalidVehicleAtStopoverException {
    Airport airport = new StubAirport(new Coordinates(1,1), 5);
    Airplane airplane = new StubAirplane(1, 100);

    airplane.burnFuel(50);
    airplane.gotAccommodatedAt(airport);

    assertEquals(100, airplane.getFuel(), 0.00001);
  }

  @Test
  public void shouldNotRefillFuelAtDestination() throws InvalidVehicleAtStopoverException {
    Stopover stopover = new Stopover(new Coordinates(1, 1), 5);
    Airplane airplane = new StubAirplane(1, 100);

    airplane.burnFuel(50);
    airplane.gotAccommodatedAt(stopover);

    assertTrue(airplane.getFuel() < 100);
  }

  @Test
  public void shouldNotGetAccommodatedWhenAirportFull() throws InvalidVehicleAtStopoverException {
    Airport airport = new StubAirport(new Coordinates(1,1), 0);
    Airplane airplane = new StubAirplane(1, 100);

    airplane.burnFuel(50);

    assertFalse(airport.accommodateVehicle(airplane));
    assertEquals(50, airplane.getFuel(), 0.00001);
  }

}
