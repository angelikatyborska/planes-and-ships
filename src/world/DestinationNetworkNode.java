package world;

import destinations.Stopover;

import java.util.ArrayList;

public class DestinationNetworkNode {
  private final Stopover stopover;

  private final ArrayList<Stopover> neighbours;

  public DestinationNetworkNode(Stopover stopover) {
    this.stopover = stopover;
    this.neighbours = new ArrayList<>();
  }

  public void addNeighbour(Stopover neighbour) {
    neighbours.add(neighbour);
  }

  public Stopover getStopover() {
    return stopover;
  }

  public ArrayList<Stopover> getNeighbours() {
    return neighbours;
  }
}
