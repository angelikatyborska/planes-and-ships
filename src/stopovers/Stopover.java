package stopovers;

import core.Coordinates;
import gui.Drawable;
import gui.Drawer;
import vehicles.*;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Stopover represents a place where a vehicle can stop.
 * @see Vehicle
 */
public class Stopover implements Drawable {
  private final Coordinates coordinates;
  private final int vehicleCapacity;
  private final ReentrantLock processingVehicleLock;
  private final String name;

  private ArrayList<Vehicle> accommodatedVehicles;

  public Stopover(String name, Coordinates coordinates, int vehicleCapacity) {
    this.coordinates = coordinates;
    this.vehicleCapacity = vehicleCapacity;
    this.accommodatedVehicles = new ArrayList<>();
    this.processingVehicleLock = new ReentrantLock();
    this.name = name;
  }

  public Stopover(Coordinates coordinates, int vehicleCapacity) {
    this("", coordinates, vehicleCapacity);
  }

  public Coordinates getCoordinates() {
    return coordinates;
  }

  public String getName() {
    return name;
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

  // TODO: rename those methods with names suggesting which vehicle is getting accommodated, like "accommodateCivilAirplane"
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

  public void prepareVehicleForTravel(Vehicle vehicle) {
    vehicle.updateRoute();
  }

  public void prepareVehicleForTravel(Airplane vehicle) throws InterruptedException {
    prepareVehicleForTravel((Vehicle) vehicle);
  }

  public void prepareVehicleForTravel(Ship vehicle) throws InterruptedException {
    prepareVehicleForTravel((Vehicle) vehicle);
  }

  public void prepareVehicleForTravel(CivilAirplane vehicle) throws InterruptedException {
    prepareVehicleForTravel((Vehicle) vehicle);
  }

  public void prepareVehicleForTravel(MilitaryAirplane vehicle) throws InterruptedException {
    prepareVehicleForTravel((Vehicle) vehicle);
  }

  public void prepareVehicleForTravel(CivilShip vehicle) throws InterruptedException {
    prepareVehicleForTravel((Vehicle) vehicle);
  }

  public void prepareVehicleForTravel(MilitaryShip vehicle) {
    prepareVehicleForTravel((Vehicle) vehicle);
  }

  @Override
  public void draw(Drawer drawer) {
    drawer.drawStopover(this);
  }
}
