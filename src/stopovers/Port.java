package stopovers;

import core.Coordinates;
import core.PassengerZone;
import gui.canvas.Drawer;
import vehicles.*;

public class Port extends Stopover implements CivilDestination {
  private final PassengerZone passengerZone;
  private final PassengerZone hotel;

  public Port(Coordinates coordinates, int vehicleCapacity) {
    this("", coordinates, vehicleCapacity);
  }

  public Port(String name, Coordinates coordinates, int vehicleCapacity) {
    super(name, coordinates, vehicleCapacity);
    passengerZone = new PassengerZone(Integer.MAX_VALUE);
    hotel = new PassengerZone(Integer.MAX_VALUE);
  }

  public PassengerZone hotel() {
    return hotel;
  }

  public boolean accommodateCivilShip(CivilShip vehicle) throws InvalidVehicleAtStopoverException {
    return accommodate(vehicle);
  }

  public void prepareCivilShipForTravel(CivilShip vehicle) throws InterruptedException {
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
