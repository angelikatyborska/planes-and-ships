package vehicles;

import core.Coordinates;
import destinations.Destination;

public class Ship extends Vehicle {
  public Ship(Coordinates coordinates) {
    super(coordinates);
  }

  @Override
  public void gotAccommodatedAt(Destination destination) {

  }

  @Override
  public void gotReleasedFrom(Destination destination) {

  }
}
