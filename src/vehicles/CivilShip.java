package vehicles;

import core.PassengerZone;
import gui.canvas.Drawer;
import stopovers.*;
import world.StopoverNotFoundInStopoverNetworkException;

import java.util.List;

/**
 * A ship that can move passengers around.
 * @see CivilVehicle
 * @see Ship
 * @see Port
 */
public class CivilShip extends Ship implements CivilVehicle {
  private final PassengerZone passengerZone;
  private final String company;

  public CivilShip(double velocity, int passengerCapacity, String company) {
    super(velocity);
    passengerZone = new PassengerZone(passengerCapacity);
    this.company = company;
  }

  public CivilShip(double velocity, int passengerCapacity) {
    this(velocity, passengerCapacity, "");
  }

  public CivilDestination getNextCivilDestination() {
    for (int i = previousStopoverNumber + 1; i < route.size(); i++) {
      if (route.get(i) instanceof CivilDestination) {
        return (CivilDestination) route.get(i);
      }
    }
    return null;
  }

  /**
   *
   * @return The name of the company that owns this civil ship
   */
  public String getCompany() {
    return company;
  }

  @Override
  public PassengerZone passengerZone() {
    return passengerZone;
  }

  @Override
  public void draw(Drawer drawer) {
    drawer.drawCivilShip(this);
  }

  @Override
  public void arrivedAtStopover(Stopover stopover) throws InvalidVehicleAtStopoverException, InterruptedException {
    if (stopover.accommodateCivilShip(this)) {
      stopover.prepareCivilShipForTravel(this);
      stopover.releaseVehicle(this);
    }
  }

  @Override
  public List<Stopover> newSubRoute() {
    try {
      return worldMap.getRouteGenerator().newRoute(route.get(previousStopoverNumber + 1), Port.class, 4);
    } catch (StopoverNotFoundInStopoverNetworkException e) {
      e.printStackTrace();
    }
    return null;
  }

  public Port getNextPort() {
    for (int i = previousStopoverNumber + 1; i < route.size(); i++) {
      if (route.get(i) instanceof Port) {
        return (Port) route.get(i);
      }
    }

    return null;
  }
}
