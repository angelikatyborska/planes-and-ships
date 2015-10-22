package core;

import stopovers.*;
import world.StopoverNotFoundInStopoverNetworkException;
import world.WorldMap;

import java.util.ArrayList;
import java.util.List;

public class Trip {
  public enum TripType { HOLIDAY, BUSINESS };
  private TripType type;
  private CivilDestination to;
  private CivilDestination from;
  private List<Stopover> throughTo;
  private List<Stopover> throughBack;
  private WorldMap map;

  public Trip(CivilDestination from, WorldMap map) throws StopoverNotFoundInStopoverNetworkException {
    this.from = from;
    this.map = map;
    randomize();
  }

  public void randomize() throws StopoverNotFoundInStopoverNetworkException {
    to = map.getRandomCivilDestination();
    throughTo = map.getRouteGenerator().newCivilRoute(from, to);
    throughBack = new ArrayList<>();
  }

  public CivilDestination getFrom() {
    return from;
  }

  public CivilDestination getTo() {
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
