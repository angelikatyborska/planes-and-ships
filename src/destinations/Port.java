package destinations;

import core.Civil;
import core.Coordinates;
import core.Passenger;

public class Port extends Destination implements Civil {
  public Port(Coordinates coordinates, int vehicleCapacity) {
    super(coordinates, vehicleCapacity);
  }

  @Override
  public int getPassengerCapacity() {
    return Integer.MAX_VALUE;
  }

  @Override
  public void accommodatePassenger(Passenger passenger) {

  }

  @Override
  public void movePassengerTo(Civil civilDestination) {

  }
}
