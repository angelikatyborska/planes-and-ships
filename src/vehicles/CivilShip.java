package vehicles;

import core.Civil;
import core.Coordinates;
import core.Passenger;

public class CivilShip extends Ship implements Civil {
  public CivilShip(Coordinates coordinates) {
    super(coordinates);
  }

  @Override
  public int getPassengerCapacity() {
    return 0;
  }

  @Override
  public void accommodatePassenger(Passenger passenger) {

  }

  @Override
  public void movePassengerTo(Civil civilDestination) {

  }
}
