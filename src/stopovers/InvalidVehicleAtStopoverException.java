package stopovers;


import vehicles.Vehicle;

/**
 * An exception that should be thrown when a vehicle tries to get accommodated at a stopover of incorrect type
 * @see Vehicle
 * @see Stopover
 */
public class InvalidVehicleAtStopoverException extends Exception {
  private Vehicle vehicle;

  private Stopover stopover;

  public InvalidVehicleAtStopoverException(Vehicle vehicle, Stopover stopover) {
    this.vehicle = vehicle;
    this.stopover = stopover;
  }

  public Stopover getStopover() {
    return stopover;
  }

  public Vehicle getVehicle() {
    return vehicle;
  }
}
