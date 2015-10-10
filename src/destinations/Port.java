package destinations;

import core.Civil;
import core.Coordinates;
import core.Passenger;

import java.util.ArrayList;

public class Port extends Destination implements Civil {
  public Port(Coordinates coordinates, int vehicleCapacity) {
    super(coordinates, vehicleCapacity);
  }

  @Override
  public int getPassengerCapacity() {
    return Integer.MAX_VALUE;
  }

  @Override
  public ArrayList<Passenger> getPassengers() {
    return null;
  }

  @Override
  public void accommodatePassenger(Passenger passenger) {

  }

  @Override
  public void accommodateAllPassengers(ArrayList<Passenger> passengers) {

  }

  @Override
  public void movePassengerTo(Civil civilDestination) {

  }

  @Override
  public void moveAllPassengersTo(Civil civilDestination) {

  }
}
