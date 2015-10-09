package destinations;

import core.Civil;
import core.Coordinates;
import core.Passenger;

public class CivilAirport extends Airport implements Civil {
  public CivilAirport(Coordinates coordinates, int vehicleCapacity) {
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
