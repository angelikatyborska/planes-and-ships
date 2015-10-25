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

import java.util.HashMap;

public class ObjectDetailDrawer implements Drawer {
  private final GraphicsContext gc;
  private final double width;
  private final double height;
  private Drawable object;
  private double textTop = 50;
  private double lineHeight = 35;
  private double textLeft = 15;
  private final String fontFamily = "Courier";
  private final HashMap<String, Image> images;
  private final HashMap<String, Color> colors;

  public ObjectDetailDrawer(GraphicsContext gc, HashMap<String, Image> images, HashMap<String, Color> colors, double width, double height) {
    this.gc = gc;
    this.width = width;
    this.height = height;
    this.object = null;
    this.colors = colors;
    this.images = images;
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
    drawTitle("#" + Integer.toString(vehicle.getId()).substring(0, 4));
    drawCoordinates(vehicle.getCoordinates());
  }

  @Override
  public void drawAirplane(Airplane vehicle) {
    drawVehicle(vehicle);
    drawNextDestination(vehicle.getNextAirport().getName());
  }

  @Override
  public void drawCivilAirplane(CivilAirplane vehicle) {
    drawAirplane(vehicle);
    listPassengers(vehicle.passengerZone());
    drawVehicleToken(colors.get("civilGreen"), images.get("airplane'"));
  }

  @Override
  public void drawMilitaryAirplane(MilitaryAirplane vehicle) {
    drawAirplane(vehicle);
    drawVehicleToken(colors.get("military"), images.get("airplane'"));
  }

  @Override
  public void drawShip(Ship vehicle) {
    drawVehicle(vehicle);
  }

  @Override
  public void drawCivilShip(CivilShip vehicle) {
    drawShip(vehicle);
    listPassengers(vehicle.passengerZone());
    drawVehicleToken(colors.get("civilNavy"), images.get("ship"));
    drawNextDestination(vehicle.getNextPort().getName());
  }

  @Override
  public void drawMilitaryShip(MilitaryShip vehicle) {
    drawShip(vehicle);
    drawVehicleToken(colors.get("military"), images.get("ship"));
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
    listPassengers(stopover.passengerZone(), stopover.hotel());
  }

  @Override
  public void drawMilitaryAirport(MilitaryAirport stopover) {
    drawAirport(stopover);
  }

  @Override
  public void drawPort(Port stopover) {
    drawStopover(stopover);
    listPassengers(stopover.passengerZone(), stopover.hotel());
  }

  private void drawPanel() {
    gc.drawImage(images.get("paper"), 0, 0);
  }

  private void drawTitle(String title) {
    gc.setFill(Color.BLACK);
    gc.setFont(Font.font(fontFamily, 30));
    gc.fillText(title, textLeft, textTop);
  }

  private void drawCoordinates(Coordinates coordinates) {
    gc.setFont(Font.font(fontFamily, 15));
    String s = (int) coordinates.getX() + ", " + (int) coordinates.getY();
    gc.fillText(s, textLeft, textTop + lineHeight);
  }

  private void listPassengers(PassengerZone passengerZone) {
    listPassengers(passengerZone, null);
  }

  private void listPassengers(PassengerZone passengerZone, PassengerZone hotel) {
    gc.setFont(Font.font(fontFamily, 12));

    gc.fillText("Boarding area:", textLeft, textTop + 3 * lineHeight);

    double i = 0.5;
    for (Passenger passenger : passengerZone.getPassengers()) {
      String s = "- ";
      s += passenger.getFirstName().substring(0, 1) + ". ";
      s += passenger.getLastName();
      s += " -> " + passenger.getNextCivilStopover().getName();

      gc.fillText(s, textLeft, textTop + (i + 3) * lineHeight);
      i += 0.5;
    }

    if (hotel != null) {
      gc.fillText("Hotel:", textLeft, textTop + (i + 3.5) * lineHeight);

      for (Passenger passenger : hotel.getPassengers()) {
        String s = "- ";
        s += passenger.getFirstName().substring(0, 1) + ". ";
        s += passenger.getLastName();
        s += " -> " + passenger.getNextCivilStopover().getName();

        gc.fillText(s, textLeft, textTop + (i + 4) * lineHeight);
        i += 0.5;
      }
    }
  }

  private void drawNextDestination(String nextDestination) {
    gc.setFont(Font.font(fontFamily, 15));
    gc.setFill(Color.BLACK);
    String s = "-> " + nextDestination;
    gc.fillText(s, textLeft, textTop + 2 * lineHeight);
  }

  private void drawVehicleToken(Color color, Image image) {
    double top = 5;
    double radius = 15;
    double right = 50;
    gc.setFill(color);
    gc.fillOval(width - right, top, 2 * radius, 2 * radius);
    gc.drawImage(image, width - right + radius - image.getWidth()/2, top + radius - image.getHeight()/2);
  }
  }
