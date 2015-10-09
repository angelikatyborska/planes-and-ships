import static org.junit.Assert.assertEquals;
import org.junit.Test;
import core.Coordinates;

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
}
