package vehicles;

import core.PassengerZone;
import gui.canvas.Drawer;
import stopovers.*;
import world.StopoverNotFoundInStopoverNetworkException;

import java.util.List;

/**
 * An airplane that can move passengers around.
 * @see Airplane
 * @see CivilVehicle
 * @see CivilAirport
 */
public class CivilAirplane extends Airplane implements CivilVehicle {
  private final PassengerZone passengerZone;

  public CivilAirplane(double velocity, int fuelCapacity, int passengerCapacity) {
    super(velocity, fuelCapacity);
    passengerZone = new PassengerZone(passengerCapacity);
  }

  public CivilDestination getNextCivilDestination() {
    synchronized (route) {
      for (int i = previousStopoverNumber + 1; i < route.size(); i++) {
        if (route.get(i) instanceof CivilDestination) {
          return (CivilDestination) route.get(i);
        }
      }
      return null;
    }
  }

  @Override
  public List<Stopover> newSubRoute() {
    synchronized (route) {
      try {
        return worldMap.getRouteGenerator().newRoute(route.get(previousStopoverNumber + 1), CivilAirport.class, 4);
      } catch (StopoverNotFoundInStopoverNetworkException e) {
        e.printStackTrace();
      }
      return null;
    }
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
    if (stopover.accommodateCivilAirplane(this)) {
      if (!shouldCrash() || stopover instanceof Junction) {
        stopover.prepareCivilAirplaneForTravel(this);
        stopover.releaseVehicle(this);
      } else {
        setCrashed(true);
      }
    }
  }

  @Override
  public Stopover getNextStopover() {
    synchronized (route) {
      if (shouldCrash()) {
        try {
          return worldMap.findClosestMetricallyCivilAirport(route.get(previousStopoverNumber + 1));
        } catch (StopoverNotFoundInStopoverNetworkException e) {
          e.printStackTrace();
        }
      }
      return route.get(previousStopoverNumber + 1);
    }
  }
}
