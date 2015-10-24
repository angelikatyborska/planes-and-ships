package core;

import stopovers.CivilDestination;

import java.util.ArrayList;
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

  /**
   * Removes a passenger from itself
   * @param passenger
   */
  public void removePassenger(Passenger passenger) {
    processingPassengers.lock();
    passengers.remove(passenger);
    processingPassengers.unlock();
  }

  /**
   * Removes all passengers from this PassengerZone letting them know that they arrived at destination PassengerZone
   * @param destination PassengerZone to which passenger will be signaled to go next
   */
  public void removePassengersWithSignal(PassengerZone destination) {
    processingPassengers.lock();
    List<Passenger> passengersToRemove = new ArrayList<>();

    for (Passenger passenger : passengers) {
      passengersToRemove.add(passenger);
    }

    for (Passenger passenger : passengersToRemove) {
      passengers.remove(passenger);

      synchronized (passenger) {
        passenger.setArrivedAtPassengerZone(destination);
        passenger.notify();
      }
    }

    processingPassengers.unlock();
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
    List<Passenger> copy = new ArrayList<>(passengers);
    processingPassengers.unlock();
    return copy;
  }

  public List<Passenger> getPassengersUnsafe() {
    return passengers;
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

  private void removePassengers(List<Passenger> passengersToRemove) {
    passengersToRemove.forEach(passengers::remove);
  }
}
