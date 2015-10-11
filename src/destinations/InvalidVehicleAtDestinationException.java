package destinations;


import vehicles.Vehicle;

public class InvalidVehicleAtDestinationException extends Exception {
  private Vehicle vehicle;

  private Destination destination;

  public InvalidVehicleAtDestinationException(Vehicle vehicle, Destination destination) {
    this.vehicle = vehicle;
    this.destination = destination;
  }

  public Destination getDestination() {
    return destination;
  }

  public Vehicle getVehicle() {
    return vehicle;
  }
}
