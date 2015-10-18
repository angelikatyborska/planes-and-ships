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

  public Destination getRandomDestinationOfType(List<Class<? extends Stopover>> destinationTypes) {
    List<Destination> destinations = stopoverNetwork.getAllDestinationsOfType(destinationTypes);
    return destinations.get((int)Math.floor(Math.random() * destinations.size()));
  }

  public Destination getRandomDestinationOfType(Stopover destinationType) {
    List list = new ArrayList<Class<? extends Stopover>>();
    list.add(destinationType);
    return getRandomDestinationOfType(list);
  }

  public Stopover findClosestDestinationOfMatchingType(Stopover from, Class<? extends Destination> destinationType) throws StopoverNotFoundInStopoverNetworkException {
    return stopoverNetwork.findClosestDestinationOfMatchingType(from, destinationType);
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
      return vehiclesGoingFromAndToSameStopovers(vehicleToMove, otherVehicle)
        && vehicleBehindOtherVehicle(vehicleToMove, otherVehicle)
        && vehiclesTooClose(vehicleToMove, otherVehicle);
    });
  }

  private boolean vehiclesTooClose(Vehicle vehicleMoving, Vehicle otherVehicle) {
    return vehicleMoving.getCoordinates().distanceTo(otherVehicle.getCoordinates()) > vehicleMoving.getVelocity() + safetyRadius;
  }

  private boolean vehicleBehindOtherVehicle(Vehicle vehicleMoving, Vehicle otherVehicle) {
    Coordinates a = vehicleMoving.getCoordinates();
    Stopover c = vehicleMoving.getNextStopover();
    Coordinates d = c.getCoordinates();
    double b = a.distanceTo(d);
    return vehicleMoving.getCoordinates().distanceTo(vehicleMoving.getNextStopover().getCoordinates())
      > otherVehicle.getCoordinates().distanceTo(otherVehicle.getNextStopover().getCoordinates());
  }

  private boolean vehiclesGoingFromAndToSameStopovers(Vehicle vehicleMoving, Vehicle otherVehicle) {
    return vehicleMoving.getNextStopover() == otherVehicle.getNextStopover()
      && vehicleMoving.getPreviousStopover() == otherVehicle.getPreviousStopover();
  }
}
