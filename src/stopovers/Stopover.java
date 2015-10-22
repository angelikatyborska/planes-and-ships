package stopovers;

import core.Coordinates;
import vehicles.*;

import java.util.ArrayList;
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

  public boolean accommodateVehicle(Vehicle vehicle) throws InvalidVehicleAtStopoverException {
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

  // TODO: show to the teacher - using casting, I have to define those dummy methods, so that when I have a reference to a Stopover that is in fact eg. CivilAirport, I can call CivilAirport's accommodateVehice with the argument type CivilAirplane
  public boolean accommodateVehicle(Airplane vehicle) throws InvalidVehicleAtStopoverException {
    return accommodateVehicle((Vehicle) vehicle);
  }

  public boolean accommodateVehicle(Ship vehicle) throws InvalidVehicleAtStopoverException {
    return accommodateVehicle((Vehicle) vehicle);
  }

  public boolean accommodateVehicle(CivilAirplane vehicle) throws InvalidVehicleAtStopoverException {
    return accommodateVehicle((Vehicle) vehicle);
  }

  public boolean accommodateVehicle(MilitaryAirplane vehicle) throws InvalidVehicleAtStopoverException {
    return accommodateVehicle((Vehicle) vehicle);
  }

  public boolean accommodateVehicle(CivilShip vehicle) throws InvalidVehicleAtStopoverException {
    return accommodateVehicle((Vehicle) vehicle);
  }

  public boolean accommodateVehicle(MilitaryShip vehicle) throws InvalidVehicleAtStopoverException {
    return accommodateVehicle((Vehicle) vehicle);
  }

  public boolean releaseVehicle(Vehicle vehicle) {
    boolean realisingSuccessful;
    processingVehicleLock.lock();

    if (isAccommodatingVehicle(vehicle)) {
      accommodatedVehicles.remove(vehicle);
      realisingSuccessful = true;
    }
    else {
      realisingSuccessful = false;
    }

    processingVehicleLock.unlock();
    return realisingSuccessful;
  }

  public boolean isAccommodatingVehicle(Vehicle vehicle) {
    return accommodatedVehicles.contains(vehicle);
  }

  // TODO: show to the teacher - using casting, twin methods just for overriding

  // TODO: rename this method to something more descriptive
  public void vehicleMaintenance(Vehicle vehicle) {
    vehicle.updateRoute();
  }

  public void vehicleMaintenance(Airplane vehicle) throws InterruptedException {
    vehicleMaintenance((Vehicle) vehicle);
  }

  public void vehicleMaintenance(Ship vehicle) throws InterruptedException {
    vehicleMaintenance((Vehicle) vehicle);
  }

  public void vehicleMaintenance(CivilAirplane vehicle) throws InterruptedException {
    vehicleMaintenance((Vehicle) vehicle);
  }

  public void vehicleMaintenance(MilitaryAirplane vehicle) throws InterruptedException {
    vehicleMaintenance((Vehicle) vehicle);
  }

  public void vehicleMaintenance(CivilShip vehicle) throws InterruptedException {
    vehicleMaintenance((Vehicle) vehicle);
  }

  public void vehicleMaintenance(MilitaryShip vehicle) {
    vehicleMaintenance((Vehicle) vehicle);
  }
}
