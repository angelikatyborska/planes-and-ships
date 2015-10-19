package world;

import stopovers.Destination;
import stopovers.Junction;
import stopovers.Stopover;

import java.util.ArrayList;
import java.util.HashMap;
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
    List<StopoverNetworkNode> foundNodes = nodes.stream().filter(node -> node.hasStopoverOfType(type)).collect(Collectors.toList());

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

    Optional<StopoverNetworkNode> matchingNode
      = nodesToSearch.stream().filter(node -> node.hasStopoverOfType(type)).findFirst();

    if (matchingNode.isPresent()) {
      StopoverNetworkNode firstMatchingNode = matchingNode.get();
      double minDistance = distanceBetweenNodes(fromNode, firstMatchingNode);
      Stopover closestStopover = firstMatchingNode.getStopover();

      for (StopoverNetworkNode nodeToSearch : nodesToSearch) {
        if (nodeToSearch.hasStopoverOfType(type)) {
          double distance = distanceBetweenNodes(fromNode, nodeToSearch);

          if (distance < minDistance) {
            minDistance = distance;
            closestStopover = nodeToSearch.getStopover();
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

    while (!nodesToSearch.isEmpty()) {
      List<StopoverNetworkNode> nextNodesToSearch = new ArrayList<>();

      Optional<StopoverNetworkNode> filteredNodes
        = nodesToSearch.stream()
        .filter(node -> node.hasStopoverOfType(type))
        .findFirst();

      nodesToSearch.forEach((node) -> {
        node.getNeighbours().forEach((nodeNeighbour) -> {
          if (!nextNodesToSearch.contains(nodeNeighbour) && !processedNodes.contains(nodeNeighbour)) {
            nextNodesToSearch.add(nodeNeighbour);
          }
        });
      });

      processedNodes.addAll(nodesToSearch);

      nodesToSearch = new ArrayList<>(nextNodesToSearch);

      if (filteredNodes.isPresent()) {
        return filteredNodes.get().getStopover();
      }
    }

    return null;
  }

  public List<Junction> findJunctionsFromStopoverTo(Stopover from, Stopover to) throws StopoverNotFoundInStopoverNetworkException {
    List<Junction> junctions = new ArrayList<>();
    List<StopoverNetworkNode> nodesToSearch = new ArrayList<>();
    List<StopoverNetworkNode> processedNodes = new ArrayList<>();

    nodesToSearch.add(getNode(from));

    HashMap<StopoverNetworkNode, StopoverNetworkNode> childToParent = new HashMap<>();
    StopoverNetworkNode currentNode;

    while (!nodesToSearch.isEmpty()) {
      currentNode = nodesToSearch.get(0);
      nodesToSearch.remove(0);
      processedNodes.add(currentNode);

      List<StopoverNetworkNode> neighboursToAdd = currentNode.getNeighbours().
        stream().filter(node -> {
        return (node.hasStopoverOfType(Junction.class) || node.getStopover() == to) && !processedNodes.contains(node);
      })
        .collect(Collectors.toList());

      nodesToSearch.addAll(neighboursToAdd);

      for (StopoverNetworkNode neighbour : neighboursToAdd) {
        childToParent.put(neighbour, currentNode);
      }

      if (currentNode.getStopover() == to) {
        while (childToParent.get(currentNode).getStopover() != from) {
          junctions.add((Junction) childToParent.get(currentNode).getStopover());
          currentNode = childToParent.get(currentNode);
        }

        return junctions;
      }
    }
    return null;
  }

  private double distanceBetweenNodes(StopoverNetworkNode node1, StopoverNetworkNode node2) {
    return node1.getStopover().getCoordinates().distanceTo(node2.getStopover().getCoordinates());
  }
}
