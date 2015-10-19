package core;

import stopovers.CivilAirport;
import stopovers.Destination;
import stopovers.Port;
import stopovers.Stopover;
import world.WorldMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Trip {
  private Destination to;
  private Destination from;
  private List<Stopover> throughTo;
  private List<Stopover> throughBack;
  double waitingTime;
  private WorldMap map;

  public Trip(Destination from, WorldMap map) {
    this.from = from;
    this.map = map;
    waitingTime = 0;
    randomize();
  }

  public void randomize() {
//    throughTo = new ArrayList<>();
//    throughBack = new ArrayList<>();
//    do {
//      to = map.getRandomStopoverOfType(Arrays.asList(CivilAirport.class, Port.class));
//    } while (to == from);
  }

  public Destination getFrom() {
    return from;
  }

  public Destination getTo() {
    return to;
  }

  public List<Stopover> getThroughTo() {
    return throughTo;
  }

  public List<Stopover> getThroughBack() {
    return throughBack;
  }

  public double getWaitingTime() {
    // TODO: refactor to remove sublclasses and remember trip type in this class
    return waitingTime;
  }
}
