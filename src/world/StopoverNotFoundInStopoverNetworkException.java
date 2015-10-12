package world;

import stopovers.Stopover;

public class StopoverNotFoundInStopoverNetworkException extends Exception {
  private StopoverNetwork network;
  private Stopover stopover;

  public StopoverNotFoundInStopoverNetworkException(StopoverNetwork network, Stopover stopover) {
    this.stopover = stopover;
    this.network = network;
  }

  public Stopover getStopover() {
    return stopover;
  }

  public StopoverNetwork getNetwork() {
    return network;
  }
}
