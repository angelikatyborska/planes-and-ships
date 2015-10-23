package gui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import stopovers.*;
import vehicles.*;
import world.World;
import world.WorldClock;

//  TODO: implement
public class WorldDrawer implements Runnable {
  private final WorldDrawerClock clock;
  private final Thread clockThread;
  private final World world;
  private final GraphicsContext gc;
  private final double height;
  private final double width;

  public WorldDrawer(int fps, World world, GraphicsContext gc) {
    this.clock = new WorldDrawerClock(1000/fps, this);
    clockThread = new Thread(clock);
    clockThread.start();
    this.world = world;
    this.gc = gc;
    this.width = gc.getCanvas().getWidth();
    this.height = gc.getCanvas().getHeight();
  }

  public void draw() {
    gc.clearRect(0, 0, width, height);
    drawTerrain();

    int i = 0;
    for (Stopover stopover : world.getAllStopovers()) {
      stopover.draw(this);
    }

    for (Vehicle vehicle : world.getAllVehicles()) {
      vehicle.draw(this);
    }

    gc.save();
  }

  public void draw(Stopover stopover) {

  }

  public void draw(Port stopover) {

  }

  public void draw(Airport airport) {

  }

  public void draw(CivilAirport stopover) {
    double x = stopover.getCoordinates().getX();
    double y = stopover.getCoordinates().getY();

    gc.setFill(Color.ORANGE);
    gc.fillOval(x-6, y-6, 12, 12);
  }

  public void draw(MilitaryAirport stopover) {
    double x = stopover.getCoordinates().getX();
    double y = stopover.getCoordinates().getY();

    gc.setFill(Color.BLACK);
    gc.fillOval(x-2, y-2, 4, 4);
  }

  public void draw(Vehicle vehicle) {

  }

  public void draw(CivilAirplane vehicle) {
    double x = vehicle.getCoordinates().getX();
    double y = vehicle.getCoordinates().getY();

    gc.setFill(Color.YELLOW);
    gc.fillRect(x-4, y-4, 8, 8);
  }

  public void draw(MilitaryAirplane vehicle) {

  }

  public void draw(CivilShip vehicle) {

  }

  public void draw(MilitaryShip vehicle) {

  }

  private void drawTerrain() {
    gc.setFill(Color.GREEN);
    gc.fillRect(0, 0, width, height);
    gc.setFill(Color.AQUA);
    gc.fillOval(width/4, height/4, width/2, height/2);
  }

  @Override
  public void run() {
    while(!Thread.currentThread().isInterrupted()) {
      synchronized (this) {
        try {
          wait();
          draw();
        } catch (InterruptedException e) {
          clockThread.interrupt();
          Thread.currentThread().interrupt();
        }
      }
    }
  }
}
