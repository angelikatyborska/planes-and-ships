package world;

import stopovers.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Generates random routes of various types based on a map.
 */
public class RouteGenerator implements Serializable {
  private WorldMap map;

  /**
   *
   * @param map The map that is used to generate routes.
   */
  public RouteGenerator(WorldMap map) {
    this.map = map;
  }

  /**
   *
   * @param from The starting point of the route to be generated
   * @param numberOfDestinations How many CivilAirports should there be in the route aside from the starting point
   * @return A list of Stopovers that are of type CivilAirport that are connected to each other on the map
   * @throws StopoverNotFoundInStopoverNetworkException
   */
  public List<Stopover> newCivilAirRoute(CivilAirport from, int numberOfDestinations) throws StopoverNotFoundInStopoverNetworkException {
    return newRoute(from, CivilAirport.class, numberOfDestinations);
  }


  /**
   *
   * @param from The starting point of the route to be generated
   * @param numberOfDestinations How many MilitaryAirports should there be in the route aside from the starting point
   * @return A list of Stopovers that are of type MilitaryAirport that are connected to each other on the map
   * @throws StopoverNotFoundInStopoverNetworkException
   */
  public List<Stopover> newMilitaryAirRoute(MilitaryAirport from, int numberOfDestinations) throws StopoverNotFoundInStopoverNetworkException {
    return newRoute(from, MilitaryAirport.class, numberOfDestinations);
  }

  /**
   *
   * @param from The starting point of the route to be generated
   * @param numberOfDestinations How many Ports should there be in the route aside from the starting point
   * @return A list of Stopovers that are of type Port that are connected to each other on the map
   * @throws StopoverNotFoundInStopoverNetworkException
   */
  public List<Stopover> newCivilSeaRoute(Port from, int numberOfDestinations) throws StopoverNotFoundInStopoverNetworkException {
    return newRoute(from, Port.class, numberOfDestinations);
  }

  /**
   *
   * @param from The starting point of the route to be generated
   * @param finalDestination the ending point of the route to be generated
   * @return A list of CivilDestinations that are connected to each other on the map
   * @throws StopoverNotFoundInStopoverNetworkException
   */
  public List<CivilDestination> newCivilRoute(CivilDestination from, CivilDestination finalDestination) throws StopoverNotFoundInStopoverNetworkException {
    ArrayList<CivilDestination> route = new ArrayList<>();

    route.add(from);

    CivilDestination to = finalDestination;
    List<CivilDestination> through = map.findCivilRouteBetween(from, to);
    route.addAll(through);
    route.add(finalDestination);
    return route;
  }

  /**
   *
   * @param from The starting point of the route to be generated
   * @param type The type of stopovers that should be in the route
   * @param n How many Stopovers of type should there be in the route aside from the starting point
   * @return A list of stopovers that are connected to each other on the map
   * @throws StopoverNotFoundInStopoverNetworkException
   */
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
