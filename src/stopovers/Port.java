package stopovers;

import core.Coordinates;
import core.PassengerZone;
import gui.Drawer;
import vehicles.CivilShip;
import vehicles.Vehicle;

public class Port extends Stopover implements CivilDestination {
  private final PassengerZone passengerZone;

  public Port(Coordinates coordinates, int vehicleCapacity) {
    super(coordinates, vehicleCapacity);
    passengerZone = new PassengerZone(Integer.MAX_VALUE);
  }

  public Port(String name, Coordinates coordinates, int vehicleCapacity) {
    super(name, coordinates, vehicleCapacity);
    passengerZone = new PassengerZone(Integer.MAX_VALUE);
  }

  public boolean accommodateVehicle(Vehicle vehicle) throws InvalidVehicleAtStopoverException {
    throw new InvalidVehicleAtStopoverException(vehicle, this);
  }

  public boolean accommodateVehicle(CivilShip civilShip) throws InvalidVehicleAtStopoverException {
    return super.accommodateVehicle((Vehicle) civilShip);
  }

  public void prepareVehicleForTravel(CivilShip vehicle) throws InterruptedException {
    super.prepareVehicleForTravel(vehicle);
    // "wake up" all passengers, "throw" them out of the vehicle and let them know where they are
    // it's up to the passenger whether he will get accommodated there or go to sleep
    vehicle.passengerZone().removePassengersWithSignal(passengerZone);

    // take all passengers waiting at the port on board
    passengerZone.moveAllWithMatchingDestinationTo(vehicle.passengerZone(), vehicle.getNextCivilDestination());
  }

  @Override
  public PassengerZone passengerZone() {
    return passengerZone;
  }

  @Override
  public void draw(Drawer drawer) {
    drawer.drawPort(this);
  }
}
