package core;

import stopovers.CivilDestination;
import stopovers.Stopover;
import world.StopoverNotFoundInStopoverNetworkException;
import world.WorldClockListener;
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
  private Stopover arrivedAt;

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
    this.arrivedAt = (Stopover) hometown;
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

  public void setArrivedAt(Stopover stopover) {
    arrivedAt = stopover;
  }

  public Stopover getArrivedAt() {
    return arrivedAt;
  }

  public CivilDestination getNextCivilDestination() {
    return trip.getNextCivilDestination();
  }

  public void run() {
    while(!Thread.currentThread().isInterrupted()) {
      synchronized (arrivedAt) {
        try {
          arrivedAt.wait();
          trip.checkpoint();
          if (arrivedAt == trip.getTo()) {
            sleep(trip.getWaitingTime());
          }
          else if (arrivedAt == trip.getFrom()) {
            try {
              trip.randomize();
            } catch (StopoverNotFoundInStopoverNetworkException e) {
              System.err.println("Passenger " + this + " tried to generate a new trip for himself");
              e.printStackTrace();
            }
          }
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    }
  }
}

