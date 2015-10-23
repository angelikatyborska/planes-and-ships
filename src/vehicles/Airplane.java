package vehicles;

import gui.WorldDrawer;
import stopovers.Airport;
import stopovers.InvalidVehicleAtStopoverException;
import stopovers.Stopover;

import static java.lang.Thread.sleep;

public abstract class Airplane extends Vehicle {
  private final double fuelCapacity;
  private final double fuelBurningRate = 1;
  private double fuel;
  private int personnel;

  public Airplane(double velocity, double fuelCapacity) {
    super(velocity);
    this.personnel = (int) Math.floor(Math.random() * 10 + 3);
    this.fuelCapacity = fuelCapacity;
    this.fuel = fuelCapacity;
  }

  public double getFuel() {
    return fuel;
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
  }

  public void crashLanding() {
    // should reroute to next nearest airport and disappear (it's damaged!) (what about passengers?)
  }

  @Override
  public void draw(WorldDrawer drawer) {
    drawer.draw(this);
  }

  @Override
  public void arrivedAtStopover(Stopover stopover) throws InvalidVehicleAtStopoverException, InterruptedException {
    while (!stopover.accommodateVehicle(this)) {}
    stopover.prepareVehicleForTravel(this);
    stopover.releaseVehicle(this);
  }
}
