package world;

import vehicles.Vehicle;

import java.util.ArrayList;

public class Map {
  private final StopoverNetwork stopoverNetwork;
  private final ArrayList<Vehicle> vehicles;

  public Map(StopoverNetwork stopoverNetwork, ArrayList<Vehicle> vehicles) {
    this.stopoverNetwork = stopoverNetwork;
    this.vehicles = vehicles;
  }
}
