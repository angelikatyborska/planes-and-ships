package vehicles;

import core.Coordinates;

public class Airplane extends Vehicle {
  private final double fuelCapacity;
  private final double fuelBurningRate = 1;
  private double fuel;
  private int personnel;

  public Airplane(Coordinates coordinates, double fuelCapacity) {
    super(coordinates);
    this.personnel = (int) Math.floor(Math.random() * 10 + 3);
    this.fuelCapacity = fuelCapacity;
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
}
