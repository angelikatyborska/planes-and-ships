package vehicles;

import gui.canvas.Drawer;
import stopovers.Airport;
import stopovers.Stopover;

/**
 * A vehicle that can fly and get accommodated at airports
 * @see Vehicle
 * @see Airport
 */
public abstract class Airplane extends Vehicle {
  private final double fuelCapacity;
  private final double fuelBurningRate = 1;
  private double fuel;
  private int personnel;
  private boolean shouldCrash;
  private boolean crashed;
  private Stopover crashAt;

  /**
   * @param velocity Vehicle with which to move the vehicle in pixels per WorldClock tick
   * @param fuelCapacity how much fuel can the airplane hold
   */
  public Airplane(double velocity, double fuelCapacity) {
    super(velocity);
    this.personnel = (int) Math.floor(Math.random() * 10 + 3);
    this.fuelCapacity = fuelCapacity;
    this.fuel = fuelCapacity;
    shouldCrash = false;
    crashed = false;
  }

  public int getFuel() {
    return (int) (fuel/fuelCapacity * 100);
  }

  public int getPersonnel() {
    return personnel;
  }

  public void refillFuel() {
    fuel = fuelCapacity;
  }

  public void burnFuel(double distance) {
    if (fuel > distance * fuelBurningRate) {
      fuel -= distance * fuelBurningRate;
    }
    else {
      fuel = 0;
    }
  }

  public boolean canMove() {
    return fuel > 0 && !crashed;
  }

  /**
   * Immediately fly to the nearest airport and stay there
   */
  public void crashLanding() {
    shouldCrash = true;
    crashAt = closestAirport();
  }

  public boolean isCrashed() {
    return crashed;
  }

  public void setCrashed(boolean crashed) {
    this.crashed = crashed;
  }

  public boolean shouldCrash() {
    return shouldCrash;
  }

  public void vehicleMovedCallback(boolean didVehicleMove) {
    if (didVehicleMove) {
      burnFuel(this.fuelBurningRate * this.getVelocity());
    }
  }

  @Override
  public Stopover getNextStopover() {
    synchronized (route) {
      if (shouldCrash()) {
        return crashAt;
      }
      return route.get(previousStopoverNumber + 1);
    }
  }

  @Override
  public void draw(Drawer drawer) {
    drawer.drawAirplane(this);
  }

  public Stopover closestAirport() {
    return worldMap.findClosestMetricallyAirport(getCoordinates());
  }
}
