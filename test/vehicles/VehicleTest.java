package vehicles;

import core.Coordinates;
import org.junit.Test;
import stopovers.Stopover;
import world.StopoverNetwork;
import world.WorldClock;
import world.WorldMap;

import static org.junit.Assert.*;

public class VehicleTest {
  private class StubVehicle extends Vehicle {
    private Stopover stopover;

    public StubVehicle(double velocity, Stopover stopover) {
      super(velocity);
      this.stopover = stopover;
    }

    public Stopover getNextStopover() { return stopover; }
  }

  @Test
  public void shouldMoveWhenClockIsTicking() {
    WorldClock clock = new WorldClock(100, 3);
    WorldMap map = new WorldMap(new StopoverNetwork());
    Stopover stopover = new Stopover(new Coordinates(100, 100), 1);
    Vehicle vehicle = new StubVehicle(10, stopover);

    map.registerVehicle(vehicle, new Coordinates(10, 10));
    double oldDistance = stopover.getCoordinates().distanceTo(vehicle.getCoordinates());

    clock.addListener(vehicle);

    new Thread(vehicle).start();
    new Thread(clock).run();

    double newDistance = stopover.getCoordinates().distanceTo(vehicle.getCoordinates());
    assertTrue(newDistance < oldDistance);
  }
}
