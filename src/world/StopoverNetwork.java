package world;

import stopovers.Stopover;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StopoverNetwork {
  private List<StopoverNetworkNode> nodes;

  public StopoverNetwork() {
    nodes = new ArrayList<StopoverNetworkNode>();
  }

  public StopoverNetworkNode getNode(Stopover stopover) throws StopoverNotFoundInStopoverNetworkException {
    Optional<StopoverNetworkNode> foundNode = nodes.stream().filter(node -> node.getStopover() == stopover).findFirst();
    if (foundNode.isPresent()) {
      return foundNode.get();
    }
    else {
      throw new StopoverNotFoundInStopoverNetworkException(this, stopover);
    }
  }

  public void add(Stopover stopover) {
    nodes.add(new StopoverNetworkNode(stopover));
  }

  public void connect(Stopover stopover1, Stopover stopover2) throws StopoverNotFoundInStopoverNetworkException {
    StopoverNetworkNode node1 = getNode(stopover1);
    StopoverNetworkNode node2 = getNode(stopover2);

    node1.addNeighbour(node2);
    node2.addNeighbour(node1);
  }
}
