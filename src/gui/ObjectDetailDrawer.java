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

  private void drawVehicle(Vehicle vehicle, Color color, Image image) {
    drawHeader(color, image, "#" + Integer.toString(vehicle.getId()).substring(0, 4));
    drawCoordinates(vehicle.getCoordinates());
  }


  @Override
  public void drawVehicle(Vehicle vehicle) {
    drawVehicle(vehicle, Color.BLACK, null);
  }

  @Override
  public void drawAirplane(Airplane vehicle) {
    drawVehicle(vehicle);
    drawNextDestination(vehicle.getNextAirport().getName());
  }

  @Override
  public void drawCivilAirplane(CivilAirplane vehicle) {
    drawVehicle(vehicle, colors.get("civilGreen"), images.get("airplane"));
    listPassengers(vehicle.passengerZone());
  }

  @Override
  public void drawMilitaryAirplane(MilitaryAirplane vehicle) {
    drawVehicle(vehicle, colors.get("military"), images.get("airplane"));
  }

  @Override
  public void drawShip(Ship vehicle) {
    drawVehicle(vehicle);
  }

  @Override
  public void drawCivilShip(CivilShip vehicle) {
    listPassengers(vehicle.passengerZone());
    drawVehicle(vehicle, colors.get("civilNavy"), images.get("ship"));
    drawNextDestination(vehicle.getNextPort().getName());
  }

  @Override
  public void drawMilitaryShip(MilitaryShip vehicle) {
    drawVehicle(vehicle, colors.get("military"), images.get("ship"));
  }

  public void drawStopover(Stopover stopover, Color color) {
    drawHeader(color, null, stopover.getName());
    drawCoordinates(stopover.getCoordinates());
  }

  @Override
  public void drawStopover(Stopover stopover) {
    drawStopover(stopover, Color.BLACK);
  }

  public void drawJunction(Junction stopover) {
    drawStopover(stopover, colors.get("junctionBeige"));
  }

  @Override
  public void drawAirport(Airport stopover) {
    drawStopover(stopover, colors.get("junctionBeige"));
  }

  @Override
  public void drawCivilAirport(CivilAirport stopover) {
    drawStopover(stopover, colors.get("civilGreen"));

    listPassengers(stopover.passengerZone(), stopover.hotel());
  }

  @Override
  public void drawMilitaryAirport(MilitaryAirport stopover) {
    drawStopover(stopover, colors.get("military"));
  }

  @Override
  public void drawPort(Port stopover) {
    drawStopover(stopover, colors.get("civilNavy"));
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
    gc.setFill(Color.BLACK);
    gc.setFont(Font.font(fontFamily, 15));
    String s = (int) coordinates.getX() + ", " + (int) coordinates.getY();
    gc.fillText(s, textLeft, textTop + lineHeight);
  }

  private void listPassengers(PassengerZone passengerZone) {
    listPassengers(passengerZone, null);
  }

  private void listPassengers(PassengerZone passengerZone, PassengerZone hotel) {
    gc.setFill(Color.BLACK);
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

  private void drawToken(Color color, Image image, double offset) {
    double top = 27;
    double radius = 15;
    double margin = 5;
    gc.setFill(color);
    gc.fillOval(textLeft + offset + margin, top, 2 * radius, 2 * radius);
    if (image != null) {
      gc.drawImage(image, textLeft + offset + margin + radius - image.getWidth() / 2, top + radius - image.getHeight() / 2);
    }
  }

  private void drawHeader(Color color, Image image, String s) {
    drawTitle(s);
    drawToken(color, image, s.length() * 19);

  }
}
