package vehicles;

import core.Coordinates;
import org.junit.Test;
import stopovers.CivilAirport;
import stopovers.Stopover;
import world.*;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

public class VehicleTest {
  private class StubVehicle extends Vehicle {
    private Stopover stopover;

    public StubVehicle(double velocity, Stopover stopover) {
      super(velocity);
      this.stopover = stopover;
    }

    public Stopover getPreviousStopover() { return null; }

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

  @Test
  public void StopoversShouldNotMoveWhenVehicleIsMoving() throws StopoverNotFoundInStopoverNetworkException, InterruptedException {
    StopoverNetwork network = new StopoverNetwork();

    CivilAirport airport1 = new CivilAirport(new Coordinates(20, 60), 4);
    CivilAirport airport2 = new CivilAirport(new Coordinates(300, 140), 4);
    CivilAirport airport3 = new CivilAirport(new Coordinates(400, 30), 4);

    network.add(airport1);
    network.add(airport2);
    network.add(airport3);

    network.connect(airport1, airport2);
    network.connect(airport2, airport3);
    network.connect(airport3, airport1);

    WorldMap map = new WorldMap(network);
    World world = new World(new WorldClock(100), map);

    world.addCivilAirplane();

    sleep(300);

    world.shutDown();

    assertEquals(20, airport1.getCoordinates().getX(), 0.00001);
    assertEquals(60, airport1.getCoordinates().getY(), 0.00001);
    assertEquals(300, airport2.getCoordinates().getX(), 0.00001);
    assertEquals(140, airport2.getCoordinates().getY(), 0.00001);
    assertEquals(400, airport3.getCoordinates().getX(), 0.00001);
    assertEquals(30, airport3.getCoordinates().getY(), 0.00001);
  }


}
