package vehicles;

import core.Coordinates;
import core.IdGenerator;

public class Vehicle {
  private Coordinates coordinates;
  private final int id;

  public Vehicle(Coordinates coordinates) {
    this.coordinates = coordinates;
    this.id = IdGenerator.getId();
  }
}
