package core;

import stopovers.Destination;
import stopovers.Stopover;
import world.WorldMap;

import java.util.List;

public class Trip {
  public static enum TripType { HOLIDAY, BUSINESS };
  private TripType type;
  private Destination to;
  private Destination from;
  private List<Stopover> throughTo;
  private List<Stopover> throughBack;
  private WorldMap map;

  public Trip(Destination from, WorldMap map) {
    this.from = from;
    this.map = map;
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
    switch (type) {
      case HOLIDAY:
        return 100;
      case BUSINESS:
        return 50;
      default:
        return 75;
    }
  }
}
