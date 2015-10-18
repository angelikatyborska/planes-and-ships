package vehicles;

import core.Coordinates;
import stopovers.Airport;
import stopovers.Stopover;

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

  @Override
  public void gotAccommodatedAt(Stopover stopover) {
    if (stopover instanceof Airport) {
      refillFuel();
    }
  }

  @Override
  public void gotReleasedFrom(Stopover stopover) {

  }
}
