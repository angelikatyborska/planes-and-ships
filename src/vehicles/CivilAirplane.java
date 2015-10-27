package vehicles;

import core.PassengerZone;
import gui.canvas.Drawer;
import stopovers.CivilAirport;
import stopovers.CivilDestination;
import stopovers.InvalidVehicleAtStopoverException;
import stopovers.Stopover;
import world.StopoverNotFoundInStopoverNetworkException;

import java.util.List;

public class CivilAirplane extends Airplane implements CivilVehicle {
  private final PassengerZone passengerZone;

  public CivilAirplane(double velocity, int fuelCapacity, int passengerCapacity) {
    super(velocity, fuelCapacity);
    passengerZone = new PassengerZone(passengerCapacity);
  }

  // TODO: show to the teacher - using instanceof
  public CivilDestination getNextCivilDestination() {
    for (int i = previousStopoverNumber + 1; i < route.size(); i++) {
      if (route.get(i) instanceof CivilDestination) {
        return (CivilDestination) route.get(i);
      }
    }
    return null;
  }

  @Override
  public List<Stopover> newSubRoute() {
    try {
      return worldMap.getRouteGenerator().newRoute(route.get(previousStopoverNumber + 1), CivilAirport.class, 4);
    } catch (StopoverNotFoundInStopoverNetworkException e) {
      e.printStackTrace();
    };
    return null;
  }

  @Override
  public PassengerZone passengerZone() {
    return passengerZone;
  }

  @Override
  public void draw(Drawer drawer) {
    drawer.drawCivilAirplane(this);
  }

  @Override
  public void arrivedAtStopover(Stopover stopover) throws InvalidVehicleAtStopoverException, InterruptedException {
    while (!stopover.accommodateCivilAirplane(this)) {}
    stopover.prepareCivilAirplaneForTravel(this);
    stopover.releaseVehicle(this);
  }
}
