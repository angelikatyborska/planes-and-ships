package world;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import core.Coordinates;
import org.junit.Test;
import vehicles.Vehicle;

public class WorldMapTest {
  @Test
  public void shouldRegisterAndGetVehicleCoordinates() {
    WorldMap worldMap = new WorldMap(null);
    Vehicle vehicle = mock(Vehicle.class);

    worldMap.registerVehicle(vehicle, new Coordinates(34, 22));
    Coordinates coordinates = worldMap.getVehicleCoordinates(vehicle);

    assertEquals(34, coordinates.getX(), 0.000001);
    assertEquals(22, coordinates.getY(), 0.000001);
  }
}
