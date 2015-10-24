package world;

import core.Coordinates;
import core.Passenger;
import core.PassengerGenerator;
import stopovers.*;
import vehicles.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class World {
  private WorldClock clock;
  private WorldMap map;
  private PassengerGenerator passengerGenerator;
  private VehicleGenerator vehicleGenerator;
  private HashMap<Runnable, Thread> threads;

  public World(WorldClock clock, WorldMap map) {
    threads = new HashMap<>();
    this.clock = clock;
    threads.put(this.clock, new Thread(this.clock));
    this.map = map;
    passengerGenerator = new PassengerGenerator(map);
    vehicleGenerator = new VehicleGenerator();

    threads.get(this.clock).start();
  }

  public List<Vehicle> getAllVehicles() {
    return map.getAllVehicles();
  }

  public List<Stopover> getAllStopovers() {
    return map.getAllStopovers();
  }

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

  public void addMilitaryAirplane(MilitaryShip ship) {
    try {
      MilitaryAirplane vehicle = vehicleGenerator.newMilitaryAirplane(ship.getWeapon().getType());
      MilitaryAirport startingPoint = map.getRandomMilitaryAirport();
      vehicle.setRoute(map.getRouteGenerator().newMilitaryAirRoute(startingPoint));
      prepareVehicle(vehicle, ship.getCoordinates());
    }
    catch (StopoverNotFoundInStopoverNetworkException e) {
      System.err.println("Could not add vehicle, because randomly selected stopover from the map is not on the map");
      e.printStackTrace();
    }
  }

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

  public void addMilitaryShip() {
    try {
      MilitaryShip vehicle = vehicleGenerator.newMilitaryShip();
      Junction startingPoint = map.getAdjecentJunction(map.getRandomPort());
      vehicle.setRoute(Arrays.asList(startingPoint, map.getAdjecentJunction(startingPoint)));
      prepareVehicle(vehicle, startingPoint.getCoordinates());
    }
    catch (StopoverNotFoundInStopoverNetworkException e) {
      System.err.println("Could not add vehicle, because randomly selected stopover from the map is not on the map");
      e.printStackTrace();
    }
  }

  public void removeVehicle(Vehicle vehicle) throws InterruptedException {
    removeVehicleThread(vehicle);
  }

  public void removeVehicle(CivilAirplane vehicle) throws InterruptedException {
    removePassengersThread(vehicle);
    removeVehicleThread(vehicle);
  }

  public void removeVehicle(CivilShip vehicle) throws InterruptedException {
    removePassengersThread(vehicle);
    removeVehicleThread(vehicle);
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

      // TODO:  honestly, assuming that every new passenger started waiting at this point is just being optimistic, and not a good coder...
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

  public Vehicle findVehicleAtCoordinates(double x, double y, double errorMargin) {
    Coordinates clickedCoordinates = new Coordinates(x, y);
    for (Vehicle vehicle : getAllVehicles()) {
      if (vehicle.getCoordinates().distanceTo(clickedCoordinates) <  errorMargin) {
        return vehicle;
      }
    }
    return null;
  }
}
