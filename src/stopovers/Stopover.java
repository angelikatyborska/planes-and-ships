package stopovers;

import core.Coordinates;
import vehicles.Vehicle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Stopover {
  private final Coordinates coordinates;
  private final int vehicleCapacity;
  private final ReentrantLock processingVehicleLock;

  private ArrayList<Vehicle> accommodatedVehicles;

  public Stopover(Coordinates coordinates, int vehicleCapacity) {
    this.coordinates = coordinates;
    this.vehicleCapacity = vehicleCapacity;
    this.accommodatedVehicles = new ArrayList<>();
    this.processingVehicleLock = new ReentrantLock();
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

  // TODO: refactor to use proper OOP instead of checking if is instance
  public boolean accommodateVehicle(Vehicle vehicle) throws InvalidVehicleAtStopoverException {
    boolean accommodatingSuccessful;
    processingVehicleLock.lock();

    if (allowedVehicles().stream().anyMatch(allowedVehicleType -> allowedVehicleType.isInstance(vehicle))) {
      if (accommodatedVehicles.size() < vehicleCapacity) {
        accommodatedVehicles.add(vehicle);
        vehicle.gotAccommodatedAt(this);
        accommodatingSuccessful = true;
      }
      else {
        accommodatingSuccessful = false;
      }
    }
    else {
      throw new InvalidVehicleAtStopoverException(vehicle, this);
    }

    processingVehicleLock.unlock();
    return accommodatingSuccessful;
  }

  public boolean releaseVehicle(Vehicle vehicle) {
    boolean realisingSuccessful;
    processingVehicleLock.lock();

    if (accommodatedVehicles.contains(vehicle)) {
      accommodatedVehicles.remove(vehicle);
      vehicle.gotReleasedFrom(this);
      realisingSuccessful = true;
    }
    else {
      realisingSuccessful = false;
    }

    processingVehicleLock.unlock();
    return realisingSuccessful;
  }

  protected List<Class<? extends Vehicle>> allowedVehicles(){
    return Arrays.asList(Vehicle.class);
  }
}
