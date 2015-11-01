package vehicles;

import gui.canvas.Drawer;
import stopovers.Airport;

public abstract class Airplane extends Vehicle {
  private final double fuelCapacity;
  private final double fuelBurningRate = 1;
  private double fuel;
  private int personnel;
  private boolean shouldCrash;
  private boolean crashed;

  public Airplane(double velocity, double fuelCapacity) {
    super(velocity);
    this.personnel = (int) Math.floor(Math.random() * 10 + 3);
    this.fuelCapacity = fuelCapacity;
    this.fuel = fuelCapacity;
    shouldCrash = false;
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

  public void crashLanding() {
    shouldCrash = true;
  }


  public boolean isCrashed() {
    return crashed;
  }

  public void setCrashed(boolean crashed) {
    this.crashed = crashed;
  }

  public Airport getNextAirport() {
    for (int i = previousStopoverNumber + 1; i < route.size(); i++) {
      if (route.get(i) instanceof Airport) {
        return (Airport) route.get(i);
      }
    }

    return null;
  }

  public void vehicleMovedCallback(boolean didVehicleMove) {
    if (didVehicleMove) {
      burnFuel(this.fuelBurningRate * this.getVelocity());
    }
  }

  @Override
  public void draw(Drawer drawer) {
    drawer.drawAirplane(this);
  }

  public boolean isShouldCrash() {
    return shouldCrash;
  }
}
