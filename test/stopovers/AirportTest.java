package stopovers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import core.Coordinates;
import core.Weapon;
import org.junit.Test;
import vehicles.*;


public class AirportTest {
  private class StubAirport extends Airport {
    public StubAirport(Coordinates coordinates, int vehicleCapacity) {
      super(coordinates, vehicleCapacity);
    }
  }

  @Test
  public void shouldAccommodateAirplanes() throws InvalidVehicleAtStopoverException {
    MilitaryAirplane militaryAirplane = mock(MilitaryAirplane.class);
    CivilAirplane civilAirplane = mock(CivilAirplane.class);
    Stopover airport = new StubAirport(new Coordinates(1,1), 5);

    assertTrue(airport.accommodateMilitaryAirplane(militaryAirplane));
    assertTrue(airport.accommodateCivilAirplane(civilAirplane));
  }

  @Test(expected= InvalidVehicleAtStopoverException.class)
  public void shouldNotAccommodateMilitaryShips() throws InvalidVehicleAtStopoverException {
    MilitaryShip militaryShip = mock(MilitaryShip.class);
    Stopover airport = new StubAirport(new Coordinates(1,1), 5);

    airport.accommodateMilitaryShip(militaryShip);
  }

  @Test(expected= InvalidVehicleAtStopoverException.class)
  public void shouldNotAccommodateCivilShips() throws InvalidVehicleAtStopoverException {
    CivilShip civilShip = mock(CivilShip.class);
    Stopover airport = new StubAirport(new Coordinates(1,1), 5);

    airport.accommodateCivilShip(civilShip);
  }

  @Test
  public void shouldRefillAirplaneFuelDuringMaintenance() throws InvalidVehicleAtStopoverException, InterruptedException {
    Stopover airport = new StubAirport(new Coordinates(1,1), 5);
    MilitaryAirplane airplane = new MilitaryAirplane(1, 100, Weapon.WeaponType.INK_BOMB);

    airplane.burnFuel(50);
    airport.prepareAirplaneForTravel(airplane);

    assertEquals(100, airplane.getFuel(), 0.00001);
  }

  @Test
  public void shouldNotAccommodateAirplaneWhenFull() throws InvalidVehicleAtStopoverException {
    Stopover airport = new StubAirport(new Coordinates(1,1), 0);
    MilitaryAirplane airplane = mock(MilitaryAirplane.class);

    assertFalse(airport.accommodateMilitaryAirplane(airplane));
  }
}
