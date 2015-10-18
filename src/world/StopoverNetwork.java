package world;

import stopovers.Destination;
import stopovers.Stopover;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StopoverNetwork {
  private List<StopoverNetworkNode> nodes;

  public StopoverNetwork() {
    nodes = new ArrayList<>();
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

  public Stopover findClosestDestinatioOfMatchingType(Stopover from, Class<? extends Destination> destinationType) throws StopoverNotFoundInStopoverNetworkException {
    StopoverNetworkNode startingNode = getNode(from);
    List<StopoverNetworkNode> nodesToSearch = new ArrayList<>(startingNode.getNeighbours());
    List<StopoverNetworkNode> processedNodes = new ArrayList<>();

    while (true) {
      List<StopoverNetworkNode> nextNodesToSearch = new ArrayList<>();

      Optional<StopoverNetworkNode> filteredNodes = nodesToSearch.stream()
        .filter(node -> node.getStopover().getClass() == destinationType)
        .findFirst();

      if (filteredNodes.isPresent()) {
        return filteredNodes.get().getStopover();
      }

      nodesToSearch.forEach((node) -> {
        node.getNeighbours().forEach((nodeNeighbour) -> {
          if (!nextNodesToSearch.contains(nodeNeighbour) && !processedNodes.contains(nodeNeighbour)) {
            nextNodesToSearch.add(nodeNeighbour);
          }
        });
      });

      processedNodes.addAll(nodesToSearch);

      nodesToSearch = new ArrayList<>(nextNodesToSearch);

      if (nodesToSearch.isEmpty()) {
        return null;
      }
    }
  }
}
