package vehicles;

import com.google.common.collect.Lists;
import core.Coordinates;
import gui.Drawable;
import gui.WorldDrawer;
import stopovers.CivilDestination;
import stopovers.InvalidVehicleAtStopoverException;
import stopovers.Stopover;
import world.StopoverNotFoundInStopoverNetworkException;
import world.WorldClockListener;
import world.WorldMap;
import java.util.List;

public abstract class Vehicle extends WorldClockListener implements Drawable {
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
      route = Lists.reverse(route);
      previousStopoverNumber = 0;
    }
  }

  public boolean hasArrivedAtStopover(Stopover stopover) {
    Coordinates vehicleCoord = getCoordinates();
    Coordinates stopoverCoord = stopover.getCoordinates();
    return (vehicleCoord.getX() == stopoverCoord.getX() && vehicleCoord.getY() == stopoverCoord.getY());
  }

  // TODO: something about that below, it seems like awful design
  // every Vehicle has to copy this method because "this" needs to be a reference of specific type
  public void arrivedAtStopover(Stopover stopover) throws InvalidVehicleAtStopoverException, InterruptedException {
    while (!stopover.accommodateVehicle(this)) {}
    stopover.prepareVehicleForTravel(this);
    stopover.releaseVehicle(this);
  }

  @Override
  public void tick() throws InterruptedException {
    try {
      worldMap.moveVehicleTowardsTargetStopover(this, this.velocity);

      if (hasArrivedAtStopover(getNextStopover())) {
        Stopover stopover = getNextStopover();
        arrivedAtStopover(stopover);
      }

    } catch (StopoverNotFoundInStopoverNetworkException e) {
      System.err.println("Vehicle " + this + " tried to move to " + getNextStopover() + ", but it doesn't exist on the WorldMap");
      e.printStackTrace();
    } catch (InvalidVehicleAtStopoverException e) {
      System.err.println("Vehicle " + this + " tried to get accommodate at " + getNextStopover());
      e.printStackTrace();
    }
  }

  public void draw(WorldDrawer drawer) {
    drawer.draw(this);
  }
}
