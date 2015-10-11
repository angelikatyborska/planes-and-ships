package destinations;
import static org.junit.Assert.*;

import core.Coordinates;
import org.junit.Test;
import org.mockito.Mockito;
import vehicles.CivilShip;

public class PortTest {
  @Test
  public void shouldAccommodateCivilShip() {
    CivilShip civilShip = new CivilShip(new Coordinates(1, 1), 100);
  }
}
