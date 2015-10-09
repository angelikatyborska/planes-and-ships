package world;

import vehicles.Vehicle;

import java.util.ArrayList;

public class Map {
  private final DestinationNetwork destinationNetwork;
  private final ArrayList<Vehicle> vehicles;

  public Map(DestinationNetwork destinationNetwork, ArrayList<Vehicle> vehicles) {
    this.destinationNetwork = destinationNetwork;
    this.vehicles = vehicles;
  }
}
