package core;

import java.util.List;


public interface Civil {
  List<Passenger> getPassengers();
  boolean accommodatePassenger(Passenger passenger);
  void accommodateAllPassengers(List<Passenger> passengers);
  boolean movePassengerTo(Civil civilDestination);
  void moveAllPassengersTo(Civil civilDestination);
}
