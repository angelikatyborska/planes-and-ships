package destinations;

import core.Civil;
import core.Coordinates;
import core.Passenger;
import vehicles.Airplane;
import vehicles.CivilAirplane;

import java.util.ArrayList;
import java.util.List;

public class CivilAirport extends Airport implements Civil {
  private ArrayList<Passenger> passengers;

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

  public boolean accommodateVehicle(Airplane airplane) throws InvalidVehicleAtDestinationException {
    throw new InvalidVehicleAtDestinationException(airplane, this);
  }

  public boolean accommodateVehicle(CivilAirplane airplane) throws InvalidVehicleAtDestinationException {
    return super.accommodateVehicle(airplane);
  }
}
