package vehicles;

import core.Civil;
import core.Coordinates;
import core.Passenger;

import java.util.ArrayList;

public class CivilAirplane extends Airplane implements Civil {
  public CivilAirplane(Coordinates coordinates, int fuelCapacity) {
    super(coordinates, fuelCapacity);
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
