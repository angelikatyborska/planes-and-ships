package stopovers;

import core.Coordinates;

public class Destination extends Stopover {

  // TODO: Destination should have PassengerZone and be a common class for civil Stopovers ????
  // keep in mind that vehicles have to have "getNextDestination" on which taking passengers is based
  public Destination(Coordinates coordinates, int vehicleCapacity) {
    super(coordinates, vehicleCapacity);
  }
}
