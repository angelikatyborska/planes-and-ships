package gui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import stopovers.*;
import vehicles.*;
import world.WorldMap;

import java.util.ArrayList;
import java.util.List;

//  TODO: implement
public class WorldDrawer implements Runnable {
  private final WorldDrawerClock clock;
  private final Thread clockThread;
  private final WorldMap map;
  private final GraphicsContext gc;
  private final Image terrain;
  private final Image civilShip;
  private final Image civilAirplane;
  private final double height;
  private final double width;
  private final double offsetFromRoute = 6;

  public WorldDrawer(int fps, WorldMap map, GraphicsContext gc) {
    this.clock = new WorldDrawerClock(1000/fps, this);
    clockThread = new Thread(clock);
    clockThread.start();
    this.map = map;
    this.gc = gc;
    this.width = gc.getCanvas().getWidth();
    this.height = gc.getCanvas().getHeight();
    terrain = new Image("file:images/terrain.png");
    civilShip = new Image("file:images/civilship.png");
    civilAirplane = new Image("file:images/civilairplane.png");
  }

  public void draw() {
    gc.clearRect(0, 0, width, height);

    drawTerrain();
    drawVehicles();
    drawStopovers();

    gc.save();
  }

  public void draw(Stopover stopover) {
    double x = stopover.getCoordinates().getX();
    double y = stopover.getCoordinates().getY();
    double a = 6;

    gc.setFill(Color.WHITE);
    gc.fillOval(x - a/2, y - a/2, a, a);
    gc.setFill(Color.BLACK);
    //gc.fillText(stopover.getName(), x, y);
  }

  public void draw(Port stopover) {
    double x = stopover.getCoordinates().getX();
    double y = stopover.getCoordinates().getY();
    double a = 16;

    gc.setFill(Color.BLACK);
    gc.fillOval(x - a/2, y - a/2, a, a);
    gc.setFill(Color.BLACK);
    //gc.fillText(stopover.getName(), x, y);
  }

  public void draw(Airport airport) {

  }

  public void draw(CivilAirport stopover) {
    double x = stopover.getCoordinates().getX();
    double y = stopover.getCoordinates().getY();
    double a = 16;

    gc.setFill(Color.PURPLE);
    gc.fillOval(x - a/2, y - a/2, a, a);
    gc.setFill(Color.BLACK);
    //gc.fillText(stopover.getName(), x, y);
  }

  public void draw(MilitaryAirport stopover) {
    double x = stopover.getCoordinates().getX();
    double y = stopover.getCoordinates().getY();
    double a = 16;

    gc.setFill(Color.OLIVE);
    gc.fillOval(x - a/2, y - a/2, a, a);
    gc.setFill(Color.BLACK);
    //gc.fillText(stopover.getName(), x, y);
  }

  public void draw(Vehicle vehicle) {

  }

  public void draw(Airplane vehicle) {
    double x = vehicle.getCoordinates().getX();
    double y = vehicle.getCoordinates().getY();
    double a = 8;

    double angle = vehicle.getBearing();
    double dx = offsetFromRoute * Math.cos(angle);
    double dy = offsetFromRoute * Math.sin(angle);

    gc.fillOval(x + dx - a / 2, y + dy - a / 2, a, a);
    //gc.fillText(Integer.toString(vehicle.getId()), x+8, y+8);
  }

  public void draw(CivilAirplane vehicle) {
    double x = vehicle.getCoordinates().getX() - civilShip.getWidth()/2;
    double y = vehicle.getCoordinates().getY() - civilShip.getHeight()/2;

    double angle = vehicle.getBearing();
    double dx = offsetFromRoute * Math.cos(angle);
    double dy = offsetFromRoute * Math.sin(angle);
    gc.drawImage(civilAirplane, x + dx, y + dy);
  }

  public void draw(MilitaryAirplane vehicle) {
    gc.setFill(Color.OLIVE);
    draw((Airplane) vehicle);
  }

  public void draw(Ship vehicle) {
    double x = vehicle.getCoordinates().getX();
    double y = vehicle.getCoordinates().getY();
    double a = 8;

    double angle = vehicle.getBearing();
    double dx = offsetFromRoute * Math.cos(angle);
    double dy = offsetFromRoute * Math.sin(angle);

    gc.fillOval(x + dx - a / 2, y + dy - a / 2, a, a);
  }

  public void draw(CivilShip vehicle) {
//    gc.setFill(Color.BLACK);
//    draw((Ship) vehicle);
    double x = vehicle.getCoordinates().getX() - civilShip.getWidth()/2;
    double y = vehicle.getCoordinates().getY() - civilShip.getHeight()/2;

    double angle = vehicle.getBearing();
    double dx = offsetFromRoute * Math.cos(angle);
    double dy = offsetFromRoute * Math.sin(angle);

    gc.drawImage(civilShip, x + dx, y + dy);
  }

  public void draw(MilitaryShip vehicle) {
    gc.setFill(Color.OLIVE);
    draw((Ship) vehicle);
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

  private void drawStopovers() {
    double lineWidth = 1;
    gc.setStroke(Color.WHITE);
    gc.setLineWidth(lineWidth);
    gc.setLineDashes(4, 10);

    List<Stopover> alreadyDrewLinesToNeighbours = new ArrayList<>();

    for (Stopover stopover : map.getAllStopovers()) {
      double x1 = stopover.getCoordinates().getX();
      double y1 = stopover.getCoordinates().getY();

      for (Stopover neighbour : map.getNeighbouringStopovers(stopover)) {
        // don't draw the same line twice
        if (!alreadyDrewLinesToNeighbours.contains(neighbour)) {
          double x2 = neighbour.getCoordinates().getX();
          double y2 = neighbour.getCoordinates().getY();
          gc.strokeLine(x1 - lineWidth / 2, y1 - lineWidth / 2, x2 - lineWidth / 2, y2 - lineWidth / 2);
        }
      }
      alreadyDrewLinesToNeighbours.add(stopover);
    }

    for (Stopover stopover : map.getAllStopovers()) {
      stopover.draw(this);
    }
  }

  private void drawVehicles() {
    for (Vehicle vehicle : map.getAllVehicles()) {
      vehicle.draw(this);
    }
  }

  private void drawTerrain() {
    gc.drawImage(terrain, 0, 0);
  }
}
