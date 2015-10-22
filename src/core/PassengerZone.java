package core;

import stopovers.CivilDestination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Represents a place that can keep Passengers, like a waiting room on the airport or passenger seating area on the airplane
 * @see stopovers.CivilDestination
 * @see vehicles.CivilVehicle
 */
public class PassengerZone {
  private final ArrayList<Passenger> passengers;
  private final int capacity;
  private final ReentrantLock processingPassengers;

  /**
   *
   * @param capacity How many passengers can be accommodated at this PassengerZone
   */
  public PassengerZone(int capacity) {
    passengers = new ArrayList<>();
    this.capacity = capacity;
    this.processingPassengers = new ReentrantLock();
  }

  public int getCapacity() {
    return capacity;
  }

  private void removePassengers(List<Passenger> passengersToRemove) {
    passengersToRemove.forEach(passengers::remove);
  }

  /**
   * Tries to accommodate a passenger
   * @param passenger
   * @return true if successful, false if PassengerZone is already full and can't accommodate
   */
  public boolean accommodate(Passenger passenger) {
    boolean successful = false;
    processingPassengers.lock();

    if (capacity > passengers.size()) {
      passengers.add(passenger);

      synchronized (passenger.getArrivedAt()) {
        passenger.getArrivedAt().notify();
      }

      successful =  true;
    }

    processingPassengers.unlock();
    return successful;
  }

  /**
   *
   * @return a list of all currently accommodates passengers
   */
  public List<Passenger> getPassengers() {
    processingPassengers.lock();

    ArrayList<Passenger> passengersCopy = new ArrayList<>(passengers);

    processingPassengers.unlock();
    return passengersCopy;
  }

  /**
   * Tries to accommodate all passengers at target PassengerZone
   * @param target a PassengerZone to which passengers will be moved
   */
  public void moveAllTo(PassengerZone target) {
    processingPassengers.lock();
    List<Passenger> movedPassengers = new ArrayList<>();

    passengers.forEach((passenger) -> {
      if (target.accommodate(passenger)) {
        movedPassengers.add(passenger);
      }
    });
    removePassengers(movedPassengers);

    processingPassengers.unlock();
  }

  /**
   * Tries to accommodate all passengers that are travelling to certain destination at target PassengerZone
   * @param target a PassengerZone to which passengers will be moved
   * @param destination a CiviDestination which will have to match passenger's next destination if he is to be moved to target PassengerZone
   */
  public void moveAllWithMatchingDestinationTo(PassengerZone target, CivilDestination destination) {
    processingPassengers.lock();
    List<Passenger> passengerToMove = new ArrayList<>();
    List<Passenger> movedPassengers = new ArrayList<>();

    passengers.forEach((passenger) -> {
      if (passenger.getNextCivilDestination() == destination) {
        if (target.accommodate(passenger)) {
          movedPassengers.add(passenger);
        }
      }
    });
    removePassengers(movedPassengers);

    processingPassengers.unlock();
  }
}
