package vehicles;

import core.Coordinates;

public class Airplane extends Vehicle {
  private final int fuelCapacity;
  private int fuel;

  public Airplane(Coordinates coordinates, int fuelCapacity) {
    super(coordinates);
    this.fuelCapacity = fuelCapacity;
  }

  public void refillFuel() {
    fuel = fuelCapacity;
  }
}
