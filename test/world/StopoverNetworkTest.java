package world;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import core.Coordinates;
import org.junit.Test;
import stopovers.Stopover;

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
}
