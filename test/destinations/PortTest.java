package destinations;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import core.Coordinates;
import org.junit.Test;
import vehicles.CivilShip;
import vehicles.Ship;

public class PortTest {
  @Test
     public void shouldAccommodateCivilShip() throws InvalidVehicleAtDestinationException {
    CivilShip civilShip = new CivilShip(new Coordinates(1, 1), 100);
    Port port = new Port(new Coordinates(1, 1), 5);

    port.accommodateVehicle(civilShip);
  }

  @Test(expected=InvalidVehicleAtDestinationException.class)
  public void shouldNotAccommodateShip() throws InvalidVehicleAtDestinationException {
    Ship ship = mock(Ship.class);
    Port port = new Port(new Coordinates(1, 1), 5);

    port.accommodateVehicle(ship);
  }
}
