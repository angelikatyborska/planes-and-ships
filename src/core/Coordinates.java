package core;

public class Coordinates {
  private double x;
  private double y;

  public Coordinates(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public double distanceTo(Coordinates destination) {
    double destinationXOffset = destination.getX() - x;
    double destinationYOffset = destination.getY() - y;

    return Math.sqrt(Math.pow(destinationXOffset, 2) + Math.pow(destinationYOffset, 2));
  }

  public void moveTowards(Coordinates destination, double distance) {
    double destinationXOffset = destination.getX() - x;
    double destinationYOffset = destination.getY() - y;
    double distanceToDestination = distanceTo(destination);

    if (distanceToDestination > distance) {
      x = x + distance * destinationXOffset / distanceToDestination;
      y = y + distance * destinationYOffset / distanceToDestination;
    }
    else {
      x = destination.getX();
      y = destination.getY();
    }
  }
}
