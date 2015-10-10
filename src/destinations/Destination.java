package destinations;

import core.Coordinates;
import vehicles.Vehicle;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Destination {
  private final Coordinates coordinates;
  private final int vehicleCapacity;
  private ReentrantLock processingVehicleLock = new ReentrantLock();

  private ArrayList<Vehicle> accommodatedVehicles;

  public Destination(Coordinates coordinates, int vehicleCapacity) {
    this.coordinates = coordinates;
    this.vehicleCapacity = vehicleCapacity;
    this.accommodatedVehicles = new ArrayList<>();
  }

  public Coordinates getCoordinates() {
    return coordinates;
  }

  public int getVehicleCapacity() {
    return vehicleCapacity;
  }

  public ArrayList<Vehicle> getAccommodatedVehicles() {
    return accommodatedVehicles;
  }

  public boolean accommodateVehicle(Vehicle vehicle) {
    boolean accommodatingSuccessful;
    processingVehicleLock.lock();

    if (accommodatedVehicles.size() < vehicleCapacity) {
      accommodatedVehicles.add(vehicle);
      accommodatingSuccessful = true;
    }
    else {
      accommodatingSuccessful = false;
    }

    processingVehicleLock.unlock();
    return accommodatingSuccessful;
  }

  public boolean releaseVehicle(Vehicle vehicle) {
    boolean realisingSuccessful;
    processingVehicleLock.lock();

    if (accommodatedVehicles.contains(vehicle)) {
      accommodatedVehicles.remove(vehicle);
      realisingSuccessful = true;
    }
    else {
      realisingSuccessful = false;
    }

    processingVehicleLock.unlock();
    return realisingSuccessful;
  }
}
