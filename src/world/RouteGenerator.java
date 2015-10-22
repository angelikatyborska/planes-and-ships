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

  public List<Stopover> newCivilRoute(CivilDestination from, CivilDestination finalDestination) throws StopoverNotFoundInStopoverNetworkException {
    ArrayList<Stopover> route = new ArrayList<>();

    Stopover to;
    Stopover oldTo = new Stopover(new Coordinates(0, 0), 0);
    List<Junction> through;
    // TODO: show to the teacher, using casting (I want a CivilDestination, but it can't be a subclass of Stopover, because then CivilAirport would have to extend Airport and CivilDestination and that's just not possible
    route.add((Stopover) from);

    do {
      do {
        to = (Stopover) map.getRandomCivilDestination();
        // if going from Airport to Airport or from Port to Port, choose new route to go by Vehicle
        if (to.getClass() == oldTo.getClass()) {
          through = map.findJunctionsBetween(oldTo, to); // TODO: should civilRoute include junctions?
        }
        // Passenger is going to have to go by foot to the closest destination
        else {
          to = map.findClosestMetricallyOfType(oldTo, to.getClass());
          through = new ArrayList<>();
        }
      } while (to == from || through == null);

      route.addAll(through);
      route.add(to);
      oldTo = to;

    } while (to != finalDestination);

    return route;
  }

  private List<Stopover> newRoute(Stopover from, Class<? extends Stopover> type, int n) throws StopoverNotFoundInStopoverNetworkException {
    int i = 0;
    ArrayList<Stopover> route = new ArrayList<>();

    Stopover to;
    List<Junction> through;
    route.add(from);

    do {
      do {
        to = map.getRandomStopoverOfType(type);
        through = map.findJunctionsBetween(from, to);
      } while (to == from || through == null);

      route.addAll(through);
      route.add(to);

      i++;
    } while (i < n);

    return route;
  }
}
