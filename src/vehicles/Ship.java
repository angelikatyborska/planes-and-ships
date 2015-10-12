package vehicles;

import core.Coordinates;
import stopovers.Stopover;

public class Ship extends Vehicle {
  public Ship(Coordinates coordinates) {
    super(coordinates);
  }

  @Override
  public void gotAccommodatedAt(Stopover stopover) {

  }

  @Override
  public void gotReleasedFrom(Stopover stopover) {

  }
}
