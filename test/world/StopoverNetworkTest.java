package world;

import static org.junit.Assert.*;

import core.Coordinates;
import org.junit.Test;
import stopovers.*;

import java.util.List;

public class StopoverNetworkTest {
  @Test
  public void shouldConnectNodes() throws StopoverNotFoundInStopoverNetworkException {
    Stopover stopover1 = new Stopover(new Coordinates(1,1), 1);
    Stopover stopover2 = new Stopover(new Coordinates(1,1), 1);
    StopoverNetwork network = new StopoverNetwork();

    network.add(stopover1);
    network.add(stopover2);

    StopoverNetworkNode node1 = network.getNode(stopover1);
    StopoverNetworkNode node2 = network.getNode(stopover2);

    assertTrue(node1.getNeighbours().isEmpty());
    assertTrue(node2.getNeighbours().isEmpty());

    network.connect(stopover1, stopover2);

    assertTrue(node1.getNeighbours().contains(node2));
    assertTrue(node2.getNeighbours().contains(node1));
  }

  @Test
  public void shouldGetNodeWhenStopoverAdded() throws StopoverNotFoundInStopoverNetworkException {
    Stopover stopover = new Stopover(new Coordinates(1,1), 1);
    StopoverNetwork network = new StopoverNetwork();

    network.add(stopover);

    StopoverNetworkNode node = network.getNode(stopover);

    assertEquals(stopover, node.getStopover());
  }


  @Test(expected=  StopoverNotFoundInStopoverNetworkException.class)
  public void shouldNotGetNodeWhenStopoverNotAdded() throws StopoverNotFoundInStopoverNetworkException {
    Stopover stopover = new Stopover(new Coordinates(1,1), 1);
    StopoverNetwork network = new StopoverNetwork();

    StopoverNetworkNode node = network.getNode(stopover);
  }

  @Test
  public void shouldFindClosestDestinationOfMatchingType() throws StopoverNotFoundInStopoverNetworkException {
    Coordinates dummyCoord = new Coordinates(1,1);
    Stopover startingPoint = new Stopover(dummyCoord, 1);
    Junction junction1 = new Junction(dummyCoord);
    Junction junction2 = new Junction(dummyCoord);
    CivilAirport civilAirport = new CivilAirport(dummyCoord, 3);
    MilitaryAirport militaryAirportCloser = new MilitaryAirport(dummyCoord, 3);
    MilitaryAirport militaryAirportFurther = new MilitaryAirport(dummyCoord, 3);
    StopoverNetwork network = new StopoverNetwork();

    network.add(startingPoint);
    network.add(junction1);
    network.connect(startingPoint, junction1);

    network.add(civilAirport);
    network.add(militaryAirportCloser);
    network.add(junction2);
    network.connect(junction1, civilAirport);
    network.connect(junction1, militaryAirportCloser);
    network.connect(junction1, junction2);

    network.add(militaryAirportFurther);
    network.connect(junction2, militaryAirportFurther);

    assertEquals(null, network.findClosestConnectedOfType(startingPoint, Port.class));
    assertEquals(civilAirport, network.findClosestConnectedOfType(startingPoint, CivilAirport.class));
    assertEquals(militaryAirportCloser, network.findClosestConnectedOfType(startingPoint, MilitaryAirport.class));
  }

  @Test
  public void shouldNotGetStuckInALoop() throws StopoverNotFoundInStopoverNetworkException {
    Coordinates dummyCoord = new Coordinates(1,1);
    Stopover startingPoint = new Stopover(dummyCoord, 1);
    Junction junction1 = new Junction(dummyCoord);
    Junction junction2 = new Junction(dummyCoord);
    StopoverNetwork network = new StopoverNetwork();

    network.add(startingPoint);
    network.add(junction1);
    network.add(junction2);

    network.connect(startingPoint, junction1);
    network.connect(junction1, junction2);
    network.connect(junction2, startingPoint);

    assertEquals(null, network.findClosestConnectedOfType(startingPoint, Port.class));
  }

  @Test
  public void shouldGetAllDestinationsOfType() throws StopoverNotFoundInStopoverNetworkException {
    Coordinates dummyCoord = new Coordinates(1,1);
    CivilAirport civilAirport = new CivilAirport(dummyCoord, 1);
    Junction junction1 = new Junction(dummyCoord);
    Junction junction2 = new Junction(dummyCoord);
    StopoverNetwork network = new StopoverNetwork();

    network.add(civilAirport);
    network.add(junction1);
    network.add(junction2);

    network.connect(civilAirport, junction1);
    network.connect(junction1, junction2);
    network.connect(junction2, civilAirport);

    List<Stopover> junctions = network.getAllOfType(Junction.class);
    List<Stopover> civilAirports = network.getAllOfType(CivilAirport.class);
    List<Stopover> ports = network.getAllOfType(Port.class);

    assertTrue(junctions.contains(junction1));
    assertTrue(junctions.contains(junction2));
    assertFalse(junctions.contains(civilAirport));

    assertTrue(civilAirports.contains(civilAirport));
    assertFalse(civilAirports.contains(junction1));
    assertFalse(civilAirports.contains(junction2));

    assertFalse(ports.contains(junction1));
    assertFalse(ports.contains(junction2));
    assertFalse(ports.contains(civilAirport));
  }

  @Test
  public void shouldFindClosestMetricallyStopoverOfMatchingType() throws StopoverNotFoundInStopoverNetworkException {
    Coordinates startingCoord = new Coordinates(10, 10);
    Coordinates closestCoord = new Coordinates (11, 10);
    Coordinates closerCoord = new Coordinates (11, 11);
    Coordinates furtherCoord = new Coordinates (13, 11);

    Stopover from = new Stopover(startingCoord, 1);
    CivilAirport civilAirport = new CivilAirport(closestCoord, 1);
    Junction furtherJunction = new Junction(furtherCoord);
    Junction closerJunction = new Junction(closerCoord);
    StopoverNetwork network = new StopoverNetwork();

    network.add(from);
    network.add(civilAirport);
    network.add(closerJunction);
    network.add(furtherJunction);

    assertEquals(closerJunction, network.findClosestMetricallyOfType(from, Junction.class));
    assertEquals(null, network.findClosestMetricallyOfType(from, MilitaryAirport.class));
  }

  @Test
  public void findClosestMetricallyStopoverOfMatchingTypeShouldNotFindStartingStopover() throws StopoverNotFoundInStopoverNetworkException {
    Coordinates startingCoord = new Coordinates(10, 10);

    Stopover from = new CivilAirport(startingCoord, 1);
    StopoverNetwork network = new StopoverNetwork();

    network.add(from);

    assertEquals(null, network.findClosestMetricallyOfType(from, CivilAirport.class));
  }

  @Test
  public void shouldNotLoopWhenStopoversNotConnected() throws StopoverNotFoundInStopoverNetworkException {
    Coordinates dummyCoord = new Coordinates(1,1);
    CivilAirport from = new CivilAirport(dummyCoord, 1);
    CivilAirport to = new CivilAirport(dummyCoord, 1);
    Junction junction1 = new Junction(dummyCoord);
    Junction junction2 = new Junction(dummyCoord);
    StopoverNetwork network = new StopoverNetwork();

    network.add(from);
    network.add(to);
    network.add(junction1);
    network.add(junction2);

    network.connect(from, junction1);
    network.connect(junction1, junction2);

    assertEquals(null, network.findJunctionsBetween(from, to));
  }

  @Test
  public void shouldFindJunctionsFromStopoverToStopover() throws StopoverNotFoundInStopoverNetworkException {
    Coordinates dummyCoord = new Coordinates(1,1);
    Stopover from = new Stopover(dummyCoord, 1);
    CivilAirport to = new CivilAirport(dummyCoord, 1);
    Junction junction1 = new Junction(dummyCoord);
    Junction junction2 = new Junction(dummyCoord);
    Junction junction3 = new Junction(dummyCoord);
    StopoverNetwork network = new StopoverNetwork();

    network.add(from);
    network.add(junction3);
    network.add(junction1);
    network.add(junction2);
    network.add(to);

    network.connect(from, junction1);
    network.connect(from, junction3);
    network.connect(junction1, junction2);
    network.connect(junction2, to);

    List<Junction> junctions = network.findJunctionsBetween(from, to);
    assertTrue(junctions.contains(junction1));
    assertTrue(junctions.contains(junction2));
    assertFalse(junctions.contains(junction3));
  }

  @Test
  public void shouldFindEmptyListOfJunctionsBetweenConnectedStopovers() throws StopoverNotFoundInStopoverNetworkException {
    Coordinates dummyCoord = new Coordinates(1,1);
    Stopover from = new Stopover(dummyCoord, 1);
    CivilAirport to = new CivilAirport(dummyCoord, 1);
    StopoverNetwork network = new StopoverNetwork();

    network.add(from);
    network.add(to);

    network.connect(from, to);

    List<Junction> junctions = network.findJunctionsBetween(from, to);
    assertTrue(junctions != null);
    assertTrue(junctions.isEmpty());
  }
}
