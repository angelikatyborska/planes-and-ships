package core;

import stopovers.CivilDestination;
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

  public void run() {
    while(!Thread.currentThread().isInterrupted()) {
      try {
        synchronized (this) {
          wait();
        }
        // TODO: this is no good, passenger doesn't teleport if it's the first thing he should do and
        // if the passenger is going to accommodate himself, he wont be able to react to it! (he won't be waiting for the signal at that moment)
        trip.checkpoint(arrivedAtPassengerZone);
        if (arrivedAtPassengerZone == trip.getTo().passengerZone()) {
          System.err.println("passenger arrived at his destination");
          trip.getTo().passengerZone().removePassenger(this);
          sleep(trip.getWaitingTime());
          while (!trip.getTo().passengerZone().accommodate(this));
          trip.checkpoint(trip.getTo().passengerZone());
        }
        else if (arrivedAtPassengerZone == trip.getFrom().passengerZone()) {
          System.err.println("passenger came back home");
          try {
            trip.randomize();
          } catch (StopoverNotFoundInStopoverNetworkException e) {
            System.err.println("Passenger " + this + " tried to generate a new trip for himself");
            e.printStackTrace();
          }
        }
        else if (trip.getNextCivilDestination().getClass() != trip.getPreviousCivilDestination().getClass()) {
          // teleport form port to airport or the other way around
          System.err.println("passenger is gonna teleport");
          arrivedAtPassengerZone.removePassenger(this);
          while (!getNextCivilDestination().passengerZone().accommodate(this));
          trip.checkpoint(getNextCivilDestination().passengerZone());
        }
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }
}

