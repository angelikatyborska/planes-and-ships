package world;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import core.Coordinates;
import org.junit.Test;
import stopovers.*;

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

    assertEquals(null, network.findClosestDestinatioOfMatchingType(startingPoint, Port.class));
    assertEquals(civilAirport, network.findClosestDestinatioOfMatchingType(startingPoint, CivilAirport.class));
    assertEquals(militaryAirportCloser, network.findClosestDestinatioOfMatchingType(startingPoint, MilitaryAirport.class));
  }

  // TODO: Write test for looped world when not finding anything
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

    assertEquals(null, network.findClosestDestinatioOfMatchingType(startingPoint, Port.class));

  }
}
