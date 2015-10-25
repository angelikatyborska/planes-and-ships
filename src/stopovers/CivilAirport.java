package stopovers;

import core.Coordinates;
import core.PassengerZone;
import gui.Drawer;
import vehicles.CivilAirplane;
import vehicles.MilitaryAirplane;

import static java.lang.Thread.sleep;

public class CivilAirport extends Airport implements CivilDestination {
  private final PassengerZone passengerZone;
  private final PassengerZone hotel;

  public CivilAirport(Coordinates coordinates, int vehicleCapacity) {
    this("", coordinates, vehicleCapacity);
  }

  public CivilAirport(String name, Coordinates coordinates, int vehicleCapacity) {
    super(name,coordinates, vehicleCapacity);
    passengerZone = new PassengerZone(Integer.MAX_VALUE);
    hotel = new PassengerZone(Integer.MAX_VALUE);
  }

  public PassengerZone hotel() {
    return hotel;
  }

  public boolean accommodateMilitaryAirplane(MilitaryAirplane vehicle) throws InvalidVehicleAtStopoverException {
    throw new InvalidVehicleAtStopoverException(vehicle, this);
  }

  public void prepareCivilAirplaneForTravel(CivilAirplane vehicle) throws InterruptedException {
    super.prepareCivilAirplaneForTravel(vehicle);

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
