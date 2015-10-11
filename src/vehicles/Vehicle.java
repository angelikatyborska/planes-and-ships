package vehicles;

import core.Coordinates;
import core.IdGenerator;
import destinations.Destination;

public abstract class Vehicle {
  private Coordinates coordinates;
  private final int id;

  public Vehicle(Coordinates coordinates) {
    this.coordinates = coordinates;
    this.id = IdGenerator.getId();
  }

  public abstract void gotAccommodatedAt(Destination destination);

  public abstract void gotReleasedFrom(Destination destination);
}
