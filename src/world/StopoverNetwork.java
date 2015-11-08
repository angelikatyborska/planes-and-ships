package world;

import com.google.common.collect.Lists;
import stopovers.*;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class StopoverNetwork implements Serializable {
  private List<StopoverNetworkNode> nodes;

  public StopoverNetwork() {
    nodes = new ArrayList<>();
  }

  public List<Stopover> getAllStopovers() {
    return getAllOfType(Stopover.class);
  }

  public List<Stopover> getAllNeighbouringStopovers(Stopover stopover) {
    List<Stopover> neighbours = new ArrayList<>();

    try {
      for (StopoverNetworkNode node : getNode(stopover).getNeighbours()) {
        neighbours.add(node.getStopover());
      }
    } catch (StopoverNotFoundInStopoverNetworkException e) {
      System.err.println("Tried to find all neighbours of " + stopover + " but it's not in " + this);
      e.printStackTrace();
    }

    return neighbours;
  }

  public List<Stopover> getAllOfType(Class<? extends Stopover> type) {
    List<Stopover> foundStopovers = new ArrayList<>();
    List<StopoverNetworkNode> foundNodes = nodes.stream().filter(node -> node.hasStopoverOfType(type)).collect(Collectors.toList());

    foundNodes.forEach(node -> foundStopovers.add(node.getStopover()));

    return foundStopovers;
  }

  public StopoverNetworkNode getNode(Stopover stopover) throws StopoverNotFoundInStopoverNetworkException {
    Optional<StopoverNetworkNode> foundNode = nodes.stream().filter(node -> node.getStopover() == stopover).findFirst();

    if (!foundNode.isPresent()) {
      throw new StopoverNotFoundInStopoverNetworkException(this, stopover);
    }

    return foundNode.get();
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

  public Stopover findClosestMetricallyOfType(Stopover from, Class<? extends Stopover> type){
    Stopover result = null;
    try {
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

        result = closestStopover;
      }
    }
    catch (StopoverNotFoundInStopoverNetworkException e) {
      System.err.println("Tried to find closest metrically of " + type + " from " + from + ", but this Stopover doesn't exist in StopoverNetwork " + this);
      System.err.println("All Stopovers from this network are: ");
      getAllStopovers().forEach(System.err::println);
      e.printStackTrace();
    }

    return result;
  }

  public Stopover findClosestConnectedOfType(Stopover from, Class<? extends Stopover> type) throws StopoverNotFoundInStopoverNetworkException {
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

  public List<Junction> findJunctionsBetween(Stopover from, Stopover to) throws StopoverNotFoundInStopoverNetworkException {
    List<Junction> junctions = new ArrayList<>();
    List<StopoverNetworkNode> nodesToSearch = new ArrayList<>();
    List<StopoverNetworkNode> processedNodes = new ArrayList<>();

    nodesToSearch.add(getNode(from));

    HashMap<StopoverNetworkNode, StopoverNetworkNode> childToParent = new HashMap<>();
    StopoverNetworkNode currentNode;

    if (from == to) {
      return null;
    }

    while (!nodesToSearch.isEmpty()) {
      currentNode = nodesToSearch.get(0);
      nodesToSearch.remove(0);
      processedNodes.add(currentNode);

      // queue only junctions and stopovers of matching type
      List<StopoverNetworkNode> neighboursToAdd = currentNode.getNeighbours()
        .stream()
        .filter(node ->
          (node.hasStopoverOfType(Junction.class) || node.getStopover() == to)
            && !processedNodes.contains(node))
        .collect(Collectors.toList());

      nodesToSearch.addAll(neighboursToAdd);

      for (StopoverNetworkNode neighbour : neighboursToAdd) {
        if (!childToParent.containsKey(neighbour)) {
          childToParent.put(neighbour, currentNode);
        }
      }

      if (currentNode.getStopover() == to) {
        // recreate the path from the last to the first junction
        while (childToParent.get(currentNode).getStopover() != from) {
          junctions.add((Junction) childToParent.get(currentNode).getStopover());
          currentNode = childToParent.get(currentNode);
        }

        Collections.reverse(junctions);

        return junctions;
      }
    }
    return null;
  }

  public List<CivilDestination> findCivilRouteBetween(CivilDestination from, CivilDestination to) throws StopoverNotFoundInStopoverNetworkException {
    List<Stopover> stopovers = new ArrayList<>();
    List<StopoverNetworkNode> nodesToSearch = new ArrayList<>();
    List<StopoverNetworkNode> processedNodes = new ArrayList<>();

    nodesToSearch.add(getNode((Stopover) from));

    HashMap<StopoverNetworkNode, StopoverNetworkNode> childToParent = new HashMap<>();
    StopoverNetworkNode currentNode;

    if (from == to) {
      return null;
    }

    while (!nodesToSearch.isEmpty()) {
      currentNode = nodesToSearch.get(0);
      nodesToSearch.remove(0);
      processedNodes.add(currentNode);

      List<StopoverNetworkNode> neighboursToAdd = currentNode.getNeighbours()
        .stream()
        .filter(node ->
          (node.hasStopoverOfType(Junction.class) || node.hasStopoverOfType(Port.class) || node.hasStopoverOfType(CivilAirport.class))
            && !processedNodes.contains(node))
        .collect(Collectors.toList());

      nodesToSearch.addAll(neighboursToAdd);

      for (StopoverNetworkNode neighbour : neighboursToAdd) {
        if (!childToParent.containsKey(neighbour)) {
          childToParent.put(neighbour, currentNode);
        }
      }

      if (currentNode.getStopover() == to) {
        // recreate the path from the last to the first junction
        while (childToParent.get(currentNode).getStopover() != from) {
          stopovers.add(childToParent.get(currentNode).getStopover());
          currentNode = childToParent.get(currentNode);
        }

        stopovers = Lists.reverse(stopovers);

        ArrayList<CivilDestination> civilDestinations = new ArrayList<>();
        for (Stopover stopover : stopovers) {
          if (stopover instanceof CivilDestination) {
            civilDestinations.add((CivilDestination) stopover);
          }
        }

        return civilDestinations;
      }
    }
    return null;
  }

  private double distanceBetweenNodes(StopoverNetworkNode node1, StopoverNetworkNode node2) {
    return node1.getStopover().getCoordinates().distanceTo(node2.getStopover().getCoordinates());
  }
}
