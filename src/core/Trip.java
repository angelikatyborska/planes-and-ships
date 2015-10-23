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
  private boolean goingBack;
  private int previousStopover;
  private WorldMap map;

  public Trip(CivilDestination from, WorldMap map) throws StopoverNotFoundInStopoverNetworkException {
    this.from = from;
    this.map = map;
    randomize();
  }

  public void randomize() throws StopoverNotFoundInStopoverNetworkException {
    do {
      to = map.getRandomCivilDestination();
    } while (to == from);


    throughTo = map.getRouteGenerator().newCivilRoute(from, to);
    throughBack = map.getRouteGenerator().newCivilRoute(to, from);
    goingBack = false;
    previousStopover = 0;

    int randomNumber = (int) Math.floor(Math.random() * 2);
    if (randomNumber == 0) {
      type = TripType.BUSINESS;
    }
    else {
      type = TripType.HOLIDAY;
    }
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

  public void checkpoint() {
    previousStopover++;

    if (goingBack) {
      if (previousStopover == throughBack.size() - 1) {
        goingBack = !goingBack;
        previousStopover = 0;
      }
    }
    else {
      if (previousStopover == throughTo.size() - 1) {
        goingBack = !goingBack;
        previousStopover = 0;
      }
    }
  }

  // TODO: show to the teacher - using instanceof

  public CivilDestination getNextCivilDestination() {
    CivilDestination nextCivilDestination = null;
    if (goingBack) {
      for (int i = previousStopover + 1; i < throughBack.size(); i++) {
        if (throughBack.get(i) instanceof CivilDestination) {
          nextCivilDestination = (CivilDestination) throughBack.get(i);
        }
      }
    }
    else {
      for (int i = previousStopover + 1; i < throughTo.size(); i++) {
        if (throughTo.get(i) instanceof CivilDestination) {
          nextCivilDestination = (CivilDestination) throughTo.get(i);
        }
      }
    }

    return nextCivilDestination;
  }

  public int getWaitingTime() {
    switch (type) {
      case HOLIDAY:
        return 10000;
      case BUSINESS:
        return 5000;
      default:
        return 7500;
    }
  }
}
