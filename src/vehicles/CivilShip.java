package vehicles;

import core.Civil;
import core.Coordinates;
import core.Passenger;

import java.util.ArrayList;

public class CivilShip extends Ship implements Civil {
  public CivilShip(Coordinates coordinates) {
    super(coordinates);
  }

  @Override
  public int getPassengerCapacity() {
    return 0;
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
