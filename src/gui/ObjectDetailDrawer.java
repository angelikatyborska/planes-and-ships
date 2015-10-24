package gui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import stopovers.*;
import vehicles.*;

public class ObjectDetailDrawer implements Drawer, Runnable {
  private final GraphicsContext gc;
  private final double width;
  private final double height;
  private Drawable object;

  public ObjectDetailDrawer(GraphicsContext gc, double width, double height) {
    this.gc = gc;
    this.width = width;
    this.height = height;
    this.object = null;
  }

  public void setObject(Drawable object) {
    this.object = object;
  }

  public void draw() {
    drawPanel();
    if (object != null) {
      object.draw(this);
    }

    gc.setFill(Color.YELLOW);
    gc.fillRect(100, 100, 30, 50);
    gc.fillText("HAHAHAHA", 150, 150);
    drawTitle("HOHOHO");
  }

  @Override
  public void drawVehicle(Vehicle vehicle) {
    drawTitle(vehicle.toString());
  }

  @Override
  public void drawAirplane(Airplane vehicle) {
    drawVehicle(vehicle);
  }

  @Override
  public void drawCivilAirplane(CivilAirplane vehicle) {
    drawAirplane(vehicle);
  }

  @Override
  public void drawMilitaryAirplane(MilitaryAirplane vehicle) {
    drawAirplane(vehicle);
  }

  @Override
  public void drawShip(Ship vehicle) {
    drawVehicle(vehicle);
  }

  @Override
  public void drawCivilShip(CivilShip vehicle) {
    drawShip(vehicle);
  }

  @Override
  public void drawMilitaryShip(MilitaryShip vehicle) {
    drawShip(vehicle);
  }

  @Override
  public void drawStopover(Stopover stopover) {
    drawTitle(stopover.getCoordinates().toString());
  }

  @Override
  public void drawAirport(Airport stopover) {

  }

  @Override
  public void drawCivilAirport(CivilAirport stopover) {

  }

  @Override
  public void drawMilitaryAirport(MilitaryAirport stopover) {

  }

  @Override
  public void drawPort(Port port) {

  }

  private void drawPanel() {
    gc.setFill(Color.BROWN);
    gc.fillRect(0, 0, width, height);
  }

  private void drawTitle(String title) {
    gc.setFill(Color.WHITE);
    gc.fillText(title, 40, 40);
  }

  @Override
  public void run() {
    while(!Thread.currentThread().isInterrupted()) {
      synchronized (this) {
        try {
          wait();
        }
        catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
      draw();
    }
  }
}
