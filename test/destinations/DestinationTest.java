package destinations;

import static org.junit.Assert.*;

import core.Coordinates;
import org.junit.Test;
import vehicles.Vehicle;

public class DestinationTest {
  @Test
  public void shouldAccommodateVehicleWhenNotFull() {
    Coordinates coordinates = new Coordinates(1,1);
    Destination destination = new Destination(coordinates, 1);
    Vehicle vehicle = new Vehicle(coordinates);

    assertTrue(destination.accommodateVehicle(vehicle));
    assertEquals(1, destination.getAccommodatedVehicles().size());
  }

  @Test
  public void shouldNotAccommodateVehicleWhenFull() {
    Coordinates coordinates = new Coordinates(1,1);
    Destination destination = new Destination(coordinates, 0);
    Vehicle vehicle = new Vehicle(coordinates);

    assertFalse(destination.accommodateVehicle(vehicle));
    assertEquals(0, destination.getAccommodatedVehicles().size());
  }

  @Test
  public void shouldReleaseAccommodatedVehicle() {
    Coordinates coordinates = new Coordinates(1,1);
    Destination destination = new Destination(coordinates, 5);
    Vehicle vehicle = new Vehicle(coordinates);
    Vehicle vehicle2 = new Vehicle(coordinates);

    destination.accommodateVehicle(vehicle);
    destination.accommodateVehicle(vehicle2);

    assertTrue(destination.releaseVehicle(vehicle));
    assertEquals(1, destination.getAccommodatedVehicles().size());
  }

  @Test
  public void shouldNotReleaseNotAccommodatedVehicle() {
    Coordinates coordinates = new Coordinates(1,1);
    Destination destination = new Destination(coordinates, 5);
    Vehicle vehicle = new Vehicle(coordinates);

    assertFalse(destination.releaseVehicle(vehicle));
    assertEquals(0, destination.getAccommodatedVehicles().size());
  }
}
