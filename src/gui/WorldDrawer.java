package gui;

import core.Passenger;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import stopovers.*;
import vehicles.*;
import world.WorldMap;

import java.util.ArrayList;
import java.util.List;

public class WorldDrawer implements Drawer {
  private final WorldMap map;
  private final GraphicsContext gc;
  private final double height;
  private final double width;
  private final double offsetFromRoute = 6;
  private final Image terrain = new Image("/images/terrain.png");
  private final Image civilShip = new Image("/images/civilship.png");
  private final Image civilAirplane = new Image("/images/civilairplane.png");
  private final Color civilNavy = Color.web("#0e3a5f");
  private final Color civilGreen = Color.web("#065525");
  private final String fontFamily = "Courier";

  public WorldDrawer(WorldMap map, GraphicsContext gc, double width, double height) {
    this.map = map;
    this.gc = gc;
    this.width = width;
    this.height = height;
    gc.setTextAlign(TextAlignment.CENTER);
    gc.setTextBaseline(VPos.CENTER);
  }

  public void draw() {
    gc.clearRect(0, 0, width, height);

    drawTerrain();
    drawVehicles();
    drawStopovers();
  }

  @Override
  public void drawStopover(Stopover stopover) {
    drawStopover(stopover, 6, Color.WHITE);
  }

  @Override
  public void drawPort(Port stopover) {
    double x = stopover.getCoordinates().getX();
    double y = stopover.getCoordinates().getY();

    drawStopover(stopover, 24, civilNavy);
    drawPassengerCounter(stopover, x, y);
    drawNamePlate(stopover);
  }

  @Override
  public void drawAirport(Airport stopover) {
    drawStopover(stopover, 16, Color.GRAY);
  }

  @Override
  public void drawCivilAirport(CivilAirport stopover) {
    double x = stopover.getCoordinates().getX();
    double y = stopover.getCoordinates().getY();

    drawStopover(stopover, 24, civilGreen);
    drawPassengerCounter(stopover, x, y);
    drawNamePlate(stopover);
  }

  @Override
  public void drawMilitaryAirport(MilitaryAirport stopover) {
    drawStopover(stopover, 16, Color.DARKOLIVEGREEN);
  }

  @Override
  public void drawVehicle(Vehicle vehicle) {

  }

  @Override
  public void drawAirplane(Airplane vehicle) {
    double x = vehicle.getCoordinates().getX();
    double y = vehicle.getCoordinates().getY();
    double a = 8;

    double angle = vehicle.getBearing();
    double dx = offsetFromRoute * Math.cos(angle);
    double dy = offsetFromRoute * Math.sin(angle);

    gc.fillOval(x + dx - a / 2, y + dy - a / 2, a, a);
  }

  @Override
  public void drawCivilAirplane(CivilAirplane vehicle) {
    double x = vehicle.getCoordinates().getX() - civilShip.getWidth()/2;
    double y = vehicle.getCoordinates().getY() - civilShip.getHeight()/2;

    double angle = vehicle.getBearing();
    double dx = offsetFromRoute * Math.cos(angle);
    double dy = offsetFromRoute * Math.sin(angle);
    gc.drawImage(civilAirplane, x + dx, y + dy);

    gc.setFill(Color.BLACK);
    gc.fillText("" + vehicle.passengerZone().getPassengers().size() + "/" + vehicle.passengerZone().getCapacity(),  x + dx, y + dy);
  }

  @Override
  public void drawMilitaryAirplane(MilitaryAirplane vehicle) {
    gc.setFill(Color.OLIVE);
    drawAirplane(vehicle);
  }

  @Override
  public void drawShip(Ship vehicle) {
    double x = vehicle.getCoordinates().getX();
    double y = vehicle.getCoordinates().getY();
    double a = 8;

    double angle = vehicle.getBearing();
    double dx = offsetFromRoute * Math.cos(angle);
    double dy = offsetFromRoute * Math.sin(angle);

    gc.fillOval(x + dx - a / 2, y + dy - a / 2, a, a);
  }

  @Override
  public void drawCivilShip(CivilShip vehicle) {
    double x = vehicle.getCoordinates().getX() - civilShip.getWidth()/2;
    double y = vehicle.getCoordinates().getY() - civilShip.getHeight()/2;

    double angle = vehicle.getBearing();
    double dx = offsetFromRoute * Math.cos(angle);
    double dy = offsetFromRoute * Math.sin(angle);

    gc.drawImage(civilShip, x + dx, y + dy);
    gc.setFill(Color.BLACK);
    gc.fillText("" + vehicle.passengerZone().getPassengers().size() + "/" + vehicle.passengerZone().getCapacity(),  x + dx, y + dy);
  }

  @Override
  public void drawMilitaryShip(MilitaryShip vehicle) {
    double x = vehicle.getCoordinates().getX() - civilShip.getWidth()/2;
    double y = vehicle.getCoordinates().getY() - civilShip.getHeight()/2;

    double angle = vehicle.getBearing();
    double dx = offsetFromRoute * Math.cos(angle);
    double dy = offsetFromRoute * Math.sin(angle);

    gc.drawImage(civilShip, x + dx, y + dy);
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

  private void drawNamePlate(Stopover stopover) {
    double x = stopover.getCoordinates().getX();
    double y = stopover.getCoordinates().getY();

    double width = stopover.getName().length() * 7;
    double height = 14;
    double topOffset = 12;

    gc.setFill(Color.WHITE);
    gc.fillRect(x - width/2, y + topOffset, width, height);
    gc.setFill(Color.BLACK);
    gc.setFont(Font.font(fontFamily, 11));
    gc.fillText(stopover.getName(), x, y + topOffset + height/2);
  }
  private void drawVehicles() {
    for (Vehicle vehicle : map.getAllVehicles()) {
      vehicle.draw(this);
    }
  }

  private void drawTerrain() {
    gc.drawImage(terrain, 0, 0);
  }

  private void drawStopover(Stopover stopover, double radius, Color color) {
    double x = stopover.getCoordinates().getX();
    double y = stopover.getCoordinates().getY();

    gc.setFill(color);
    gc.fillOval(x - radius/2, y - radius/2, radius, radius);
  }

  private void drawPassengerCounter(CivilDestination stopover, double x, double y) {
    gc.setFill(Color.WHITE);
    gc.setFont(Font.font(fontFamily, 13));

    int howManyPassengers = stopover.passengerZone().getPassengers().size() + stopover.hotel().getPassengers().size();
    gc.fillText("" + howManyPassengers, x, y);
  }

}
