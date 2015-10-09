package core;

public class Coordinates {
  private double x;
  private double y;

  public Coordinates(double xCoordinate, double yCoordinate) {
    x = xCoordinate;
    y = yCoordinate;
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

  public void moveTowardsDestination(Coordinates destination, double distance) {
    double destinationXOffset = destination.getX() - x;
    double destinationYOffset = destination.getY() - y;
    double distanceToDestination = distanceTo(destination);

    x = x + distance * destinationXOffset / distanceToDestination;
    y = y + distance * destinationYOffset / distanceToDestination;
  }
}
