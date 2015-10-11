package vehicles;

import core.Civil;
import core.Coordinates;
import core.Passenger;

import java.util.ArrayList;
import java.util.List;

public class CivilAirplane extends Airplane implements Civil {
  private ArrayList<Passenger> passengers;

  public CivilAirplane(Coordinates coordinates, int fuelCapacity) {
    super(coordinates, fuelCapacity);
  }

  @Override
  public List<Passenger> getPassengers() {
    return passengers;
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
