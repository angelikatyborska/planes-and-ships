package stopovers;

import core.Coordinates;
import core.PassengerZone;
import gui.Drawer;
import vehicles.Airplane;
import vehicles.CivilAirplane;

public class CivilAirport extends Airport implements CivilDestination {
  private final PassengerZone passengerZone;

  public CivilAirport(Coordinates coordinates, int vehicleCapacity) {
    super(coordinates, vehicleCapacity);
    passengerZone = new PassengerZone(Integer.MAX_VALUE);
  }

  public CivilAirport(String name, Coordinates coordinates, int vehicleCapacity) {
    super(name,coordinates, vehicleCapacity);
    passengerZone = new PassengerZone(Integer.MAX_VALUE);
  }


  public boolean accommodateVehicle(Airplane airplane) throws InvalidVehicleAtStopoverException {
    throw new InvalidVehicleAtStopoverException(airplane, this);
  }

  public boolean accommodateVehicle(CivilAirplane civilAirplane) throws InvalidVehicleAtStopoverException {
    return super.accommodateVehicle((Airplane) civilAirplane);
  }

  public void prepareVehicleForTravel(CivilAirplane vehicle) throws InterruptedException {
    super.prepareVehicleForTravel((Airplane) vehicle);

    // "wake up" all passengers, "throw" them out of the vehicle and let them know where they are
    // it's up to the passenger whether he will get accommodated there or go to sleep
    vehicle.passengerZone().removePassengersWithSignal(passengerZone);


    // take all passengers waiting at the airport on board
    passengerZone.moveAllWithMatchingDestinationTo(vehicle.passengerZone(), vehicle.getNextCivilDestination());
  }

  @Override
  public PassengerZone passengerZone() {
    return passengerZone;
  }

  @Override
  public void draw(Drawer drawer) {
    drawer.drawCivilAirport(this);
  }
}
