package world;

import stopovers.*;

import java.util.ArrayList;
import java.util.List;

public class RouteGenerator {
  private WorldMap map;
  public RouteGenerator(WorldMap map) {
    this.map = map;
  }

  public List<Stopover> newCivilAirRoute(CivilAirport from) throws StopoverNotFoundInStopoverNetworkException {
    return newRoute(from, CivilAirport.class);
  }

  public List<Stopover> newMilitaryAirRoute(MilitaryAirport from) throws StopoverNotFoundInStopoverNetworkException {
    return newRoute(from, MilitaryAirport.class);
  }

  public List<Stopover> newCivilSeaRoute(Port from) throws StopoverNotFoundInStopoverNetworkException {
    return newRoute(from, Port.class);
  }

  private List<Stopover> newRoute(Stopover from, Class<? extends Stopover> type) throws StopoverNotFoundInStopoverNetworkException {
    ArrayList<Stopover> route = new ArrayList<>();

    Stopover to;
    List<Junction> through;

    do {
      to = map.getRandomStopoverOfType(type);
      through = map.findJunctionsBetween(from, to);
    } while (to == from || through == null);

    route.add(from);
    route.addAll(through);
    route.add(to);

    return route;
  }
}
