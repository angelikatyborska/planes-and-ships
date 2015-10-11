package destinations;

import core.Civil;
import core.Coordinates;
import core.Passenger;
import vehicles.CivilAirplane;
import vehicles.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class CivilAirport extends Airport implements Civil {
  private List<Passenger> passengers;

  public CivilAirport(Coordinates coordinates, int vehicleCapacity) {
    super(coordinates, vehicleCapacity);
    passengers = new ArrayList<>();
  }

  @Override
  public List<Passenger> getPassengers() {
    return passengers;
  }

  public ArrayList<Passenger> getPassengersTravellingTo(Destination destination) {
    ArrayList<Passenger> passengersTravellingToDestination = new ArrayList<>();

    for (Passenger passenger : passengers ) {
      if (passenger.getNextCivilDestination() == destination) {
        passengersTravellingToDestination.add(passenger);
      }
    }

    return passengersTravellingToDestination;
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

  @Override
  public boolean accommodateVehicle(Vehicle vehicle) throws InvalidVehicleAtDestinationException {
    if (vehicle instanceof CivilAirplane) {
      return super.accommodateVehicle(vehicle);
    }
    else {
      throw new InvalidVehicleAtDestinationException(vehicle, this);
    }
  }
}
