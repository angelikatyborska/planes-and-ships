package destinations;

import core.Coordinates;
import vehicles.Vehicle;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Destination {
  private final Coordinates coordinates;
  private final int vehicleCapacity;
  private ReentrantLock accommodatingVehicleLock = new ReentrantLock();

  private ArrayList<Vehicle> vehiclesStaying;

  public Destination(Coordinates coordinates, int vehicleCapacity) {
    this.coordinates = coordinates;
    this.vehicleCapacity = vehicleCapacity;
    this.vehiclesStaying = new ArrayList<>();
  }

  public Coordinates getCoordinates() {
    return coordinates;
  }

  public int getVehicleCapacity() {
    return vehicleCapacity;
  }

  public ArrayList<Vehicle> getVehiclesStaying() {
    return vehiclesStaying;
  }

  public boolean accommodateVehicle(Vehicle vehicle) {
    boolean accommodatingSuccessful;
    accommodatingVehicleLock.lock();

    if (vehiclesStaying.size() < vehicleCapacity) {
      vehiclesStaying.add(vehicle);
      accommodatingSuccessful = true;
    }
    else {
      accommodatingSuccessful = false;
    }

    accommodatingVehicleLock.unlock();
    return accommodatingSuccessful;
  }
}
