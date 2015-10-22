package core;

import static org.junit.Assert.*;
import org.junit.Test;

public class CoordinatesTest {
  private static final double epsilon = 0.0001;

  @Test
  public void shouldSetXAndYCoordinates() {
    double x = 14.2;
    double y = 8.23;
    Coordinates coordinates = new Coordinates(x, y);

    assertEquals(x, coordinates.getX(), epsilon);
    assertEquals(y, coordinates.getY(), epsilon);
  }

  @Test
  public void shouldMoveNorthTowardsDestination() {
    double x = 14.2;
    double y = 8.23;
    double distance = 5;
    Coordinates coordinates = new Coordinates(x, y);
    Coordinates destination = new Coordinates (x, y + 3 * distance);

    coordinates.moveTowards(destination, distance);

    assertEquals(x, coordinates.getX(), epsilon);
    assertEquals(y + distance, coordinates.getY(), epsilon);
  }

  @Test
  public void shouldMoveEastTowardsDestination() {
    double x = 14.2;
    double y = 8.23;
    double distance = 5;
    Coordinates coordinates = new Coordinates(x, y);
    Coordinates destination = new Coordinates (x + 3 * distance, y);

    coordinates.moveTowards(destination, distance);

    assertEquals(x + distance, coordinates.getX(), epsilon);
    assertEquals(y, coordinates.getY(), epsilon);
  }

  @Test
  public void shouldMoveSouthTowardsDestination() {
    double x = 14.2;
    double y = 8.23;
    double distance = 5;
    Coordinates coordinates = new Coordinates(x, y);
    Coordinates destination = new Coordinates (x, y - 3 * distance);

    coordinates.moveTowards(destination, distance);

    assertEquals(x, coordinates.getX(), epsilon);
    assertEquals(y - distance, coordinates.getY(), epsilon);
  }

  @Test
  public void shouldMoveWestTowardsDestination() {
    double x = 14.2;
    double y = 8.23;
    double distance = 5;
    Coordinates coordinates = new Coordinates(x, y);
    Coordinates destination = new Coordinates (x - 3 * distance, y);

    coordinates.moveTowards(destination, distance);

    assertEquals(x - distance, coordinates.getX(), epsilon);
    assertEquals(y, coordinates.getY(), epsilon);
  }

  @Test
  public void shouldMoveCloserTowardsDestination() {
    double x = 14.2;
    double y = 8.23;
    double distance = 5;
    Coordinates coordinates = new Coordinates(x, y);
    Coordinates destination = new Coordinates (x + 2 * distance, y + 3 * distance);
    double oldDistance = coordinates.distanceTo(destination);

    coordinates.moveTowards(destination, distance);

    assertEquals(oldDistance - distance, coordinates.distanceTo(destination), epsilon);
  }

  @Test
  public void shouldMoveToDestination() {
    double x = 14.2;
    double y = 8.23;
    double distance = 5;
    Coordinates coordinates = new Coordinates(x, y);
    Coordinates destination = new Coordinates (x - 0.2 * distance, y - 0.3 * distance);

    coordinates.moveTowards(destination, distance);

    assertEquals(0, coordinates.distanceTo(destination), epsilon);
  }

  @Test
  public void shouldGetBearing() {
    Coordinates startingPoint = new Coordinates(3, 3);

    assertEquals(0, startingPoint.getBearing(new Coordinates(4, 3)), 0.000001);
    assertEquals(45, startingPoint.getBearing(new Coordinates(4, 4)), 0.000001);
    assertEquals(90, startingPoint.getBearing(new Coordinates(3, 4)), 0.000001);
    assertEquals(135, startingPoint.getBearing(new Coordinates(2, 4)), 0.000001);
    assertEquals(180, startingPoint.getBearing(new Coordinates(2, 3)), 0.000001);
    assertEquals(225, startingPoint.getBearing(new Coordinates(2, 2)), 0.000001);
    assertEquals(270, startingPoint.getBearing(new Coordinates(3, 2)), 0.000001);
    assertEquals(315, startingPoint.getBearing(new Coordinates(4, 2)), 0.000001);
  }
}
