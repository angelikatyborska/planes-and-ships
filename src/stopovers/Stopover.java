package stopovers;

import core.Coordinates;
import gui.canvas.Drawable;
import gui.canvas.Drawer;
import vehicles.*;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Stopover represents a place where a vehicle can stop (get accommodated at).
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


  /**
   *
   * @param vehicle A vehicle to be accommodated at this stopover.
   * @return true if successful, throws an exception if CivilAirplane is not allowed at this stopover.
   * @throws InvalidVehicleAtStopoverException
   */
  public boolean accommodateCivilAirplane(CivilAirplane vehicle) throws InvalidVehicleAtStopoverException {
    throw new InvalidVehicleAtStopoverException(vehicle, this);
  }

  /**
   *
   * @param vehicle A vehicle to be accommodated at this stopover.
   * @return true if successful, throws an exception if MilitaryAirplane is not allowed at this stopover.
   * @throws InvalidVehicleAtStopoverException
   */
  public boolean accommodateMilitaryAirplane(MilitaryAirplane vehicle) throws InvalidVehicleAtStopoverException {
    throw new InvalidVehicleAtStopoverException(vehicle, this);
  }

  /**
   *
   * @param vehicle A vehicle to be accommodated at this stopover.
   * @return true if successful, throws an exception if CivilShip is not allowed at this stopover.
   * @throws InvalidVehicleAtStopoverException
   */
  public boolean accommodateCivilShip(CivilShip vehicle) throws InvalidVehicleAtStopoverException {
    throw new InvalidVehicleAtStopoverException(vehicle, this);
  }

  /**
   *
   * @param vehicle A vehicle to be accommodated at this stopover.
   * @return true if successful, throws an exception if MilitaryShip is not allowed at this stopover.
   * @throws InvalidVehicleAtStopoverException
   */
  public boolean accommodateMilitaryShip(MilitaryShip vehicle) throws InvalidVehicleAtStopoverException {
    throw new InvalidVehicleAtStopoverException(vehicle, this);
  }

  /**
   *
   * @param vehicle A vehicle to be released from this stopover.
   * @return true if vehicle was accommodated and now is released, false otherwise
   */
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

  /**
   *
   * @param vehicle
   * @return true if vehicle is currently accommodated at this stopver
   */
  public boolean isAccommodatingVehicle(Vehicle vehicle) {
    return accommodatedVehicles.contains(vehicle);
  }

  /**
   * Vehicle maintenance procedures necessary before the vehicle can be released and continue its journey.
   * @param vehicle the vehicle to be prepared for travel
   */
  public void prepareVehicleForTravel(Vehicle vehicle) {
    vehicle.updateRoute();
  }

  /**
   * Airplane maintenance procedures necessary before the vehicle can be released and continue its journey.
   * @param vehicle the vehicle to be prepared for travel
   */
  public void prepareAirplaneForTravel(Airplane vehicle) throws InterruptedException {
    prepareVehicleForTravel(vehicle);
  }

  /**
   * Ship maintenance procedures necessary before the vehicle can be released and continue its journey.
   * @param vehicle the vehicle to be prepared for travel
   */
  public void prepareShipForTravel(Ship vehicle) throws InterruptedException {
    prepareVehicleForTravel(vehicle);
  }

  /**
   * CivilAirplane maintenance procedures necessary before the vehicle can be released and continue its journey.
   * @param vehicle the vehicle to be prepared for travel
   */
  public void prepareCivilAirplaneForTravel(CivilAirplane vehicle) throws InterruptedException {
    prepareAirplaneForTravel(vehicle);
  }

  /**
   * MilitaryAirplane maintenance procedures necessary before the vehicle can be released and continue its journey.
   * @param vehicle the vehicle to be prepared for travel
   */
  public void prepareMilitaryAirplaneForTravel(MilitaryAirplane vehicle) throws InterruptedException {
    prepareAirplaneForTravel(vehicle);
  }

  /**
   * CivilVehicle maintenance procedures necessary before the vehicle can be released and continue its journey.
   * @param vehicle the vehicle to be prepared for travel
   */
  public void prepareCivilShipForTravel(CivilShip vehicle) throws InterruptedException {
    prepareShipForTravel(vehicle);
  }

  /**
   * MilitarySip maintenance procedures necessary before the vehicle can be released and continue its journey.
   * @param vehicle the vehicle to be prepared for travel
   */
  public void prepareMilitaryShipForTravel(MilitaryShip vehicle) throws InterruptedException {
    prepareShipForTravel(vehicle);
  }

  @Override
  public void draw(Drawer drawer) {
    drawer.drawStopover(this);
  }

  /**
   * Checks if this stopover has an empty spot and, if true, accommodates the vehicle
   * @param vehicle
   * @return accommodating successful
   * @throws InvalidVehicleAtStopoverException
   */
  protected boolean accommodate(Vehicle vehicle) throws InvalidVehicleAtStopoverException {
    boolean accommodatingSuccessful;
    processingVehicleLock.lock();

    if (isAccommodatingVehicle(vehicle)) {
      accommodatingSuccessful = true;
    }
    else if (accommodatedVehicles.size() < vehicleCapacity) {
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
