package core;

/**
 * Represents a set of coordinates in the Cartesian coordinate system
 */
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

  /**
   * Calculates the distance in a straight line between coordinates and target coordinates
   * @param target
   * @return
   */
  public double distanceTo(Coordinates target) {
    double destinationXOffset = target.getX() - x;
    double destinationYOffset = target.getY() - y;

    return Math.sqrt(Math.pow(destinationXOffset, 2) + Math.pow(destinationYOffset, 2));
  }

  /**
   * Recalculates coordinates so that they are closer to target coordinates by the value of distance in a straight line
   * @param target
   * @param distance
   */
  public void moveTowards(Coordinates target, double distance) {
    double destinationXOffset = target.getX() - x;
    double destinationYOffset = target.getY() - y;
    double distanceToDestination = distanceTo(target);

    if (distanceToDestination > distance) {
      x = x + distance * destinationXOffset / distanceToDestination;
      y = y + distance * destinationYOffset / distanceToDestination;
    }
    else {
      x = target.getX();
      y = target.getY();
    }
  }

  /**
   *
   * @param target
   * @return an angle in radians formed by the 0Y axis and the segment formed with these coordinates and target coordinates
   */
  public double getBearing(Coordinates target) {
    double dx = target.getX() - x;
    double dy = target.getY() - y;
    return Math.atan2(dy, dx) + Math.PI / 2;
  }

  public String toString() {
    return "(" + (int) x + ", " + (int) y + ")";
  }
}
