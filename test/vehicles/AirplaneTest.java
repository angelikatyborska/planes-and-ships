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
}
