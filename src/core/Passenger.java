package core;

import stopovers.CivilDestination;
import stopovers.Stopover;
import world.StopoverNotFoundInStopoverNetworkException;
import world.WorldMap;

import static java.lang.Thread.sleep;

/**
 * Represents a single being that can be kept by CivilVehicle and CivilDestination via PassengerZone
 */
public class Passenger implements Runnable {
  private final String firstName;
  private final String lastName;
  private final int age;
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
  public Passenger(WorldMap map, String firstName, String lastName, int age, String PESEL, CivilDestination hometown) throws StopoverNotFoundInStopoverNetworkException {
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
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

  public int getAge() {
    return age;
  }

  public String getPESEL() {
    return PESEL;
  }

  public String getTripType() {
    switch (trip.getTripType()) {
      case BUSINESS:
        return "Business trip";
      default:
        return "Vacation";
    }
  }

  public String getHometown() {
    return ((Stopover) hometown).getName();
  }

  /**
   * Let the passenger know where he has arrived. A call to notify() on the passenger should follow.
   * @param passengerZone a PassengerZone to which the passenger has arrived
   */
  public void setArrivedAtPassengerZone(PassengerZone passengerZone) {
    arrivedAtPassengerZone = passengerZone;
  }

  /**
   *
   * @return an object implementing CivilDestination interface (also of Stopover class) that this passenger wants to stop at next
   */
  public CivilDestination getNextCivilDestination() {
    return trip.getNextCivilDestination();
  }

  /**
   *
   * @return an object implementing CivilDestination interface (also of Stopover class) that this passenger wanted to stop at previously
   */
  public CivilDestination getPreviousCivilDestination() {
    return trip.getPreviousCivilDestination();
  }

  /**
   *
   * @return an object of Stopover class (also implementing CivilDestination) that this passenger wants to stop at next
   */
  public Stopover getNextCivilStopover() {
    return (Stopover) getNextCivilDestination();
  }

  /**
   * Runs passenger's journey, passenger waits for a vehicle, keeps track of his journey, sleeps at a hotel at his trip's destination
   */
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

    if (arrivedAtDestination()) {
      // passenger arrived at his destination, do not go to the (air)port, check in at the hotel
      while(!trip.getTo().hotel().accommodate(this));
      sleep(trip.getWaitingTime());
      trip.getTo().hotel().removePassenger(this);
    }
    else if (arrivedAtHometown()) {
      // passenger ended his trip, generate a new random trip and accommodate at first passenger zone
      try {
        trip.randomize();
      } catch (StopoverNotFoundInStopoverNetworkException e) {
        e.printStackTrace();
      }
    }

    // try to get back into passenger zone until successful
    while (!arrivedAtPassengerZone.accommodate(this)) ;
  }

  private boolean arrivedAtHometown() {
    return arrivedAtPassengerZone == trip.getFrom().passengerZone();
  }

  private boolean arrivedAtDestination() {
    return arrivedAtPassengerZone == trip.getTo().passengerZone();
  }

  private void goByFootAsLongAsNecessary() throws InterruptedException {
    // no vehicle-based connection between different types of civil destinations, need to go by foot
    boolean stillHaveToWalk = true;

    do {
      if (getNextCivilDestination().getClass() != getPreviousCivilDestination().getClass()) {
        sleep(5000);

        // leave previous city
        getPreviousCivilDestination().passengerZone().removePassenger(this);

        // going, going...
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

