package gui;

import core.Coordinates;
import core.Passenger;
import core.PassengerZone;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import stopovers.*;
import vehicles.*;

public class ObjectDetailDrawer implements Drawer {
  private final GraphicsContext gc;
  private final double width;
  private final double height;
  private Drawable object;
  private final Image paper;
  private double textTop = 50;
  private double lineHeight = 35;
  private double textLeft = 15;

  public ObjectDetailDrawer(GraphicsContext gc, double width, double height) {
    this.gc = gc;
    this.width = width;
    this.height = height;
    this.object = null;
    paper = new Image("file:images/paper.png");
  }

  public void setObject(Drawable object) {
    this.object = object;
  }

  public void draw() {
    gc.clearRect(0, 0, width, height);

    drawPanel();

    if (object != null) {
      object.draw(this);
    }
  }

  @Override
  public void drawVehicle(Vehicle vehicle) {
    drawTitle(vehicle.getClass().getName());
    drawCoordinates(vehicle.getCoordinates());
    drawNextDestination(vehicle.getNextCivilStopover().getName());
  }

  @Override
  public void drawAirplane(Airplane vehicle) {
    drawVehicle(vehicle);
  }

  @Override
  public void drawCivilAirplane(CivilAirplane vehicle) {
    drawAirplane(vehicle);
    listPassengers(vehicle.passengerZone());
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
    listPassengers(vehicle.passengerZone());
  }

  @Override
  public void drawMilitaryShip(MilitaryShip vehicle) {
    drawShip(vehicle);
  }

  @Override
  public void drawStopover(Stopover stopover) {
    drawTitle(stopover.getName());
    drawCoordinates(stopover.getCoordinates());
  }

  @Override
  public void drawAirport(Airport stopover) {
    drawStopover(stopover);
  }

  @Override
  public void drawCivilAirport(CivilAirport stopover) {
    drawAirport(stopover);
    listPassengers(stopover.passengerZone());
  }

  @Override
  public void drawMilitaryAirport(MilitaryAirport stopover) {
    drawAirport(stopover);
  }

  @Override
  public void drawPort(Port stopover) {
    drawStopover(stopover);
    listPassengers(stopover.passengerZone());
  }

  private void drawPanel() {
    gc.drawImage(paper, 0, 0);
  }

  private void drawTitle(String title) {
    gc.setFill(Color.BLACK);
    gc.setFont(Font.font("Courier", 30));
    gc.fillText(title, textLeft, textTop);
  }

  private void drawCoordinates(Coordinates coordinates) {
    gc.setFont(Font.font("Courier", 15));
    String s = (int) coordinates.getX() + ", " + (int) coordinates.getY();
    gc.fillText(s, textLeft, textTop + lineHeight);
  }

  private void listPassengers(PassengerZone passengerZone) {
    gc.setFont(Font.font("Courier", 12));
    double i = 0.5;
    for (Passenger passenger : passengerZone.getPassengers()) {
      String s = "- ";
      s += passenger.getFirstName().substring(0, 1) + ". ";
      s += passenger.getLastName();
      s += " -> " + passenger.getNextCivilStopover().getName();

      gc.fillText(s, textLeft, textTop + (i + 3) * lineHeight);
      i += 0.5;
    }
  }

  private void drawNextDestination(String nextDestination) {
    gc.setFont(Font.font("Courier", 15));
    gc.setFill(Color.BLACK);
    String s = "-> " + nextDestination;
    gc.fillText(s, textLeft, textTop + 2 * lineHeight);
  }
}
