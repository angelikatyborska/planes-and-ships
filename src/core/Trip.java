package core;

import stopovers.CivilAirport;
import stopovers.Destination;
import stopovers.Port;
import world.WorldMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Trip {
  private Destination to;
  private Destination from;
  private List<Destination> throughTo;
  private List<Destination> throughBack;
  double waitingTime;
  private WorldMap map;

  public Trip(Destination from, WorldMap map) {
    this.from = from;
    this.map = map;
    waitingTime = 0;
    randomize();
  }

  public void randomize() {
    throughTo = new ArrayList<>();
    throughBack = new ArrayList<>();
    do {
      to = map.getRandomDestinationOfType(Arrays.asList(CivilAirport.class, Port.class));
    } while (to == from);
  }

  public Destination getFrom() {
    return from;
  }

  public Destination getTo() {
    return to;
  }

  public List<Destination> getThroughTo() {
    return throughTo;
  }

  public List<Destination> getThroughBack() {
    return throughBack;
  }

  public double getWaitingTime() {
    return waitingTime;
  }
}
