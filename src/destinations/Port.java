package destinations;

import core.Civil;
import core.Coordinates;
import core.Passenger;

import java.util.ArrayList;
import java.util.List;

public class Port extends Destination implements Civil {
  public Port(Coordinates coordinates, int vehicleCapacity) {
    super(coordinates, vehicleCapacity);
  }

  @Override
  public List<Passenger> getPassengers() {
    return null;
  }

  @Override
  public boolean accommodatePassenger(Passenger passenger) {
    return false;
  }

  @Override
  public void accommodateAllPassengers(List<Passenger> passengers) {

  }

  @Override
  public boolean movePassengerTo(Civil civilDestination) {
    return false;
  }

  @Override
  public void moveAllPassengersTo(Civil civilDestination) {

  }
}
