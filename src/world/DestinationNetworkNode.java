package world;

import destinations.Destination;

import java.util.ArrayList;

public class DestinationNetworkNode {
  private final Destination destination;

  private final ArrayList<Destination> neighbours;

  public DestinationNetworkNode(Destination destination) {
    this.destination = destination;
    this.neighbours = new ArrayList<>();
  }

  public void addNeighbour(Destination neighbour) {
    neighbours.add(neighbour);
  }

  public Destination getDestination() {
    return destination;
  }

  public ArrayList<Destination> getNeighbours() {
    return neighbours;
  }
}
