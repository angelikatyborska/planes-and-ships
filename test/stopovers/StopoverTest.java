package stopovers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import core.Coordinates;
import org.junit.Test;
import vehicles.Vehicle;

public class StopoverTest {
  @Test
  public void shouldAccommodateVehicleWhenNotFull() throws InvalidVehicleAtStopoverException {
    Coordinates coordinates = new Coordinates(1,1);
    Stopover stopover = new Stopover(coordinates, 1);
    Vehicle mockedVehicle = mock(Vehicle.class);

    assertTrue(stopover.accommodateVehicle(mockedVehicle));
    assertEquals(1, stopover.getAccommodatedVehicles().size());
  }

  @Test
  public void shouldNotAccommodateVehicleWhenFull() throws InvalidVehicleAtStopoverException {
    Coordinates coordinates = new Coordinates(1,1);
    Stopover stopover = new Stopover(coordinates, 0);
    Vehicle mockedVehicle = mock(Vehicle.class);

    assertFalse(stopover.accommodateVehicle(mockedVehicle));
    assertEquals(0, stopover.getAccommodatedVehicles().size());
  }

  @Test
  public void shouldReleaseAccommodatedVehicle() throws InvalidVehicleAtStopoverException {
    Coordinates coordinates = new Coordinates(1,1);
    Stopover stopover = new Stopover(coordinates, 5);
    Vehicle mockedVehicle = mock(Vehicle.class);

    stopover.accommodateVehicle(mockedVehicle);

    assertTrue(stopover.releaseVehicle(mockedVehicle));
    assertEquals(0, stopover.getAccommodatedVehicles().size());
  }

  @Test
  public void shouldNotReleaseNotAccommodatedVehicle() {
    Coordinates coordinates = new Coordinates(1,1);
    Stopover stopover = new Stopover(coordinates, 5);
    Vehicle mockedVehicle = mock(Vehicle.class);

    assertFalse(stopover.releaseVehicle(mockedVehicle));
    assertEquals(0, stopover.getAccommodatedVehicles().size());
  }
}
