package world;

import stopovers.Destination;
import stopovers.Stopover;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StopoverNetwork {
  private List<StopoverNetworkNode> nodes;

  public StopoverNetwork() {
    nodes = new ArrayList<>();
  }

  public List<Stopover> getAllStopoversOfType(Class<? extends Stopover> type) {
    List<Stopover> foundStopovers = new ArrayList<>();
    List<StopoverNetworkNode> foundNodes = nodes.stream().filter(node -> type.isInstance(node.getStopover())).collect(Collectors.toList());

    foundNodes.forEach(node -> foundStopovers.add(node.getStopover()));

    return foundStopovers;
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

  // TODO: refactor
  public Stopover findClosestMetricallyStopoverOfMatchingType(Stopover from, Class<? extends Stopover> type) throws StopoverNotFoundInStopoverNetworkException {
    StopoverNetworkNode fromNode = getNode(from);
    List<StopoverNetworkNode> nodesToSearch = new ArrayList<>(nodes);
    nodesToSearch.remove(fromNode);
    Optional<StopoverNetworkNode> matchingNodes = nodesToSearch.stream().filter(node -> type.isInstance(node.getStopover())).findFirst();

    if (matchingNodes.isPresent()) {
      StopoverNetworkNode firstMatchingNode = matchingNodes.get();
      double minDistance = from.getCoordinates().distanceTo(firstMatchingNode.getStopover().getCoordinates());
      Stopover closestStopover = firstMatchingNode.getStopover();

      for (StopoverNetworkNode node : nodesToSearch) {
        if (type.isInstance(node.getStopover())) {
          double distance = from.getCoordinates().distanceTo(node.getStopover().getCoordinates());

          if (distance < minDistance) {
            minDistance = distance;
            closestStopover = node.getStopover();
          }
        }
      }

      return closestStopover;
    }
    else {
      return null;
    }

  }

  public Stopover findClosestConnectedStopoverOfMatchingType(Stopover from, Class<? extends Stopover> type) throws StopoverNotFoundInStopoverNetworkException {
    StopoverNetworkNode startingNode = getNode(from);
    List<StopoverNetworkNode> nodesToSearch = new ArrayList<>(startingNode.getNeighbours());
    List<StopoverNetworkNode> processedNodes = new ArrayList<>();

    while (true) {
      List<StopoverNetworkNode> nextNodesToSearch = new ArrayList<>();

      Optional<StopoverNetworkNode> filteredNodes = nodesToSearch.stream()
        .filter(node -> node.getStopover().getClass() == type)
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
