package vehicles;

import com.google.common.collect.Lists;
import core.Coordinates;
import stopovers.CivilDestination;
import stopovers.InvalidVehicleAtStopoverException;
import stopovers.Stopover;
import world.StopoverNotFoundInStopoverNetworkException;
import world.WorldClockListener;
import world.WorldMap;
import java.util.List;

public abstract class Vehicle extends WorldClockListener {
  private final int id;
  private final double velocity;
  protected WorldMap worldMap;
  protected List<Stopover> route;
  protected int previousStopoverNumber;

  public Vehicle(double velocity) {
    this.id = this.hashCode();
    this.velocity = velocity;
  }

  public double getVelocity() {
    return velocity;
  }

  public void setWorldMap(WorldMap worldMap) {
    this.worldMap = worldMap;
  }

  public Coordinates getCoordinates() {
    return worldMap.getVehicleCoordinates(this);
  }

  public int getId() {
    return id;
  }

  public Stopover getNextStopover() {
    return route.get(previousStopoverNumber + 1);
  }

  public Stopover getPreviousStopover() {
    return route.get(previousStopoverNumber);
  }

  public CivilDestination getNextCivilDestination() {
    return null;
  }

  public void setRoute(List<Stopover> route) {
    previousStopoverNumber = 0;
    this.route = route;
  }

  public void updateRoute() {
    previousStopoverNumber++;
    if (previousStopoverNumber == route.size() - 1) {
      Lists.reverse(route);
      previousStopoverNumber = 0;
    }
  }

  @Override
  public void tick() throws InterruptedException {
    try {
      worldMap.moveVehicleTowardsTargetStopover(this, this.velocity);

      if (hasArrivedAtStopover(getNextStopover())) {
        Stopover stopover = getNextStopover();

        // try to get into a Stopover until successful
        while (!stopover.accommodateVehicle(this)) {}

        stopover.prepareVehicleForTravel(this);
        stopover.releaseVehicle(this);
      }

    } catch (StopoverNotFoundInStopoverNetworkException e) {
      System.err.println("Vehicle " + this + " tried to move to " + getNextStopover() + ", but it doesn't exist on the WorldMap");
      e.printStackTrace();
    } catch (InvalidVehicleAtStopoverException e) {
      System.err.println("Vehicle " + this + " tried to get accommodate at " + getNextStopover());
      e.printStackTrace();
    }
  }

  public boolean hasArrivedAtStopover(Stopover stopover) {
    Coordinates vehicleCoord = getCoordinates();
    Coordinates stopoverCoord = stopover.getCoordinates();
    return (vehicleCoord.getX() == stopoverCoord.getX() && vehicleCoord.getY() == stopoverCoord.getY());
  }
}
