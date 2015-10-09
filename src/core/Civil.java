package core;

public interface Civil {
  int getPassengerCapacity();
  void accommodatePassenger(Passenger passenger);
  void movePassengerTo(Civil civilDestination);
}
