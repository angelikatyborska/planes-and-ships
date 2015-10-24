package core;

import stopovers.CivilDestination;
import stopovers.Stopover;
import world.StopoverNotFoundInStopoverNetworkException;
import world.WorldMap;

import static java.lang.Thread.sleep;

/**
 * Represents a signle being that can be kept by CivilVehicle and CivilDestination via PassengerZone
 */
public class Passenger implements Runnable {
  private final String firstName;
  private final String lastName;
  private final String PESEL;
  private final CivilDestination hometown;
  private Trip trip;
  private PassengerZone arrivedAtPassengerZone;

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
    this.arrivedAtPassengerZone = hometown.passengerZone();
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

  public void setArrivedAtPassengerZone(PassengerZone passengerZone) {
    arrivedAtPassengerZone = passengerZone;
  }

  public CivilDestination getNextCivilDestination() {
    return trip.getNextCivilDestination();
  }

  public CivilDestination getPreviousCivilDestination() {
    return trip.getPreviousCivilDestination();
  }

  public Stopover getNextCivilStopover() {
    return (Stopover) getNextCivilDestination();
  }

  public void run() {
    while(!Thread.currentThread().isInterrupted()) {
      try {
        synchronized (this) {
          wait();

          // everything below also has to be synchronized on this passenger so that he does all of this stuff before he is signaled again
          handleArrivingSomewhere();
          goByFootAsLongAsNecessary();
        }
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }

  private void handleArrivingSomewhere() throws InterruptedException {
    trip.checkpoint(arrivedAtPassengerZone);

    if (arrivedAtPassengerZone == trip.getTo().passengerZone()) {
      System.out.println("passenger " + this + " arrived at his destination at " + ((Stopover) trip.getTo()).getCoordinates().toString());

      // passenger arrived at his destination, do not go to the (air)port, go "downtown" to do his business
      sleep(trip.getWaitingTime());
    }
    else if (arrivedAtPassengerZone == trip.getFrom().passengerZone()) {
      System.out.println("passenger " + this + " arrived home at " + ((Stopover) trip.getFrom()).getCoordinates().toString());
      // passenger ended his trip, generate a new random trip and accommodate at first passenger zone
      try {
        trip.randomize();
      } catch (StopoverNotFoundInStopoverNetworkException e) {
        System.out.println("Passenger " + this + " tried to generate a new trip for himself");
        e.printStackTrace();
      }
    }

    // try to get back into passenger zone until successful
    while (!arrivedAtPassengerZone.accommodate(this)) ;
  }

  private void goByFootAsLongAsNecessary() throws InterruptedException {
    // no vehicle-based connection between different types of civil destinations, need to go by foot
    boolean stillHaveToWalk = true;

    do {
      if (getNextCivilDestination().getClass() != getPreviousCivilDestination().getClass()) {
        // leave previous city
        getPreviousCivilDestination().passengerZone().removePassenger(this);

        // going, going...
        System.out.println("Passenger " + this + " is going by foot from " + ((Stopover) getPreviousCivilDestination()).getCoordinates().toString() + " to " + ((Stopover) getNextCivilDestination()).getCoordinates().toString());
        sleep(3000);

        // arrive at the next city
        arrivedAtPassengerZone = getNextCivilDestination().passengerZone();
        handleArrivingSomewhere();
      }
      else {
        stillHaveToWalk = false;
      }

    } while (stillHaveToWalk);
  }
}

