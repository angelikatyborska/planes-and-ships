package vehicles;

import core.Civil;
import core.Coordinates;
import core.Passenger;

import java.util.ArrayList;
import java.util.List;

public class CivilShip extends Ship implements Civil {
  public CivilShip(Coordinates coordinates) {
    super(coordinates);
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
