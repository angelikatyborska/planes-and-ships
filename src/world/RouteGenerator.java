package world;

import core.Coordinates;
import stopovers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RouteGenerator {
  private WorldMap map;
  public RouteGenerator(WorldMap map) {
    this.map = map;
  }

  public List<Stopover> newCivilAirRoute(CivilAirport from) throws StopoverNotFoundInStopoverNetworkException {
    return newCivilAirRoute(from, 1);
  }

  public List<Stopover> newCivilAirRoute(CivilAirport from, int numberOfDestinations) throws StopoverNotFoundInStopoverNetworkException {
    return newRoute(from, CivilAirport.class, numberOfDestinations);
  }

  public List<Stopover> newMilitaryAirRoute(MilitaryAirport from) throws StopoverNotFoundInStopoverNetworkException {
    return newMilitaryAirRoute(from, 1);
  }

  public List<Stopover> newMilitaryAirRoute(MilitaryAirport from, int numberOfDestinations) throws StopoverNotFoundInStopoverNetworkException {
    return newRoute(from, MilitaryAirport.class, numberOfDestinations);
  }

  public List<Stopover> newCivilSeaRoute(Port from) throws StopoverNotFoundInStopoverNetworkException {
    return newCivilSeaRoute(from, 1);
  }

  public List<Stopover> newCivilSeaRoute(Port from, int numberOfDestinations) throws StopoverNotFoundInStopoverNetworkException {
    return newRoute(from, Port.class, numberOfDestinations);
  }

  public List<CivilDestination> newCivilRoute(CivilDestination from, CivilDestination finalDestination) throws StopoverNotFoundInStopoverNetworkException {
    ArrayList<CivilDestination> route = new ArrayList<>();

    route.add(from);

    CivilDestination to = finalDestination;
    List<CivilDestination> through = map.findCivilRouteBetween(from, to);
    route.addAll(through);
    route.add(finalDestination);
    return route;
  }

  public List<Stopover> newRoute(Stopover from, Class<? extends Stopover> type, int n) throws StopoverNotFoundInStopoverNetworkException {
    int i = 0;
    ArrayList<Stopover> route = new ArrayList<>();

    Stopover to;
    Stopover oldTo = from;
    List<Junction> through;
    route.add(from);

    do {
      do {
        to = map.getRandomStopoverOfType(type);
        through = map.findJunctionsBetween(oldTo, to);
      } while (to == from || to == oldTo || through == null);

      route.addAll(through);
      route.add(to);
      oldTo = to;
      i++;
    } while (i < n);

    return route;
  }
}
