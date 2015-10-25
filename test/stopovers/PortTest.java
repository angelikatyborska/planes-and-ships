package stopovers;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import core.Coordinates;
import org.junit.Test;
import vehicles.CivilAirplane;
import vehicles.CivilShip;
import vehicles.Ship;

public class PortTest {
  @Test
     public void shouldAccommodateCivilShip() throws InvalidVehicleAtStopoverException {
    CivilShip civilShip = new CivilShip(1, 100);
    Port port = new Port(new Coordinates(1, 1), 5);

    assertTrue(port.accommodateCivilShip(civilShip));
  }

  @Test(expected= InvalidVehicleAtStopoverException.class)
  public void shouldNotAccommodateCivilAirplane() throws InvalidVehicleAtStopoverException {
    CivilAirplane airplane = mock(CivilAirplane.class);
    Port port = new Port(new Coordinates(1, 1), 5);

    port.accommodateCivilAirplane(airplane);
  }
}
