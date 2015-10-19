package world;

import stopovers.Stopover;

import java.util.ArrayList;
import java.util.List;

public class StopoverNetworkNode {
  private final Stopover stopover;

  private final ArrayList<StopoverNetworkNode> neighbours;

  public StopoverNetworkNode(Stopover stopover) {
    this.stopover = stopover;
    this.neighbours = new ArrayList<>();
  }

  public void addNeighbour(StopoverNetworkNode neighbour) {
    neighbours.add(neighbour);
  }

  public Stopover getStopover() {
    return stopover;
  }

  public List<StopoverNetworkNode> getNeighbours() {
    return neighbours;
  }

  public boolean hasStopoverOfType(Class<? extends Stopover> type) {
    return type.isInstance(this.stopover);
  }
}
