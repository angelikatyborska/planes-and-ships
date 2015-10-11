package vehicles;

import core.Coordinates;
import core.IdGenerator;
import destinations.Stopover;

public abstract class Vehicle {
  private Coordinates coordinates;
  private final int id;

  public Vehicle(Coordinates coordinates) {
    this.coordinates = coordinates;
    this.id = IdGenerator.getId();
  }

  public Stopover getNextDestination() {
    return null;
  }

  public abstract void gotAccommodatedAt(Stopover stopover);

  public abstract void gotReleasedFrom(Stopover stopover);
}
