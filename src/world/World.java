package world;

import core.Coordinates;
import core.Passenger;
import core.PassengerGenerator;
import core.PassengerZone;
import stopovers.*;
import vehicles.*;

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
  }

  public void addCivilAirplane() {
    try {
      CivilAirplane vehicle = vehicleGenerator.newCivilAirplane();
      CivilAirport startingPoint = map.getRandomCivilAirport();
      vehicle.setRoute(map.getRouteGenerator().newCivilAirRoute(startingPoint));
      fillWithPassengers(startingPoint.passengerZone(), startingPoint);
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
      vehicle.setRoute(map.getRouteGenerator().newCivilSeaRoute(startingPoint));
      fillWithPassengers(startingPoint.passengerZone(), startingPoint);
      prepareVehicle(vehicle, startingPoint.getCoordinates());
    }
    catch (StopoverNotFoundInStopoverNetworkException e) {
      System.err.println("Could not add vehicle, because randomly selected stopover from the map is not on the map");
      e.printStackTrace();
    }
  }

  public void addMilitaryShip() {
    MilitaryShip vehicle = vehicleGenerator.newMilitaryShip();
    Port startingPoint = map.getRandomPort();
    prepareVehicle(vehicle, startingPoint.getCoordinates());
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
    for( Passenger passenger : vehicle.passengerZone().getPassengers()) {
      Thread thread = threads.get(passenger);
      thread.interrupt();
      thread.join();
      threads.remove(passenger);
    }
  }

  private void prepareVehicle(Vehicle vehicle, Coordinates coordinates) {
    map.registerVehicle(vehicle, coordinates);
    threads.put(vehicle, new Thread(vehicle));
  }

  private void fillWithPassengers(PassengerZone zone, CivilDestination hometown) {
    try {
      for (int i = 0; i < zone.getCapacity() / 2; i++) {
        Passenger newPassenger = passengerGenerator.randomPassenger(hometown);
        threads.put(newPassenger, new Thread(newPassenger));
        zone.accommodate(passengerGenerator.randomPassenger(hometown));
      }
    } catch (StopoverNotFoundInStopoverNetworkException e) {
      System.err.println("Tried to add passengers with the hometown of " + hometown + ", but no such CivilDestination exist on Map " + map);
      e.printStackTrace();
    }
  }
}
