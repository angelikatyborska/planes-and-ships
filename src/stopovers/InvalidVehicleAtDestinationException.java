package stopovers;


import vehicles.Vehicle;

public class InvalidVehicleAtDestinationException extends Exception {
  private Vehicle vehicle;

  private Stopover stopover;

  public InvalidVehicleAtDestinationException(Vehicle vehicle, Stopover stopover) {
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
