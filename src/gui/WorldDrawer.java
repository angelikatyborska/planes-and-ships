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

//  TODO: implement
public class WorldDrawer implements Drawer {
  private final WorldMap map;
  private final GraphicsContext gc;
  private final Image terrain;
  private final Image civilShip;
  private final Image civilAirplane;
  private final double height;
  private final double width;
  private final double offsetFromRoute = 6;

  public WorldDrawer(WorldMap map, GraphicsContext gc, double width, double height) {
    this.map = map;
    this.gc = gc;
    this.width = width;
    this.height = height;
    terrain = new Image("file:images/terrain.png");
    civilShip = new Image("file:images/civilship.png");
    civilAirplane = new Image("file:images/civilairplane.png");

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
    double x = stopover.getCoordinates().getX();
    double y = stopover.getCoordinates().getY();
    double a = 6;

    gc.setFill(Color.WHITE);
    gc.fillOval(x - a/2, y - a/2, a, a);
    gc.setFill(Color.BLACK);
    //gc.fillText(stopover.getName(), x, y);
  }

  @Override
  public void drawPort(Port stopover) {
    double x = stopover.getCoordinates().getX();
    double y = stopover.getCoordinates().getY();
    double a = 24;

    gc.setFill(Color.web("#0e3a5f"));
    gc.fillOval(x - a/2, y - a/2, a, a);
    gc.setFill(Color.BLACK);
    gc.setFill(Color.WHITE);
        gc.setFont(Font.font(13));

    gc.fillText("" + stopover.passengerZone().getPassengers().size(), x, y);
    drawNamePlate(stopover.getName(), x, y);
  }

  @Override
  public void drawAirport(Airport airport) {

  }

  @Override
  public void drawCivilAirport(CivilAirport stopover) {
    double x = stopover.getCoordinates().getX();
    double y = stopover.getCoordinates().getY();
    double a = 24;

    gc.setFill(Color.web("#065525"));
    gc.fillOval(x - a/2, y - a/2, a, a);
    gc.setFill(Color.WHITE);
        gc.setFont(Font.font(13));

    gc.fillText("" + stopover.passengerZone().getPassengers().size(), x, y);

    drawNamePlate(stopover.getName(), x, y);
  }

  @Override
  public void drawMilitaryAirport(MilitaryAirport stopover) {
    double x = stopover.getCoordinates().getX();
    double y = stopover.getCoordinates().getY();
    double a = 16;

    gc.setFill(Color.OLIVE);
    gc.fillOval(x - a/2, y - a/2, a, a);
    gc.setFill(Color.BLACK);
    //gc.fillText(stopover.getName(), x, y);
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
    //gc.fillText(Integer.toString(vehicle.getId()), x+8, y+8);
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
//    gc.setFill(Color.BLACK);
//    draw((Ship) vehicle);
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
    gc.setFill(Color.OLIVE);
    drawShip(vehicle);
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

  private void drawNamePlate(String s, double x, double y) {
    gc.setFill(Color.WHITE);
    double width = s.length() * 7;
    double height = 14;
    double topOffset = 12;
    gc.fillRect(x - width/2, y + topOffset, width, height);
    gc.setFill(Color.BLACK);
    gc.setFont(Font.font("Courier", 11));
    gc.fillText(s, x, y + topOffset + height/2);
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
