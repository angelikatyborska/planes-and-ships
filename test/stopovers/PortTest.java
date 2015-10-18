package stopovers;
import static org.mockito.Mockito.*;

import core.Coordinates;
import org.junit.Test;
import vehicles.CivilShip;
import vehicles.Ship;

public class PortTest {
  @Test
     public void shouldAccommodateCivilShip() throws InvalidVehicleAtStopoverException {
    CivilShip civilShip = new CivilShip(1, 100);
    Port port = new Port(new Coordinates(1, 1), 5);

    port.accommodateVehicle(civilShip);
  }

  @Test(expected= InvalidVehicleAtStopoverException.class)
  public void shouldNotAccommodateShip() throws InvalidVehicleAtStopoverException {
    Ship ship = mock(Ship.class);
    Port port = new Port(new Coordinates(1, 1), 5);

    port.accommodateVehicle(ship);
  }
}
