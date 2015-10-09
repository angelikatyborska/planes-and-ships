import static org.junit.Assert.*;

import core.Coordinates;
import org.junit.Test;
import destinations.Destination;
import vehicles.Vehicle;

public class DestinationTest {
  @Test
  public void shouldAccommodateVehicleWhenNotFull() {
    Coordinates coordinates = new Coordinates(1,1);
    Destination destination = new Destination(coordinates, 1);
    Vehicle vehicle = new Vehicle(coordinates);

    assertTrue(destination.accommodateVehicle(vehicle));
    assertEquals(1, destination.getVehiclesStaying().size());
  }
}
