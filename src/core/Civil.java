package core;

import java.util.ArrayList;

public interface Civil {
  int getPassengerCapacity();
  ArrayList<Passenger> getPassengers();
  void accommodatePassenger(Passenger passenger);
  void accommodateAllPassengers(ArrayList<Passenger> passengers);
  void movePassengerTo(Civil civilDestination);
  void moveAllPassengersTo(Civil civilDestination);
}
