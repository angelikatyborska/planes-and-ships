package gui.canvas;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import stopovers.*;
import vehicles.*;
import world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WorldDrawer implements Drawer {
  private final World world;
  private final GraphicsContext gc;
  private final double height;
  private final double width;
  private final double offsetFromRoute = 6;
  private final String fontFamily = "Courier";
  private final HashMap<String, Image> images;
  private final HashMap<String, Color> colors;
  private Vehicle selectedVehicle;

  public WorldDrawer(World world, GraphicsContext gc, HashMap<String, Image> images, HashMap<String, Color> colors, double width, double height) {
    this.world = world;
    this.gc = gc;
    this.width = width;
    this.height = height;
    this.colors = colors;
    this.images = images;
    gc.setTextAlign(TextAlignment.CENTER);
    gc.setTextBaseline(VPos.CENTER);
  }

  public void draw() {
    gc.clearRect(0, 0, width, height);

    drawTerrain();
    drawNetwork();
    drawStopovers();
    drawVehicles();
  }


  public void setVehicle(Vehicle vehicle) {
    selectedVehicle = vehicle;
  }

  @Override
  public void drawStopover(Stopover stopover) {
    drawStopover(stopover, 6, colors.get("junctionBeige"));
  }

  @Override
  public void drawJunction(Junction stopover) {
    drawStopover(stopover, 6, colors.get("junctionBeige"));
  }

  @Override
  public void drawPort(Port stopover) {
    double x = stopover.getCoordinates().getX();
    double y = stopover.getCoordinates().getY();

    drawStopover(stopover, 24, colors.get("civilNavy"));
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

    drawStopover(stopover, 24, colors.get("civilGreen"));
    drawPassengerCounter(stopover, x, y);
    drawNamePlate(stopover);
  }

  @Override
  public void drawMilitaryAirport(MilitaryAirport stopover) {
    drawStopover(stopover, 16, colors.get("military"));
  }

  @Override
  public void drawVehicle(Vehicle vehicle) {
    if (vehicle == selectedVehicle) {
      int radius = 16;
      double x = vehicle.getCoordinates().getX() - radius;
      double y = vehicle.getCoordinates().getY() - radius;

      double angle = vehicle.getBearing();
      double dx = offsetFromRoute * Math.cos(angle);
      double dy = offsetFromRoute * Math.sin(angle);

      gc.setStroke(Color.WHITE);
      gc.setLineDashes();
      gc.strokeOval(x + dx, y + dy, 2 * radius, 2 * radius);
    }
  }

  @Override
  public void drawAirplane(Airplane vehicle) {
    double x = vehicle.getCoordinates().getX() - images.get("airplane").getWidth()/2;
    double y = vehicle.getCoordinates().getY() - images.get("airplane").getHeight()/2;

    double angle = vehicle.getBearing();
    double dx = offsetFromRoute * Math.cos(angle);
    double dy = offsetFromRoute * Math.sin(angle);

    drawVehicle(vehicle);

    gc.drawImage(images.get("airplane"), x + dx, y + dy);
  }

  @Override
  public void drawCivilAirplane(CivilAirplane vehicle) {
    double x = vehicle.getCoordinates().getX() - images.get("airplane").getWidth()/2;
    double y = vehicle.getCoordinates().getY() - images.get("airplane").getHeight()/2;

    double angle = vehicle.getBearing();
    double dx = offsetFromRoute * Math.cos(angle);
    double dy = offsetFromRoute * Math.sin(angle);

    drawAirplane(vehicle);

    gc.setFill(Color.BLACK);
    gc.fillText("" + vehicle.passengerZone().getPassengers().size() + "/" + vehicle.passengerZone().getCapacity(),  x + dx, y + dy);
  }

  @Override
  public void drawMilitaryAirplane(MilitaryAirplane vehicle) {
    drawAirplane(vehicle);
  }

  @Override
  public void drawShip(Ship vehicle) {
    double x = vehicle.getCoordinates().getX() - images.get("ship").getWidth()/2;
    double y = vehicle.getCoordinates().getY() - images.get("ship").getHeight()/2;

    double angle = vehicle.getBearing();
    double dx = offsetFromRoute * Math.cos(angle);
    double dy = offsetFromRoute * Math.sin(angle);

    drawVehicle(vehicle);

    gc.drawImage(images.get("ship"), x + dx, y + dy);
  }

  @Override
  public void drawCivilShip(CivilShip vehicle) {
    double x = vehicle.getCoordinates().getX() - images.get("ship").getWidth()/2;
    double y = vehicle.getCoordinates().getY() - images.get("ship").getHeight()/2;

    double angle = vehicle.getBearing();
    double dx = offsetFromRoute * Math.cos(angle);
    double dy = offsetFromRoute * Math.sin(angle);

    drawShip(vehicle);

    gc.setFill(Color.BLACK);
    gc.fillText("" + vehicle.passengerZone().getPassengers().size() + "/" + vehicle.passengerZone().getCapacity(),  x + dx, y + dy);
  }

  @Override
  public void drawMilitaryShip(MilitaryShip vehicle) {
    drawShip(vehicle);
  }

  private void drawNetwork() {
    double lineWidth = 1;
    gc.setStroke(Color.WHITE);
    gc.setLineWidth(lineWidth);
    gc.setLineDashes(4, 10);

    List<Stopover> alreadyDrewLinesToNeighbours = new ArrayList<>();

    for (Stopover stopover : world.getAllStopovers()) {
      double x1 = stopover.getCoordinates().getX();
      double y1 = stopover.getCoordinates().getY();

      for (Stopover neighbour : world.getNeighbouringStopovers(stopover)) {
        // don't draw the same line twice
        if (!alreadyDrewLinesToNeighbours.contains(neighbour)) {
          double x2 = neighbour.getCoordinates().getX();
          double y2 = neighbour.getCoordinates().getY();
          gc.strokeLine(x1 - lineWidth / 2, y1 - lineWidth / 2, x2 - lineWidth / 2, y2 - lineWidth / 2);
        }
      }
      alreadyDrewLinesToNeighbours.add(stopover);
    }
  }

  private void drawStopovers() {
    for (Stopover stopover : world.getAllStopovers()) {
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
    for (Vehicle vehicle : world.getAllVehicles()) {
      vehicle.draw(this);
    }
  }

  private void drawTerrain() {
    gc.drawImage(images.get("terrain"), 0, 0);
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
