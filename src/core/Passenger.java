package core;

import stopovers.CivilDestination;
import stopovers.Stopover;
import world.StopoverNotFoundInStopoverNetworkException;
import world.WorldClockListener;
import world.WorldMap;

public class Passenger extends WorldClockListener {
  private final String firstName;
  private final String lastName;
  private final String PESEL;
  private final CivilDestination hometown;
  private Trip trip;

  public Passenger(WorldMap map, String firstName, String lastName, String PESEL, CivilDestination hometown) throws StopoverNotFoundInStopoverNetworkException {
    this.firstName = firstName;
    this.lastName = lastName;
    this.PESEL = PESEL;
    this.hometown = hometown;
    this.trip = new Trip(hometown, map);
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getPESEL() {
    return PESEL;
  }

  public Stopover getNextDestination() {
    return null;
  }

  @Override
  public void tick() {
    // while true
    // generate new random route
    // teleport to first location
    // wait till signal
    // if signal from route destination, leave destination and sleep for trip's time duration
    // if not, wait again
    // go back to destination
    // wait till signal
  }
}
