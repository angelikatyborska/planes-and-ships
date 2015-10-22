package vehicles;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import core.Coordinates;
import core.Passenger;
import org.junit.Test;
import stopovers.CivilAirport;
import stopovers.InvalidVehicleAtStopoverException;
import stopovers.Stopover;

import java.util.ArrayList;


public class CivilAirplaneTest {
  @Test
  public void shouldRefillAirplaneFuelDuringMaintenance() throws InvalidVehicleAtStopoverException, InterruptedException {
    CivilAirport airportAsAirport = new CivilAirport(new Coordinates(1,1), 5);
    Stopover airportAsStopover = airportAsAirport;
    CivilAirplane airplane = new CivilAirplane(1, 100, 1);
    airplane.setRoute(new ArrayList<Stopover>());

    airplane.burnFuel(50);
    airportAsStopover.vehicleMaintenance(airplane);

    assertEquals(100, airplane.getFuel(), 0.00001);
  }
}
