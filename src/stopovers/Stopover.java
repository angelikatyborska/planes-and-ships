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

  // default: no one allowed, so that overriding means white-listing
  public boolean accommodateCivilAirplane(CivilAirplane vehicle) throws InvalidVehicleAtStopoverException {
    throw new InvalidVehicleAtStopoverException(vehicle, this);
  }

  public boolean accommodateMilitaryAirplane(MilitaryAirplane vehicle) throws InvalidVehicleAtStopoverException {
    throw new InvalidVehicleAtStopoverException(vehicle, this);
  }

  public boolean accommodateCivilShip(CivilShip vehicle) throws InvalidVehicleAtStopoverException {
    throw new InvalidVehicleAtStopoverException(vehicle, this);
  }

  public boolean accommodateMilitaryShip(MilitaryShip vehicle) throws InvalidVehicleAtStopoverException {
    throw new InvalidVehicleAtStopoverException(vehicle, this);
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

  public void prepareVehicleForTravel(Vehicle vehicle) {
    vehicle.updateRoute();
  }

  public void prepareAirplaneForTravel(Airplane vehicle) throws InterruptedException {
    prepareVehicleForTravel(vehicle);
  }

  public void prepareShipForTravel(Ship vehicle) throws InterruptedException {
    prepareVehicleForTravel(vehicle);
  }

  public void prepareCivilAirplaneForTravel(CivilAirplane vehicle) throws InterruptedException {
    prepareAirplaneForTravel(vehicle);
  }

  public void prepareMilitaryAirplaneForTravel(MilitaryAirplane vehicle) throws InterruptedException {
    prepareAirplaneForTravel(vehicle);
  }

  public void prepareCivilShipForTravel(CivilShip vehicle) throws InterruptedException {
    prepareShipForTravel(vehicle);
  }

  public void prepareMilitaryShipForTravel(MilitaryShip vehicle) throws InterruptedException {
    prepareShipForTravel(vehicle);
  }

  @Override
  public void draw(Drawer drawer) {
    drawer.drawStopover(this);
  }

  protected boolean accommodate(Vehicle vehicle) throws InvalidVehicleAtStopoverException {
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
}
