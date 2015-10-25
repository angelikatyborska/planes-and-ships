package world;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import core.Coordinates;
import org.junit.Test;
import stopovers.InvalidVehicleAtStopoverException;
import stopovers.Stopover;
import vehicles.Vehicle;

public class WorldMapTest {
  private class StubVehicle extends Vehicle {
    public StubVehicle(double velocity) { super(velocity); }

    @Override
    public void arrivedAtStopover(Stopover stopover) throws InvalidVehicleAtStopoverException, InterruptedException {}
  }

  @Test
  public void shouldRegisterAndGetVehicleCoordinates() {
    WorldMap worldMap = new WorldMap(null);
    Vehicle vehicle = new StubVehicle(1);

    worldMap.registerVehicle(vehicle, new Coordinates(34, 22));
    Coordinates coordinates = worldMap.getVehicleCoordinates(vehicle);

    assertEquals(34, coordinates.getX(), 0.000001);
    assertEquals(22, coordinates.getY(), 0.000001);
  }

  @Test
  public void shouldMoveVehicleWhenRouteFree() throws StopoverNotFoundInStopoverNetworkException {
    Stopover stopover1 = mock(Stopover.class);
    Stopover stopover2 = mock(Stopover.class);

    stub(stopover1.getCoordinates()).toReturn(new Coordinates(32, 20));
    stub(stopover2.getCoordinates()).toReturn(new Coordinates(38, 26));

    Vehicle vehicle = new StubVehicle(1) {
      @Override
      public Stopover getPreviousStopover() { return stopover1; }
      public Stopover getNextStopover() { return stopover2; }
    };

    StopoverNetwork network = new StopoverNetwork();

    network.add(stopover1);
    network.add(stopover2);
    network.connect(stopover1, stopover2);

    WorldMap worldMap = new WorldMap(network);

    worldMap.registerVehicle(vehicle, new Coordinates(34, 22));

    assertTrue(worldMap.moveVehicleTowardsTargetStopover(vehicle, 1));
  }

  @Test
  public void shouldMoveVehicleWhenJustPassing() throws StopoverNotFoundInStopoverNetworkException {
    Stopover stopover1 = mock(Stopover.class);
    Stopover stopover2 = mock(Stopover.class);

    stub(stopover1.getCoordinates()).toReturn(new Coordinates(32, 20));
    stub(stopover2.getCoordinates()).toReturn(new Coordinates(38, 26));

    Vehicle vehicle1 = new StubVehicle(1) {
      @Override
      public Stopover getPreviousStopover() { return stopover1; }
      public Stopover getNextStopover() { return stopover2; }
    };

    Vehicle vehicle2 = new StubVehicle(1) {
      @Override
      public Stopover getPreviousStopover() { return stopover2; }
      public Stopover getNextStopover() { return stopover1; }
    };

    StopoverNetwork network = new StopoverNetwork();

    network.add(stopover1);
    network.add(stopover2);
    network.connect(stopover1, stopover2);

    WorldMap worldMap = new WorldMap(network);

    worldMap.registerVehicle(vehicle1, new Coordinates(34, 22));
    worldMap.registerVehicle(vehicle2, new Coordinates(34.01, 22.01));

    assertTrue(worldMap.moveVehicleTowardsTargetStopover(vehicle1, 1));
  }

  @Test
  public void shouldNotMoveVehicleOnCollisionCourse() throws StopoverNotFoundInStopoverNetworkException {
    Stopover stopover1 = mock(Stopover.class);
    Stopover stopover2 = mock(Stopover.class);

    stub(stopover1.getCoordinates()).toReturn(new Coordinates(32, 20));
    stub(stopover2.getCoordinates()).toReturn(new Coordinates(38, 26));


    Vehicle vehicle1 = new StubVehicle(1) {
      @Override
      public Stopover getPreviousStopover() { return stopover1; }
      public Stopover getNextStopover() { return stopover2; }
    };

    Vehicle vehicle2 = new StubVehicle(1) {
      @Override
      public Stopover getPreviousStopover() { return stopover1; }
      public Stopover getNextStopover() { return stopover2; }
    };

    StopoverNetwork network = new StopoverNetwork();

    network.add(stopover1);
    network.add(stopover2);
    network.connect(stopover1, stopover2);

    WorldMap worldMap = new WorldMap(network);

    worldMap.registerVehicle(vehicle1, new Coordinates(34, 22));
    worldMap.registerVehicle(vehicle2, new Coordinates(34.01, 22.01));

    assertTrue(!worldMap.moveVehicleTowardsTargetStopover(vehicle1, 1));
  }
}
