package core;
import static org.junit.Assert.*;

import destinations.Destination;
import org.junit.Test;
import org.mockito.Mockito;

public class PassengerZoneTest {
  @Test
  public void shouldAccommodateWhenNotFull() {
    PassengerZone passengerZone = new PassengerZone(4);
    Passenger passenger = Mockito.mock(Passenger.class);

    assertTrue(passengerZone.accommodate(passenger));
  }

  @Test
  public void shouldNotAccommodateWhenFull() {
    PassengerZone passengerZone = new PassengerZone(0);
    Passenger passenger = Mockito.mock(Passenger.class);

    assertFalse(passengerZone.accommodate(passenger));
  }

  @Test
  public void shouldMoveAllPassengers() {
    PassengerZone passengerZone1 = new PassengerZone(4);
    PassengerZone passengerZone2 = new PassengerZone(4);

    Passenger passenger1 = Mockito.mock(Passenger.class);
    Passenger passenger2 = Mockito.mock(Passenger.class);
    Passenger passenger3 = Mockito.mock(Passenger.class);

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
    Destination paris = new Destination(new Coordinates(1,1) , 3);
    Destination london = new Destination(new Coordinates(1,1) , 3);

    Passenger passengerGoingToLondon = Mockito.mock(Passenger.class);
    Mockito.when(passengerGoingToLondon.getNextDestination()).thenReturn(london);
    Passenger passengerGoingToParis = Mockito.mock(Passenger.class);
    Mockito.when(passengerGoingToParis.getNextDestination()).thenReturn(paris);

    passengerZone1.accommodate(passengerGoingToLondon);
    passengerZone1.accommodate(passengerGoingToParis);

    assertEquals(2, passengerZone1.getPassengers().size());
    assertEquals(0, passengerZone2.getPassengers().size());

    passengerZone1.moveAllWithMatchingDestinationTo(passengerZone2, london);

    assertEquals(1, passengerZone1.getPassengers().size());
    assertEquals(1, passengerZone2.getPassengers().size());
  }
}
