package world;

import static org.junit.Assert.*;

import com.sun.prism.paint.Stop;
import core.Coordinates;
import org.junit.Before;
import org.junit.Test;
import stopovers.*;

import java.util.List;

public class RouteGeneratorTest {
  private RouteGenerator routeGenerator;
  private WorldMap map;
  private Junction junction1;
  private Junction junction2;
  private Port startingPort;
  private Port endPort;

  @Before
  public void setUp() throws StopoverNotFoundInStopoverNetworkException {
    Coordinates dummyCoord = new Coordinates(1, 1);
    junction1 = new Junction(dummyCoord);
    junction2 = new Junction(dummyCoord);

    startingPort = new Port(dummyCoord, 4);
    endPort = new Port(dummyCoord, 4);
    CivilAirport civilAirport1 = new CivilAirport(dummyCoord, 4);
    CivilAirport civilAirport2 = new CivilAirport(dummyCoord, 4);
    MilitaryAirport militaryAirport1 = new MilitaryAirport(dummyCoord, 4);
    MilitaryAirport militaryAirport2 = new MilitaryAirport(dummyCoord, 4);

    StopoverNetwork network = new StopoverNetwork();

    network.add(junction1);
    network.add(junction2);
    network.add(startingPort);
    network.add(endPort);
    network.add(civilAirport1);
    network.add(civilAirport2);
    network.add(militaryAirport1);
    network.add(militaryAirport2);

    // hex
    network.connect(startingPort, civilAirport1);
    network.connect(civilAirport1, militaryAirport1);
    network.connect(militaryAirport1, endPort);
    network.connect(endPort, civilAirport2);
    network.connect(civilAirport2, militaryAirport2);
    network.connect(militaryAirport2, startingPort);

    network.connect(startingPort, junction1);
    network.connect(civilAirport1, junction1);
    network.connect(militaryAirport1, junction1);

    network.connect(endPort, junction2);
    network.connect(civilAirport2, junction2);
    network.connect(militaryAirport2, junction2);

    network.connect(junction1, junction2);
    map = new WorldMap(network);
    routeGenerator = new RouteGenerator(map);
  }

  @Test
  public void CivilSeaRouteShouldOnlyContainJunctionsAndPorts() throws StopoverNotFoundInStopoverNetworkException {
    List<Stopover> seaRoute = routeGenerator.newCivilSeaRoute(startingPort);

    for (Stopover stopover : seaRoute) {
      assertTrue(stopover instanceof Port || stopover instanceof Junction);
    }
  }

  @Test
  public void CivilSeaRouteShouldStartAndEndWithAPort() throws StopoverNotFoundInStopoverNetworkException {
    List<Stopover> seaRoute = routeGenerator.newCivilSeaRoute(startingPort);

    assertTrue(seaRoute.get(0) instanceof Port);
    assertTrue(seaRoute.get(seaRoute.size() - 1) instanceof Port);
  }

  @Test
  public void StopoversInARouteShouldBeConnected() throws StopoverNotFoundInStopoverNetworkException {
    List<Stopover> seaRoute = routeGenerator.newCivilSeaRoute(startingPort);

    for (int i = 0; i < seaRoute.size() - 1; i++) {
      List<Junction> junctionsBetweenNeighbours = map.findJunctionsBetween(seaRoute.get(i), seaRoute.get(i + 1));
      assertTrue(junctionsBetweenNeighbours.isEmpty());
    }
  }

  @Test
  public void shouldFindTheOnlyCorrectRoute() throws StopoverNotFoundInStopoverNetworkException {
    List<Stopover> seaRoute = routeGenerator.newCivilSeaRoute(startingPort);

    assertEquals(startingPort, seaRoute.get(0));
    assertEquals(junction1, seaRoute.get(1));
    assertEquals(junction2, seaRoute.get(2));
    assertEquals(endPort, seaRoute.get(3));
    assertEquals(4, seaRoute.size());
  }
}
