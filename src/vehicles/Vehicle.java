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

import java.util.ArrayList;
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
    this.route = new ArrayList<>();
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
    synchronized (route) {
      return route.get(previousStopoverNumber + 1);
    }
  }

  public Stopover getNextCivilStopover() {
    return (Stopover) getNextCivilDestination();
  }

  public Stopover getPreviousStopover() {
    synchronized (route) {
      return route.get(previousStopoverNumber);
    }
  }

  public CivilDestination getNextCivilDestination() {
    synchronized (route) {
      for (int i = previousStopoverNumber; i < route.size(); i++) {
        if (route.get(i) instanceof CivilDestination) {
          return (CivilDestination) route.get(i);
        }
      }

      return null;
    }
  }

  public void setRoute(List<Stopover> route) {
    synchronized (route) {
      previousStopoverNumber = 0;
      this.route.clear();
      this.route.addAll(route);
    }
  }

  public void updateRoute() {
    synchronized (route) {
      previousStopoverNumber++;

      if (previousStopoverNumber == route.size() - 1) {
        route = Lists.reverse(route);
        previousStopoverNumber = 0;
      }
    }
  }

  public boolean hasArrivedAtStopover(Stopover stopover) {
    Coordinates vehicleCoord = getCoordinates();
    Coordinates stopoverCoord = stopover.getCoordinates();
    return (vehicleCoord.getX() == stopoverCoord.getX() && vehicleCoord.getY() == stopoverCoord.getY());
  }

  public abstract void arrivedAtStopover(Stopover stopover) throws InvalidVehicleAtStopoverException, InterruptedException;

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
