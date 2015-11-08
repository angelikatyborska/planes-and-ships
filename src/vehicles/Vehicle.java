package vehicles;

import com.google.common.collect.Lists;
import core.Coordinates;
import gui.canvas.Drawable;
import gui.canvas.Drawer;
import stopovers.CivilDestination;
import stopovers.InvalidVehicleAtStopoverException;
import stopovers.Junction;
import stopovers.Stopover;
import world.StopoverNotFoundInStopoverNetworkException;
import world.WorldClockListener;
import world.WorldMap;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A vehicle can travel between and get accommodated at stopovers.
 * @see Stopover
 */
public abstract class Vehicle extends WorldClockListener implements Drawable {
  private final int id;
  private final double velocity;
  protected WorldMap worldMap;
  protected final List<Stopover> route;
  protected int previousStopoverNumber;

  /**
   *
   * @param velocity Vehicle with which to move the vehicle in pixels per WorldClock tick
   */
  public Vehicle(double velocity) {
    this.id = this.hashCode();
    this.velocity = velocity;
    this.route = new ArrayList<>();
  }

  public double getVelocity() {
    return velocity;
  }

  /**
   *
   * @param worldMap The map on which the vehicle moves
   */
  public void setWorldMap(WorldMap worldMap) {
    this.worldMap = worldMap;
  }

  public Coordinates getCoordinates() {
      return worldMap.getVehicleCoordinates(this);
  }

  /**
   *
   * @return An angle in radians formed by the 0Y axis and the segment formed with vehicle coordinates and vehicle's next destination coordinates
   */
  public double getBearing() {
    return getCoordinates().getBearing(getNextStopover().getCoordinates());
  }

  public int getId() {
    return id;
  }

  /**
   *
   * @return A comma-separated list of all vehicle's stopovers except for junctions
   */
  public List<String> routeToStrings() {
    synchronized (route) {
      return route.stream().filter(stopover -> !(stopover instanceof Junction)).map(Stopover::getName).collect(Collectors.toList());
    }
  }

  /**
   *
   * @return The next stopover at which the vehicle will stop
   */
  public Stopover getNextStopover() {
    synchronized (route) {
      try {
        return route.get(previousStopoverNumber + 1);
      }
      catch (IndexOutOfBoundsException e){
        e.printStackTrace();
        System.err.println("previousStopoverNumber = " + previousStopoverNumber);
        route.forEach(stopover -> System.err.println(stopover.getName()));
      }

      return null;
    }
  }

  /**
   *
   * @return The next stopover implementing CivilDestination at which the vehicle will stop
   */
  public Stopover getNextCivilStopover() {
    return (Stopover) getNextCivilDestination();
  }

  /**
   *
   * @return The previous stopover at which the vehicle stopped
   */
  public Stopover getPreviousStopover() {
    synchronized (route) {
      return route.get(previousStopoverNumber);
    }
  }

  /**
   *
   * @return The next civil destination at which the vehicle will stop
   */
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

  /**
   * Replaces vehicle's route with the given route
   * @param newRoute
   */
  public void setRoute(List<Stopover> newRoute) {
    synchronized (route) {
      previousStopoverNumber = 0;
      this.route.clear();
      this.route.addAll(newRoute);
    }
  }

  /**
   * Changes vehicle's next stopover to be vehicle's previous stopover
   */
  public void updateRoute() {
    synchronized (route) {
      previousStopoverNumber++;

      if (previousStopoverNumber == route.size() - 1) {
        List<Stopover> reversedRoute = new ArrayList<>();

        for (int i = route.size() - 1; i >= 0; i--) {
          reversedRoute.add(route.get(i));
        }

        route.clear();
        route.addAll(reversedRoute);
        previousStopoverNumber = 0;
      }
    }
  }

  /**
   * Randomize vehicle's route in such a manner that previous and next stopover stays the same to avoid inconsistency between vehicle's current coordinates and it's next stopover
   */
  public void randomizeCurrentRoute() {
      List<Stopover> newRoute = new ArrayList<>();

    synchronized (route) {
      // new route must start with previous and next stopover from current route
      newRoute.add(route.get(previousStopoverNumber));
      newRoute.addAll(newSubRoute());
    }

    setRoute(newRoute);
  }

  protected abstract List<Stopover> newSubRoute();

  /**
   * checks if vehicle's coordinates identical with the given stopover's coordinates
   * @param stopover
   * @return
   */
  public boolean hasArrivedAtStopover(Stopover stopover) {
    Coordinates vehicleCoord = getCoordinates();
    Coordinates stopoverCoord = stopover.getCoordinates();
    return (vehicleCoord.getX() == stopoverCoord.getX() && vehicleCoord.getY() == stopoverCoord.getY());
  }

  /**
   * Gets called if the vehicle arrived at its next stopover while trying to move
   * @param stopover
   * @throws InvalidVehicleAtStopoverException
   * @throws InterruptedException
   */
  public abstract void arrivedAtStopover(Stopover stopover) throws InvalidVehicleAtStopoverException, InterruptedException;

  /**
   * Gets called if the vehicle changed its coordinates while trying to move
   * @param didVehicleMove
   */
  public void vehicleMovedCallback(boolean didVehicleMove) { }

  /**
   * Determines weather the vehicle will move when the WorldClock ticks
   * @return
   */
  public boolean canMove() {
    return true;
  }

  @Override
  public void tick() throws InterruptedException {
    synchronized (this) {
      try {
        if (canMove()) {
          boolean didVehicleMove = worldMap.moveVehicleTowardsTargetStopover(this, this.velocity);
          vehicleMovedCallback(didVehicleMove);

          if (hasArrivedAtStopover(getNextStopover())) {
            Stopover stopover = getNextStopover();
            arrivedAtStopover(stopover);
          }
        }

      } catch (StopoverNotFoundInStopoverNetworkException e) {
        System.err.println("Vehicle " + this + " tried to move to " + getNextStopover() + ", but it doesn't exist on the WorldMap");
        e.printStackTrace();
      } catch (InvalidVehicleAtStopoverException e) {
        System.err.println("Vehicle " + this + " tried to get accommodate at " + getNextStopover());
        e.printStackTrace();
      }
    }
  }

  public void draw(Drawer drawer) {
    drawer.drawVehicle(this);
  }
}
