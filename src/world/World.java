package world;

import core.Coordinates;
import core.Passenger;
import core.PassengerGenerator;
import stopovers.*;
import vehicles.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * Keeps track of all the vehicles and passengers that move on its map
 */
public class World implements Serializable {
  private WorldClock clock;
  private WorldMap map;
  private PassengerGenerator passengerGenerator;
  private VehicleGenerator vehicleGenerator;
  private transient HashMap<Runnable, Thread> threads;
  private List<Runnable> runnables;

  public World(WorldClock clock, WorldMap map) {
    threads = new HashMap<>();
    this.clock = clock;
    threads.put(this.clock, new Thread(this.clock));
    this.map = map;
    passengerGenerator = new PassengerGenerator(map);
    vehicleGenerator = new VehicleGenerator();

    threads.get(this.clock).start();
  }

  private void writeObject(java.io.ObjectOutputStream out) throws IOException {
    runnables = new ArrayList<>();

    for (Runnable runnable : threads.keySet()) {
      runnables.add(runnable);
    }

    out.defaultWriteObject();
  }

  private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
    in.defaultReadObject();
    threads = new HashMap<>();
    for (Runnable runnable : runnables) {
      threads.put(runnable, new Thread(runnable));
      threads.get(runnable).start();
    }
  }

  /**
   * Toggle world clock on/off
   */
  public void togglePause() {
    clock.pause();
  }

  /**
   * Pause world clock
   */
  public void pause() {
    if (!clock.isPaused()) {
      clock.pause();
    }
  }

  /**
   * Resume world clock
   */
  public void resume() {
    if (clock.isPaused()) {
      clock.pause();
    }
  }

  /**
   *
   * @return A list of all vehicles that currently inhabit this world.
   */
  public List<Vehicle> getAllVehicles() {
    return map.getAllVehicles();
  }

  /**
   *
   * @return A list of all stopovers that are on this world's map.
   */
  public List<Stopover> getAllStopovers() {
    return map.getAllStopovers();
  }

  /**
   *
   * @param stopover
   * @return A list of all stopovers that are neighbors of a given stopover on this world's map.
   */
  public List<Stopover> getNeighbouringStopovers(Stopover stopover) {
    return map.getNeighbouringStopovers(stopover);
  }

  /**
   * Adds a new random civil airplane and generates passengers in a nearby civil destination.
   */
  public void addCivilAirplane() {
    try {
      CivilAirplane vehicle = vehicleGenerator.newCivilAirplane();
      CivilAirport startingPoint = map.getRandomCivilAirport();
      vehicle.setRoute(map.getRouteGenerator().newCivilAirRoute(startingPoint, 4));
      addPassengersAccordingly(vehicle.passengerZone().getCapacity() / 2, startingPoint);
      prepareVehicle(vehicle, startingPoint.getCoordinates());

    }
    catch (StopoverNotFoundInStopoverNetworkException e) {
      System.err.println("Could not add vehicle, because randomly selected stopover from the map is not on the map");
      e.printStackTrace();
    }
  }

  /**
   * Adds a new military plane that starts from the given ship.
   * @param ship
   */
  public void addMilitaryAirplane(MilitaryShip ship) {
    try {
      MilitaryAirplane vehicle = vehicleGenerator.newMilitaryAirplane(ship.getWeapon().getType());
      MilitaryAirport startingPoint = map.findClosestMetricallyMilitaryAirport(ship.getPreviousStopover());
      vehicle.setRoute(map.getRouteGenerator().newMilitaryAirRoute(startingPoint, 4));
      prepareVehicle(vehicle, ship.getCoordinates());
    }
    catch (StopoverNotFoundInStopoverNetworkException e) {
      System.err.println("Could not add vehicle, because randomly selected stopover from the map is not on the map");
      e.printStackTrace();
    }
  }

  /**
   * Adds a new random civil ship and generates passengers in a nearby civil destination.
   */
  public void addCivilShip() {
    try {
      CivilShip vehicle = vehicleGenerator.newCivilShip();
      Port startingPoint = map.getRandomPort();
      vehicle.setRoute(map.getRouteGenerator().newCivilSeaRoute(startingPoint, 4));
      addPassengersAccordingly((vehicle.passengerZone().getCapacity() / 2), startingPoint);
      prepareVehicle(vehicle, startingPoint.getCoordinates());
    }
    catch (StopoverNotFoundInStopoverNetworkException e) {
      System.err.println("Could not add vehicle, because randomly selected stopover from the map is not on the map");
      e.printStackTrace();
    }
  }

  /**
   * Adds a new military ship.
   */
  public void addMilitaryShip() {
    try {
      MilitaryShip vehicle = vehicleGenerator.newMilitaryShip();
      Junction startingPoint = map.getAdjacentJunction(map.getRandomPort());
      vehicle.setRoute(Arrays.asList(startingPoint, map.getAdjacentJunction(startingPoint)));
      prepareVehicle(vehicle, startingPoint.getCoordinates());
    }
    catch (StopoverNotFoundInStopoverNetworkException e) {
      System.err.println("Could not add vehicle, because randomly selected stopover from the map is not on the map");
      e.printStackTrace();
    }
  }

  /**
   * Removes the given vehicle from this world and terminates its thread.
   * @param vehicle
   * @throws InterruptedException
   */
  public void removeVehicle(Vehicle vehicle) throws InterruptedException {
    if (vehicle instanceof CivilVehicle) {
      removePassengersThread((CivilVehicle) vehicle);
    }

    removeVehicleThread(vehicle);
    map.removeVehicle(vehicle);
  }

  /**
   * Terminates threads of all vehicles and passengers inhabiting this world.
   */
  public void shutDown() {
    threads.forEach((runnable, thread) -> thread.interrupt());
  }

  public Stopover findStopoverAtCoordinates(double x, double y, double errorMargin) {
    Coordinates clickedCoordinates = new Coordinates(x, y);
    for (Stopover stopover : getAllStopovers()) {
      if (stopover.getCoordinates().distanceTo(clickedCoordinates) < errorMargin) {
        return stopover;
      }
    }
    return null;
  }

  /**
   *
   * @param x
   * @param y
   * @param errorMargin How much vehicle's coordinates can differ from given coordinates
   * @return A vehicle whose coordinates are equal to the given coordinates plus/minus error margin
   */
  public Vehicle findVehicleAtCoordinates(double x, double y, double errorMargin) {
    Coordinates clickedCoordinates = new Coordinates(x, y);
    for (Vehicle vehicle : getAllVehicles()) {
      if (vehicle.getCoordinates().distanceTo(clickedCoordinates) <  errorMargin) {
        return vehicle;
      }
    }
    return null;
  }

  private void removeVehicleThread(Vehicle vehicle) throws InterruptedException {
    Thread thread = threads.get(vehicle);
    thread.interrupt();
    thread.join();
    threads.remove(vehicle);
  }

  private void removePassengersThread(CivilVehicle vehicle) throws InterruptedException {
    for( Passenger passenger : vehicle.passengerZone().getPassengersUnsafe()) {
      Thread thread = threads.get(passenger);
      thread.interrupt();
      thread.join();
      threads.remove(passenger);
    }
  }

  private void prepareVehicle(Vehicle vehicle, Coordinates coordinates) {
    map.registerVehicle(vehicle, coordinates);
    threads.put(vehicle, new Thread(vehicle));

    clock.addListener(vehicle);
    threads.get(vehicle).start();
  }

  private void addPassengersAccordingly(int howMany, CivilDestination hometown) {
    try {
      ArrayList<Passenger> passengers = new ArrayList<>();

      for (int i = 0; i < howMany; i++) {
        Passenger newPassenger = passengerGenerator.randomPassenger(hometown);
        threads.put(newPassenger, new Thread(newPassenger));
        threads.get(newPassenger).start();
        passengers.add(newPassenger);
      }

      for (Passenger passenger : passengers) {
        synchronized (passenger) {
          passenger.setArrivedAtPassengerZone(hometown.passengerZone());
          passenger.notify();
        }
      }
    }
    catch (StopoverNotFoundInStopoverNetworkException e) {
      System.err.println("Tried to add passengers with the hometown of " + hometown + ", but no such CivilDestination exist on Map " + map);
      System.err.println("All stopovers on map are: ");
      map.getAllStopovers().forEach(System.err::println);
      e.printStackTrace();
    }
  }
}
