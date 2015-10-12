package stopovers;


import vehicles.Vehicle;

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
