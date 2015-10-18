package core;

import stopovers.Destination;
import world.WorldMap;

import java.util.List;

public class Trip {
  private Destination to;
  private Destination from;
  private List<Destination> throughTo;
  private List<Destination> throughBack;
  double waitingTime;

  public Trip(Destination from, WorldMap map) {
    this.from = from;

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
