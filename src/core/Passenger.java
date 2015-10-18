package core;

import stopovers.Stopover;
import world.WorldClockListener;

public class Passenger extends WorldClockListener {
  private final String firstName;

  private final String lastName;
  private final String PESEL;
  private Coordinates coordinaes;
  public Passenger(String firstName, String lastName, String PESEL, Coordinates coordinates){
    this.firstName = firstName;
    this.lastName = lastName;
    this.PESEL = PESEL;
    this.coordinaes = coordinates;
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

  public Coordinates getCoordinaes() {
    return coordinaes;
  }

  public void arriveAt(Stopover stopover) {

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
