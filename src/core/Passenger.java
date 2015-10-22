package core;

import stopovers.CivilDestination;
import stopovers.Stopover;
import world.StopoverNotFoundInStopoverNetworkException;
import world.WorldClockListener;
import world.WorldMap;

/**
 * Represents a signle being that can be kept by CivilVehicle and CivilDestination via PassengerZone
 */
public class Passenger extends WorldClockListener {
  private final String firstName;
  private final String lastName;
  private final String PESEL;
  private final CivilDestination hometown;
  private Trip trip;

  /**
   *
   * @param map WorldMap object which will be used to generate passenger's trip
   * @param firstName first name
   * @param lastName last name
   * @param PESEL national identification number used in Poland
   * @param hometown CivilDestination which will be the starting point of passenger's every trip
   * @throws StopoverNotFoundInStopoverNetworkException
   * @see PassengerZone
   * @see stopovers.CivilDestination
   */
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

  public Stopover getNextCivilDestination() {
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
