package core;

import stopovers.*;
import world.StopoverNotFoundInStopoverNetworkException;
import world.WorldMap;

import java.util.List;

/**
 * Keeps a list of CivilDestinations that the passenger wants to visit.
 * @see Passenger
 * @see CivilDestination
 */
public class Trip {
  public enum TripType { HOLIDAY, BUSINESS };
  private TripType type;
  private CivilDestination to;
  private CivilDestination from;
  private List<CivilDestination> throughTo;
  private List<CivilDestination> throughBack;
  private boolean goingBack;
  private int previousStopover;
  private WorldMap map;

  /**
   *
   * @param from Where to start this trip
   * @param map A world map on which this trip is based
   * @throws StopoverNotFoundInStopoverNetworkException
   */
  public Trip(CivilDestination from, WorldMap map) throws StopoverNotFoundInStopoverNetworkException {
    this.from = from;
    this.map = map;
    randomize();
  }

  /**
   * Chooses new CivilStopovers to stop at and a new trip type, doesn't change the starting point
   * @throws StopoverNotFoundInStopoverNetworkException
   */
  public void randomize() throws StopoverNotFoundInStopoverNetworkException {
    do {
      do {
        to = map.getRandomCivilDestination();
      } while (to == from);
      throughTo = map.getRouteGenerator().newCivilRoute(from, to);
      throughBack = map.getRouteGenerator().newCivilRoute(to, from);
    } while (throughTo == null || throughBack == null);

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

  public List<CivilDestination> getThroughTo() {
    return throughTo;
  }

  public List<CivilDestination> getThroughBack() {
    return throughBack;
  }

  public void checkpoint(PassengerZone checkpoint) {
   if (getNextCivilDestination().passengerZone() == checkpoint) {
     previousStopover++;

     if (goingBack) {
       if (previousStopover == throughBack.size() - 1) {
         goingBack = !goingBack;
         previousStopover = 0;
       }
     } else {
       if (previousStopover == throughTo.size() - 1) {
         goingBack = !goingBack;
         previousStopover = 0;
       }
     }
   }
  }

  // TODO: show to the teacher - using instanceof

  public CivilDestination getNextCivilDestination() {
    if (goingBack) {
      return throughBack.get(previousStopover + 1);
    }
    else {
      return throughTo.get(previousStopover + 1);
    }
  }

  public CivilDestination getPreviousCivilDestination() {
    if (goingBack) {
      return throughBack.get(previousStopover);
    }
    else {
      return throughTo.get(previousStopover);
    }
  }

  public int getWaitingTime() {
    switch (type) {
      case HOLIDAY:
        return (int) Math.floor(Math.random() * 10000 + 10000);
      case BUSINESS:
        return (int) Math.floor(Math.random() * 10000 + 5000);
      default:
        return 7500;
    }
  }

  public TripType getTripType() {
    return type;
  }
}
