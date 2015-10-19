package world;

import core.Coordinates;
import stopovers.Destination;
import stopovers.Stopover;
import vehicles.Vehicle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class WorldMap {
  private final StopoverNetwork stopoverNetwork;
  private final Map<Vehicle, Coordinates> vehicles;
  private final ReentrantLock processingVehicle;
  private final double safetyRadius;

  public WorldMap(StopoverNetwork stopoverNetwork) {
    this.stopoverNetwork = stopoverNetwork;
    this.vehicles = new HashMap<>();
    processingVehicle = new ReentrantLock();
    safetyRadius = 3;
  }

  public Stopover getRandomStopoverOfType(Class<? extends Stopover> type) {
    List<Stopover> stopovers = stopoverNetwork.getAllOfType(type);
    return stopovers.get((int)Math.floor(Math.random() * stopovers.size()));
  }

  public Stopover getRandomStopoverOfType(List<Class<? extends Stopover>> types) {
    List<Stopover> stopovers = new ArrayList<>();
    types.forEach(type -> stopovers.addAll(stopoverNetwork.getAllOfType(type)));
    return stopovers.get((int)Math.floor(Math.random() * stopovers.size()));
  }

  public Stopover findClosestStopoverOfMatchingType(Stopover from, Class<? extends Destination> destinationType) throws StopoverNotFoundInStopoverNetworkException {
    return stopoverNetwork.findClosestConnectedOfType(from, destinationType);
  }

  public void registerVehicle(Vehicle vehicle, Coordinates coordinates) {
    vehicle.setWorldMap(this);
    vehicles.put(vehicle, coordinates);
  }

  public Coordinates getVehicleCoordinates(Vehicle vehicle) {
    return vehicles.get(vehicle);
  }

  public boolean moveVehicleTowardsTargetStopover(Vehicle vehicle, double velocity) throws StopoverNotFoundInStopoverNetworkException {
    processingVehicle.lock();

    boolean canMove = canVehicleMove(vehicle);
    if (canMove) {
      vehicle.getCoordinates().moveTowards(vehicle.getNextStopover().getCoordinates(), velocity);
    }

    processingVehicle.unlock();
    return canMove;
  }

  private boolean canVehicleMove(Vehicle vehicleToMove) {
    return !vehicles.keySet().stream().anyMatch(otherVehicle -> {
      return areVehiclesGoingFromAndToSameStopovers(vehicleToMove, otherVehicle)
        && isVehicleBehindOtherVehicle(vehicleToMove, otherVehicle)
        && areVehiclesTooClose(vehicleToMove, otherVehicle);
    });
  }

  private boolean areVehiclesTooClose(Vehicle vehicleMoving, Vehicle otherVehicle) {
    return vehicleMoving.getCoordinates().distanceTo(otherVehicle.getCoordinates()) > vehicleMoving.getVelocity() + safetyRadius;
  }

  private boolean isVehicleBehindOtherVehicle(Vehicle vehicleMoving, Vehicle otherVehicle) {
    Coordinates a = vehicleMoving.getCoordinates();
    Stopover c = vehicleMoving.getNextStopover();
    Coordinates d = c.getCoordinates();
    double b = a.distanceTo(d);
    return vehicleMoving.getCoordinates().distanceTo(vehicleMoving.getNextStopover().getCoordinates())
      > otherVehicle.getCoordinates().distanceTo(otherVehicle.getNextStopover().getCoordinates());
  }

  private boolean areVehiclesGoingFromAndToSameStopovers(Vehicle vehicleMoving, Vehicle otherVehicle) {
    return vehicleMoving.getNextStopover() == otherVehicle.getNextStopover()
      && vehicleMoving.getPreviousStopover() == otherVehicle.getPreviousStopover();
  }
}
