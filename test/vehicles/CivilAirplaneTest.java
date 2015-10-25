package vehicles;

import static org.junit.Assert.*;

import core.Coordinates;
import org.junit.Test;
import stopovers.CivilAirport;
import stopovers.InvalidVehicleAtStopoverException;
import stopovers.Stopover;

import java.util.ArrayList;


public class CivilAirplaneTest {
  @Test
  public void shouldRefillAirplaneFuelDuringMaintenance() throws InvalidVehicleAtStopoverException, InterruptedException {
    Stopover airport = new CivilAirport(new Coordinates(1,1), 5);
    CivilAirplane airplane = new CivilAirplane(1, 100, 1);
    airplane.setRoute(new ArrayList<>());

    airplane.burnFuel(50);
    airport.prepareAirplaneForTravel(airplane);

    assertEquals(100, airplane.getFuel(), 0.00001);
  }
}
