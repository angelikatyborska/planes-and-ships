package stopovers;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import core.Coordinates;
import org.junit.Test;
import vehicles.CivilShip;
import vehicles.Ship;

public class PortTest {
  @Test
     public void shouldAccommodateCivilShip() throws InvalidVehicleAtStopoverException {
    CivilShip civilShip = new CivilShip(1, 100);
    Port port = new Port(new Coordinates(1, 1), 5);

    assertTrue(port.accommodateVehicle(civilShip));
  }

  @Test(expected= InvalidVehicleAtStopoverException.class)
  public void shouldNotAccommodateShip() throws InvalidVehicleAtStopoverException {
    Ship ship = mock(Ship.class);
    Port port = new Port(new Coordinates(1, 1), 5);

    port.accommodateVehicle(ship);
  }

  // TODO: implement this test when checking for matching destinations between passenger and civilship is resolved
//  @Test
//  public void CivilShipShouldExchangePassengersAfterMaintenanceAtPort() throws InvalidVehicleAtStopoverException, InterruptedException {
//    CivilAirplane civilAirplane = new CivilAirplane(1, 100, 100);
//    Stopover civilAirport = new CivilAirport(new Coordinates(1, 1), 1);
//    Passenger passenger1 = mock(Passenger.class);
//
//    assertTrue(civilAirplane.passengerZone().accommodate(passenger));
//
//    civilAirport.vehicleMaintenance(civilAirplane);
//    assertTrue(civilAirplane.passengerZone().getPassengers().isEmpty());
//  }
}
