package vehicles;

import com.google.common.collect.Lists;
import core.Coordinates;
import gui.Drawable;
import gui.Drawer;
import stopovers.CivilDestination;
import stopovers.InvalidVehicleAtStopoverException;
import stopovers.Stopover;
import world.StopoverNotFoundInStopoverNetworkException;
import world.WorldClockListener;
import world.WorldMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Vehicle extends WorldClockListener implements Drawable {
  private final int id;
  private final double velocity;
  protected WorldMap worldMap;
  protected List<Stopover> route;
  protected int previousStopoverNumber;
  private ReentrantLock processingRoute;

  public Vehicle(double velocity) {
    this.id = this.hashCode();
    this.velocity = velocity;
    this.processingRoute = new ReentrantLock();
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

  public double getBearing() {
    return getCoordinates().getBearing(getNextStopover().getCoordinates());
  }

  public int getId() {
    return id;
  }

  public Stopover getNextStopover() {
    processingRoute.lock();
    Stopover stopover =  route.get(previousStopoverNumber + 1);
    processingRoute.unlock();

    return stopover;
  }

  public Stopover getNextCivilStopover() {
    return (Stopover) getNextCivilDestination();
  }

  public Stopover getPreviousStopover() {
    processingRoute.lock();
    Stopover stopover = route.get(previousStopoverNumber);
    processingRoute.unlock();

    return stopover;
  }

  public CivilDestination getNextCivilDestination() {
    for (int i = previousStopoverNumber; i < route.size(); i++) {
      if (route.get(i) instanceof CivilDestination) {
        return (CivilDestination) route.get(i);
      }
    }

    return null;
  }

  public void setRoute(List<Stopover> route) {
    processingRoute.lock();
    previousStopoverNumber = 0;
    this.route = route;
    processingRoute.unlock();
  }

  public void updateRoute() {
    processingRoute.lock();
    previousStopoverNumber++;

    if (previousStopoverNumber == route.size() - 1) {
      route = Lists.reverse(route);
      previousStopoverNumber = 0;
    }
    processingRoute.unlock();
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

  public void draw(Drawer drawer) {
    drawer.drawVehicle(this);
  }
}
