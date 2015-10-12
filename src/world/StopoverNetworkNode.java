package world;

import stopovers.Stopover;

import java.util.ArrayList;

public class StopoverNetworkNode {
  private final Stopover stopover;

  private final ArrayList<Stopover> neighbours;

  public StopoverNetworkNode(Stopover stopover) {
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
