package vehicles;

import core.Coordinates;
import core.IdGenerator;
import stopovers.CivilDestination;
import stopovers.InvalidVehicleAtStopoverException;
import stopovers.Stopover;
import world.StopoverNotFoundInStopoverNetworkException;
import world.WorldClockListener;
import world.WorldMap;

import java.util.List;

import static java.lang.Thread.sleep;

public abstract class Vehicle extends WorldClockListener {
  private final int id;
  private final double velocity;
  protected WorldMap worldMap;
  protected List<Stopover> route;
  protected Stopover previousStopover;
  protected boolean goingBack;

  public Vehicle(double velocity) {
    this.id = IdGenerator.getId();
    this.velocity = velocity;
  }

  public double getVelocity() {
    return velocity;
  }

  public void setWorldMap(WorldMap worldMap) {
    this.worldMap = worldMap;
  }

  public Coordinates getCoordinates() {
    return worldMap.getVehicleCoordinates(this);
  }

  public Stopover getNextStopover() {
    return null;
  }

  public Stopover getPreviousStopover() {
    return null;
  }

  public CivilDestination getNextCivilDestination() {
    return null;
  }

  public void setRoute(List<Stopover> route) {
    this.route = route;
  }

  public void gotAccommodatedAt(Stopover stopover) {

  }

  public void gotReleasedFrom(Stopover stopover) {

  }

  @Override
  public void tick() {
    try {
      worldMap.moveVehicleTowardsTargetStopover(this, this.velocity);

      if (hasArrivedAtStopover(getNextStopover())) {
        Stopover stopover = getNextStopover();

        while (!stopover.accommodateVehicle(this)) {}
        gotAccommodatedAt(stopover);

        // implement route changes

        stopover.releaseVehicle(this);
        gotReleasedFrom(stopover);
      }

    } catch (StopoverNotFoundInStopoverNetworkException e) {
      System.err.println("Vehicle " + this + " tried to move to " + getNextStopover() + ", but it doesn't exist on the WorldMap");
      e.printStackTrace();
    } catch (InvalidVehicleAtStopoverException e) {
      System.err.println("Vehicle " + this + " tried to get accommodate at " + getNextStopover());
      e.printStackTrace();
    }
  }

  private boolean hasArrivedAtStopover(Stopover stopover) {
    Coordinates vehicleCoord = getCoordinates();
    Coordinates stopoverCoord = stopover.getCoordinates();
    return (vehicleCoord.getX() == stopoverCoord.getX() && vehicleCoord.getY() == stopoverCoord.getY());
  }
}
