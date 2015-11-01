package vehicles;

import core.Coordinates;
import core.Weapon;
import org.junit.Test;
import stopovers.InvalidVehicleAtStopoverException;
import stopovers.MilitaryAirport;
import stopovers.Stopover;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


public class MilitaryAirplaneTest {
  @Test
  public void shouldRefillAirplaneFuelDuringMaintenance() throws InvalidVehicleAtStopoverException, InterruptedException {
    Stopover militaryAirport = new MilitaryAirport(new Coordinates(1,1), 5);
    MilitaryAirplane airplane = new MilitaryAirplane(1, 100, Weapon.WeaponType.INK_BOOMB);
    airplane.setRoute(new ArrayList<>());

    airplane.burnFuel(50);
    militaryAirport.prepareMilitaryAirplaneForTravel(airplane);

    assertEquals(100, airplane.getFuel(), 0.00001);
  }
}
