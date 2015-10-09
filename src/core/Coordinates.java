package core;

public class Coordinates {
    private int x;
    private int y;

    public Coordinates(int xCoordinate, int yCoordinate) {
        x = xCoordinate;
        y = yCoordinate;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void moveUp() {
        y -= 1;
    }

    public void moveRight() {
        x += 1;
    }

    public void moveDown() {
        y += 1;
    }

    public void moveLeft() {
        x -= 1;
    }

    public void moveTowardsDestination(Coordinates destination) {
        int dx = destination.getX() - x;
        int dy = destination.getY() - y;

        if (Math.abs(dx) <= Math.abs(dy)) {
            if (dy >= 0) {
                moveUp();
            }
            else {
                moveDown();
            }
        }
        else {
            if (dx >= 0) {
                moveRight();
            }
            else {
                moveLeft();
            }
        }
    }
}
