package gui.objectdetails;

import gui.canvas.Drawable;
import gui.canvas.Drawer;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import stopovers.*;
import vehicles.*;

/**
 * A Drawer that can print details about a given Drawable object
 * @see Drawer
 * @see Drawable
 */
public class ObjectDetails implements Drawer {
  private Drawable object;
  @FXML private Label title;
  @FXML private Label subtitle;
  @FXML private Label coordinates;
  @FXML private Label personnel;
  @FXML private Label fuel;
  @FXML private Label nextDestination;
  @FXML private ListView destinations;
  @FXML private Label passengersLabel;
  @FXML private PassengerList passengers;
  @FXML private Label passengersHotelLabel;
  @FXML private PassengerList passengersHotel;


  public ObjectDetails() { }

  public void initialize() {
    setObject(null);
  }

  /**
   *
   * @param object The drawable object whose details will be printed when drawer is refreshed
   */
  public void setObject(Drawable object) {
    this.object = object;

    if (object instanceof CivilVehicle) {
      passengers.setPassengerZone(((CivilVehicle) object).passengerZone());
      passengersHotel.setPassengerZone(null);
    }
    else if (object instanceof CivilDestination) {
      passengers.setPassengerZone(((CivilDestination) object).passengerZone());
      passengersHotel.setPassengerZone(((CivilDestination) object).hotel());
    }
    else {
      passengers.setPassengerZone(null);
      passengersHotel.setPassengerZone(null);
    }
  }

  /**
   * Reprint with fresh information
   */
  public void refresh() {
    clear();

    if (object != null) {
      object.draw(this);
    }

    passengers.refresh();
    passengersHotel.refresh();
  }

  @Override
  public void drawVehicle(Vehicle vehicle) {
    title.setText("#" + Integer.toString(vehicle.getId()).substring(0, 4));
    coordinates.setText(vehicle.getCoordinates().toString());
    destinations.setItems(FXCollections.observableArrayList(vehicle.routeToStrings()));
    int worldClockTicksPerSecond = 20;
    fuel.setText(String.format("%.3g", vehicle.getVelocity() * worldClockTicksPerSecond) + " pixels/s");
  }

  @Override
  public void drawAirplane(Airplane vehicle) {
    drawVehicle(vehicle);
    personnel.setText("Personnel: " + vehicle.getPersonnel());
    fuel.setText(fuel.getText() + ", Fuel: " + vehicle.getFuel() + "%");
  }

  @Override
  public void drawCivilAirplane(CivilAirplane vehicle) {
    drawAirplane(vehicle);
    nextDestination.setText("-> " + vehicle.getNextCivilStopover().getName());
    passengersLabel.setText("On board:");
  }

  @Override
  public void drawMilitaryAirplane(MilitaryAirplane vehicle) {
    drawAirplane(vehicle);
    personnel.setText(personnel.getText() + ", weapon: " + vehicle.getWeapon());
  }

  @Override
  public void drawShip(Ship vehicle) {
    drawVehicle(vehicle);
  }

  @Override
  public void drawCivilShip(CivilShip vehicle) {
    drawShip(vehicle);
    subtitle.setText("Owned by: " + vehicle.getCompany());
    nextDestination.setText("-> " + vehicle.getNextCivilStopover().getName());
    passengersLabel.setText("On board:");
  }

  @Override
  public void drawMilitaryShip(MilitaryShip vehicle) {
    drawShip(vehicle);
    personnel.setText("weapon: " + vehicle.getWeapon());
  }

  @Override
  public void drawStopover(Stopover stopover) {
    title.setText(stopover.getName());
    coordinates.setText(stopover.getCoordinates().toString());
  }

  @Override
  public void drawAirport(Airport stopover) {
    drawStopover(stopover);
  }

  @Override
  public void drawCivilAirport(CivilAirport stopover) {
    drawAirport(stopover);
    passengersLabel.setText("Waiting for departure:");
    passengersHotelLabel.setText("At the hotel: ");
  }

  @Override
  public void drawMilitaryAirport(MilitaryAirport stopover) {
    drawAirport(stopover);
  }

  @Override
  public void drawPort(Port stopover) {
    drawStopover(stopover);
    passengersLabel.setText("Waiting for departure:");
    passengersHotelLabel.setText("At the hotel: ");
  }

  @Override
  public void drawJunction(Junction stopover) {
    drawStopover(stopover);
  }

  private void clear() {
    title.setText("");
    subtitle.setText("");
    coordinates.setText("");
    personnel.setText("");
    fuel.setText("");
    nextDestination.setText("");
    destinations.setItems(FXCollections.observableArrayList());
    passengersLabel.setText("");
    passengersHotelLabel.setText("");
  }
}
