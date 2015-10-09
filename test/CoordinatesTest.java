import static org.junit.Assert.assertEquals;
import org.junit.Test;
import core.Coordinates;

public class CoordinatesTest {
    @Test
    public void shouldSetXAndYCoordinates() {
        Coordinates coordinates = new Coordinates(1, 2);
        assertEquals(1, coordinates.getX());
        assertEquals(2, coordinates.getY());
    }

    @Test
    public void shouldMoveUp() {
        Coordinates coordinates = new Coordinates(1, 1);
        int oldX = coordinates.getX();
        int oldY = coordinates.getY();

        coordinates.moveUp();

        int newX = coordinates.getX();
        int newY = coordinates.getY();

        assertEquals(oldX, newX);
        assertEquals(oldY - 1, newY);
    }

    @Test
    public void shouldMoveRight() {
        Coordinates coordinates = new Coordinates(1, 1);
        int oldX = coordinates.getX();
        int oldY = coordinates.getY();

        coordinates.moveRight();

        int newX = coordinates.getX();
        int newY = coordinates.getY();

        assertEquals(oldX + 1, newX);
        assertEquals(oldY, newY);
    }

    @Test
    public void shouldMoveDown() {
        Coordinates coordinates = new Coordinates(1, 1);
        int oldX = coordinates.getX();
        int oldY = coordinates.getY();

        coordinates.moveDown();

        int newX = coordinates.getX();
        int newY = coordinates.getY();

        assertEquals(oldX, newX);
        assertEquals(oldY + 1, newY);
    }

    @Test
    public void shouldMoveLeft() {
        Coordinates coordinates = new Coordinates(1, 1);
        int oldX = coordinates.getX();
        int oldY = coordinates.getY();

        coordinates.moveLeft();

        int newX = coordinates.getX();
        int newY = coordinates.getY();

        assertEquals(oldX - 1, newX);
        assertEquals(oldY, newY);
    }

    @Test
    public void shouldMoveUpTowardsDestination() {
        Coordinates coordinates1 = new Coordinates(10, 10);
        Coordinates coordinates2 = new Coordinates(10, 10);
        Coordinates destination = new Coordinates (10, 15);

        coordinates1.moveTowardsDestination(destination);
        coordinates2.moveUp();

        assertEquals(coordinates1.getX(), coordinates2.getX());
        assertEquals(coordinates1.getY(), coordinates2.getY());
    }

    @Test
    public void shouldMoveRightTowardsDestination() {
        Coordinates coordinates1 = new Coordinates(10, 10);
        Coordinates coordinates2 = new Coordinates(10, 10);
        Coordinates destination = new Coordinates (15, 10);

        coordinates1.moveTowardsDestination(destination);
        coordinates2.moveRight();

        assertEquals(coordinates1.getX(), coordinates2.getX());
        assertEquals(coordinates1.getY(), coordinates2.getY());
    }

    @Test
    public void shouldMoveDownTowardsDestination() {
        Coordinates coordinates1 = new Coordinates(10, 10);
        Coordinates coordinates2 = new Coordinates(10, 10);
        Coordinates destination = new Coordinates (10, 5);

        coordinates1.moveTowardsDestination(destination);
        coordinates2.moveDown();

        assertEquals(coordinates1.getX(), coordinates2.getX());
        assertEquals(coordinates1.getY(), coordinates2.getY());
    }

    @Test
    public void shouldMoveLeftTowardsDestination() {
        Coordinates coordinates1 = new Coordinates(10, 10);
        Coordinates coordinates2 = new Coordinates(10, 10);
        Coordinates destination = new Coordinates (5, 10);

        coordinates1.moveTowardsDestination(destination);
        coordinates2.moveLeft();

        assertEquals(coordinates1.getX(), coordinates2.getX());
        assertEquals(coordinates1.getY(), coordinates2.getY());
    }
}
