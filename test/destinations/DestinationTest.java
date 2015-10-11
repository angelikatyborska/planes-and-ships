package destinations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import core.Coordinates;
import org.junit.Test;
import vehicles.Vehicle;

public class DestinationTest {
  @Test
  public void shouldAccommodateVehicleWhenNotFull() throws InvalidVehicleAtDestinationException {
    Coordinates coordinates = new Coordinates(1,1);
    Destination destination = new Destination(coordinates, 1);
    Vehicle mockedVehicle = mock(Vehicle.class);

    assertTrue(destination.accommodateVehicle(mockedVehicle));
    assertEquals(1, destination.getAccommodatedVehicles().size());
    verify(mockedVehicle).gotAccommodatedAt(destination);
  }

  @Test
  public void shouldNotAccommodateVehicleWhenFull() throws InvalidVehicleAtDestinationException {
    Coordinates coordinates = new Coordinates(1,1);
    Destination destination = new Destination(coordinates, 0);
    Vehicle mockedVehicle = mock(Vehicle.class);

    assertFalse(destination.accommodateVehicle(mockedVehicle));
    assertEquals(0, destination.getAccommodatedVehicles().size());
    verify(mockedVehicle, never()).gotAccommodatedAt(destination);
  }

  @Test
  public void shouldReleaseAccommodatedVehicle() throws InvalidVehicleAtDestinationException {
    Coordinates coordinates = new Coordinates(1,1);
    Destination destination = new Destination(coordinates, 5);
    Vehicle mockedVehicle = mock(Vehicle.class);

    destination.accommodateVehicle(mockedVehicle);

    assertTrue(destination.releaseVehicle(mockedVehicle));
    assertEquals(0, destination.getAccommodatedVehicles().size());
    verify(mockedVehicle).gotReleasedFrom(destination);
  }

  @Test
  public void shouldNotReleaseNotAccommodatedVehicle() {
    Coordinates coordinates = new Coordinates(1,1);
    Destination destination = new Destination(coordinates, 5);
    Vehicle mockedVehicle = mock(Vehicle.class);

    assertFalse(destination.releaseVehicle(mockedVehicle));
    assertEquals(0, destination.getAccommodatedVehicles().size());
    verify(mockedVehicle, never()).gotReleasedFrom(destination);
  }
}
