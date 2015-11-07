package world;

import core.Coordinates;
import stopovers.*;
import vehicles.Vehicle;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class WorldMap implements Serializable {
  private final StopoverNetwork stopoverNetwork;
  private final Map<Vehicle, Coordinates> vehicleCoordinates;
  private final ReentrantLock processingVehicle;
  private final double safetyRadius;

  private RouteGenerator routeGenerator;

  public WorldMap(StopoverNetwork stopoverNetwork) {
    this.stopoverNetwork = stopoverNetwork;
    this.vehicleCoordinates = new HashMap<>();
    processingVehicle = new ReentrantLock();
    routeGenerator = new RouteGenerator(this);
    safetyRadius = 12;
  }

  public List<Vehicle> getAllVehicles() {
    return new ArrayList<>(vehicleCoordinates.keySet());
  }

  public List<Stopover> getAllStopovers() {
    return stopoverNetwork.getAllStopovers();
  }

  public RouteGenerator getRouteGenerator() {
    return routeGenerator;
  }

  public List<Stopover> getNeighbouringStopovers(Stopover stopover) {
    return stopoverNetwork.getAllNeighbouringStopovers(stopover);
  }

  public CivilDestination getRandomCivilDestination() {
    List<Stopover> stopovers = new ArrayList<>();
    List<Class<? extends Stopover>> types = Arrays.asList(CivilAirport.class, Port.class);
    types.forEach(type -> stopovers.addAll(stopoverNetwork.getAllOfType(type)));
    return (CivilDestination) stopovers.get((int)Math.floor(Math.random() * stopovers.size()));
  }

  public CivilAirport getRandomCivilAirport() {
    return (CivilAirport) getRandomStopoverOfType(CivilAirport.class);
  }

  public MilitaryAirport getRandomMilitaryAirport() {
    return (MilitaryAirport) getRandomStopoverOfType(MilitaryAirport.class);
  }

  public Port getRandomPort() {
    return (Port) getRandomStopoverOfType(Port.class);
  }

  public CivilAirport findClosestCivilAirport(Stopover from) throws StopoverNotFoundInStopoverNetworkException {
    return (CivilAirport) findClosestStopoverOfType(from, CivilAirport.class);
  }

  public MilitaryAirport findClosestMilitaryAirport(Stopover from) throws StopoverNotFoundInStopoverNetworkException {
    return (MilitaryAirport) findClosestStopoverOfType(from, MilitaryAirport.class);
  }

  public Port findClosestPort(Stopover from) throws StopoverNotFoundInStopoverNetworkException {
    return (Port) findClosestStopoverOfType(from, Port.class);
  }

  public CivilAirport findClosestMetricallyCivilAirport(Stopover from) throws StopoverNotFoundInStopoverNetworkException {
    return (CivilAirport) findClosestMetricallyOfType(from, CivilAirport.class);
  }

  public MilitaryAirport findClosestMetricallyMilitaryAirport(Stopover from) throws StopoverNotFoundInStopoverNetworkException {
    return (MilitaryAirport) findClosestMetricallyOfType(from, MilitaryAirport.class);
  }

  public Port findClosestMetricallyPort(Stopover from) throws StopoverNotFoundInStopoverNetworkException {
    return (Port) findClosestMetricallyOfType(from, Port.class);
  }

  public List<Junction> findJunctionsBetween(Stopover stopover1, Stopover stopover2) throws StopoverNotFoundInStopoverNetworkException {
    return stopoverNetwork.findJunctionsBetween(stopover1, stopover2);
  }

  public List<CivilDestination> findCivilRouteBetween(CivilDestination stopover1, CivilDestination stopover2) throws StopoverNotFoundInStopoverNetworkException {
    return stopoverNetwork.findCivilRouteBetween(stopover1, stopover2);
  }

  public void registerVehicle(Vehicle vehicle, Coordinates coordinates) {
    processingVehicle.lock();
    vehicle.setWorldMap(this);

    // it is very important to clone the coordinates, otherwise vehicle and stopover will share the object and the stopover will move along with the vehicle
    vehicleCoordinates.put(vehicle, new Coordinates(coordinates));
    processingVehicle.unlock();
  }

  public Junction getAdjecentJunction(Stopover stopover) throws StopoverNotFoundInStopoverNetworkException {
    Junction randomAdjecentJunction;

    List<Junction> adjecentJunctions = new ArrayList<>();

    for (Stopover neighbour : stopoverNetwork.getAllNeighbouringStopovers(stopover)) {
      if (neighbour instanceof Junction) {
        adjecentJunctions.add((Junction) neighbour);
      }
    }

    randomAdjecentJunction = adjecentJunctions.get((int) Math.floor(Math.random() * adjecentJunctions.size()));

    return randomAdjecentJunction;
  }

  /**
   *
   * @param vehicle
   * @return a reference to the vehicle's Coordinate object
   */
  public Coordinates getVehicleCoordinates(Vehicle vehicle) {
    processingVehicle.lock();
    Coordinates coordinates = vehicleCoordinates.get(vehicle);
    processingVehicle.unlock();
    return coordinates;
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

  protected void removeVehicle(Vehicle vehicle) {
    processingVehicle.lock();
    Stopover stopover = getVehicleCurrentStopover(vehicle);
    if (stopover != null) {
      stopover.releaseVehicle(vehicle);
    }

    vehicleCoordinates.remove(vehicle);
    processingVehicle.unlock();
  }

  protected Stopover findClosestStopoverOfType(Stopover from, Class<? extends Stopover> destinationType) throws StopoverNotFoundInStopoverNetworkException {
    return stopoverNetwork.findClosestConnectedOfType(from, destinationType);
  }

  protected Stopover findClosestMetricallyOfType(Stopover from, Class<? extends Stopover> type) throws StopoverNotFoundInStopoverNetworkException {
    return stopoverNetwork.findClosestMetricallyOfType(from, type);
  }

  protected Stopover getRandomStopoverOfType(Class<? extends Stopover> type) {
    List<Stopover> stopovers = stopoverNetwork.getAllOfType(type);
    return stopovers.get((int)Math.floor(Math.random() * stopovers.size()));
  }

  private boolean canVehicleMove(Vehicle vehicleToMove) {
    return !vehicleCoordinates.keySet().stream().anyMatch(otherVehicle ->
      areVehiclesGoingFromAndToSameStopovers(vehicleToMove, otherVehicle)
      && isVehicleBehindOtherVehicle(vehicleToMove, otherVehicle)
      && areVehiclesTooClose(vehicleToMove, otherVehicle));
  }

  private boolean areVehiclesTooClose(Vehicle vehicleMoving, Vehicle otherVehicle) {
    return vehicleMoving.getCoordinates().distanceTo(otherVehicle.getCoordinates()) < vehicleMoving.getVelocity() + safetyRadius;
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

  private Stopover getVehicleCurrentStopover(Vehicle vehicle) {
    List<Stopover> stopovers = stopoverNetwork.getAllStopovers();
    for (Stopover stopover : stopovers) {
      if (stopover.isAccommodatingVehicle(vehicle)) {
        return stopover;
      }
    }

    return null;
  }
}
