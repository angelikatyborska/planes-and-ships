package world;

import stopovers.*;

import java.util.ArrayList;
import java.util.List;

public class RouteGenerator {
  public static List<Stopover> newCivilAirRoute(WorldMap map, CivilAirport from) throws StopoverNotFoundInStopoverNetworkException {
    return newRoute(map, from, CivilAirport.class);
  }

  public static List<Stopover> newMilitaryAirRoute(WorldMap map, MilitaryAirport from) throws StopoverNotFoundInStopoverNetworkException {
    return newRoute(map, from, MilitaryAirport.class);
  }

  public static List<Stopover> newCivilSeaRoute(WorldMap map, Port from) throws StopoverNotFoundInStopoverNetworkException {
    return newRoute(map, from, Port.class);
  }

  private static List<Stopover> newRoute(WorldMap map, Stopover from, Class<? extends Stopover> type) throws StopoverNotFoundInStopoverNetworkException {
    ArrayList<Stopover> route = new ArrayList<>();
    int minLength = 4;

    for (int i = 0; i < minLength; i++) {
      Stopover to;
      List<Junction> through;

      do {
        to = map.getRandomStopoverOfType(type);
        through = map.findJunctionsBetween(from, to);
      } while (to == from || through == null);

      route.addAll(through);
      route.add(to);
    }

    return route;
  }
}
