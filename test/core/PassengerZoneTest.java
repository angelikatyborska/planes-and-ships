package core;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import stopovers.CivilAirport;
import org.junit.Test;
import stopovers.CivilDestination;
import stopovers.Stopover;
import world.StopoverNotFoundInStopoverNetworkException;
import world.WorldMap;

public class PassengerZoneTest {
  @Test
  public void shouldAccommodateWhenNotFull() {
    PassengerZone passengerZone = new PassengerZone(4);
    Passenger passenger = mock(Passenger.class);
    stub(passenger.getArrivedAt()).toReturn(mock(Stopover.class));

    assertTrue(passengerZone.accommodate(passenger));
  }

  @Test
  public void shouldNotAccommodateWhenFull() {
    PassengerZone passengerZone = new PassengerZone(0);
    Passenger passenger = mock(Passenger.class);
    stub(passenger.getArrivedAt()).toReturn(mock(Stopover.class));

    assertFalse(passengerZone.accommodate(passenger));
  }

  @Test
  public void shouldMoveAllPassengers() {
    PassengerZone passengerZone1 = new PassengerZone(4);
    PassengerZone passengerZone2 = new PassengerZone(4);

    Passenger passenger1 = mock(Passenger.class);
    Passenger passenger2 = mock(Passenger.class);
    Passenger passenger3 = mock(Passenger.class);
    stub(passenger1.getArrivedAt()).toReturn(mock(Stopover.class));
    stub(passenger2.getArrivedAt()).toReturn(mock(Stopover.class));
    stub(passenger3.getArrivedAt()).toReturn(mock(Stopover.class));

    passengerZone1.accommodate(passenger1);
    passengerZone1.accommodate(passenger2);
    passengerZone1.accommodate(passenger3);

    assertEquals(3, passengerZone1.getPassengers().size());
    assertEquals(0, passengerZone2.getPassengers().size());

    passengerZone1.moveAllTo(passengerZone2);

    assertEquals(0, passengerZone1.getPassengers().size());
    assertEquals(3, passengerZone2.getPassengers().size());
  }

  @Test
  public void shouldMovePassengersIfDestinationsMatch() {
    PassengerZone passengerZone1 = new PassengerZone(4);
    PassengerZone passengerZone2 = new PassengerZone(4);
    CivilAirport paris = new CivilAirport(new Coordinates(1,1) , 3);
    CivilAirport london = new CivilAirport(new Coordinates(1,1) , 3);

    Passenger passengerGoingToLondon = mock(Passenger.class);
    stub(passengerGoingToLondon.getNextCivilDestination()).toReturn(london);
    stub(passengerGoingToLondon.getArrivedAt()).toReturn(mock(Stopover.class));

    Passenger passengerGoingToParis = mock(Passenger.class);
    stub(passengerGoingToParis.getNextCivilDestination()).toReturn(paris);
    stub(passengerGoingToParis.getArrivedAt()).toReturn(mock(Stopover.class));

    passengerZone1.accommodate(passengerGoingToLondon);
    passengerZone1.accommodate(passengerGoingToParis);

    assertEquals(2, passengerZone1.getPassengers().size());
    assertEquals(0, passengerZone2.getPassengers().size());

    passengerZone1.moveAllWithMatchingDestinationTo(passengerZone2, london);

    assertEquals(1, passengerZone1.getPassengers().size());
    assertEquals(1, passengerZone2.getPassengers().size());
  }
}
