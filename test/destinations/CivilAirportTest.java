package destinations;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import core.Coordinates;
import org.junit.Test;
import vehicles.CivilAirplane;

public class CivilAirportTest {
  @Test
  public void shouldLeaveAndTakePassengers() {
    CivilAirplane mockedCivilAirplane = mock(CivilAirplane.class);
    CivilAirport civilAirport = new CivilAirport(new Coordinates(1,1), 5);

    civilAirport.accommodateVehicle(mockedCivilAirplane);

    verify(mockedCivilAirplane).moveAllPassengersTo(civilAirport);
    verify(mockedCivilAirplane).accommodateAllPassengers(civilAirport.getPassengers());
  }
}
