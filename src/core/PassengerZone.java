package core;

import destinations.Destination;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class PassengerZone {
  private final ArrayList<Passenger> passengers;
  private final int capacity;
  private final ReentrantLock processingPassengers;

  public PassengerZone(int capacity) {
    passengers = new ArrayList<>();
    this.capacity = capacity;
    this.processingPassengers = new ReentrantLock();
  }

  private void removePassengers(List<Passenger> passengersToRemove) {
    passengersToRemove.forEach((passengerToRemove) -> {
      passengers.remove(passengerToRemove);
    });
  }

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

  public List<Passenger> getPassengers() {
    processingPassengers.lock();

    ArrayList<Passenger> passengersCopy = new ArrayList<>(passengers);

    processingPassengers.unlock();
    return passengersCopy;
  }

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

  public void moveAllWithMatchingDestinationTo(PassengerZone target, Destination destination) {
    processingPassengers.lock();
    List<Passenger> movedPassengers = new ArrayList<>();

    passengers.forEach((passenger) -> {
      Destination nxt = passenger.getNextDestination();
      if (passenger.getNextDestination() == destination) {
        if (target.accommodate(passenger)) {
          movedPassengers.add(passenger);
        }
      }
    });
    removePassengers(movedPassengers);

    processingPassengers.unlock();
  }
}
