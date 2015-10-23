package core;

public class Coordinates {
  private double x;
  private double y;

  public Coordinates(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public Coordinates(Coordinates coordinates) {
    this.x = coordinates.getX();
    this.y = coordinates.getY();
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

  /**
   *
   * @param destination
   * @return an angle in degrees formed by the 0X axis and the segment formed with these coordinates and destiantion coordinates
   */
  public double getBearing(Coordinates destination) {
    double dx = destination.getX() - x;
    double dy = destination.getY() - y;
    double radiansFromMinusPiToPi = Math.atan2(dy, dx);
    double radiansFromZeroToMinusPi = radiansFromMinusPiToPi >= 0 ? radiansFromMinusPiToPi : (2 * Math.PI + radiansFromMinusPiToPi);
    double degreesFromZeroTo360 = 180 * (radiansFromZeroToMinusPi) / Math.PI;
    return degreesFromZeroTo360;
  }
}
