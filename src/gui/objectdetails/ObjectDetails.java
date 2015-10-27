package gui.objectdetails;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import stopovers.CivilDestination;
import stopovers.Stopover;
import vehicles.Airplane;
import vehicles.CivilShip;
import vehicles.CivilVehicle;
import vehicles.Vehicle;

public class ObjectDetails {
  private Object object;
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

  public void setObject(Object object) {
    this.object = object;
    passengers.setPassengerZone(null);
    passengersHotel.setPassengerZone(null);


    if (object instanceof CivilVehicle) {
      passengers.setPassengerZone(((CivilVehicle) object).passengerZone());
      passengersHotel.setPassengerZone(null);
    }

    if (object instanceof CivilDestination) {
      passengers.setPassengerZone(((CivilDestination) object).passengerZone());
      passengersHotel.setPassengerZone(((CivilDestination) object).hotel());
    }
  }

  public void refresh() {
    clear();

    if (object instanceof Vehicle) {
      printVehicleInfo((Vehicle) object);
    }
    else if (object instanceof Stopover) {
      printStopoverInfo((Stopover) object);
    }

    passengers.refresh();
    passengersHotel.refresh();
  }

  private void printVehicleInfo(Vehicle vehicle) {
    title.setText("#" + Integer.toString(vehicle.getId()).substring(0, 4));
    coordinates.setText(vehicle.getCoordinates().toString());
    destinations.setItems(FXCollections.observableArrayList(vehicle.routeToStrings()));

    if (vehicle instanceof CivilShip) {
      subtitle.setText("Owned by: " + ((CivilShip) vehicle).getCompany());
    }

    if (vehicle instanceof Airplane) {
      personnel.setText("Personnel: " + ((Airplane) vehicle).getPersonnel());
      fuel.setText("Fuel: " + ((Airplane) vehicle).getFuel() + "%");
    }

    if (vehicle instanceof CivilVehicle) {
      nextDestination.setText("-> " + vehicle.getNextCivilStopover().getName());
      passengersLabel.setText("On board:");
    }
  }

  private void printStopoverInfo(Stopover stopover) {
    title.setText(stopover.getName());
    coordinates.setText(stopover.getCoordinates().toString());

    if (stopover instanceof CivilDestination) {
      passengersLabel.setText("Waiting for departure:");
      passengersHotelLabel.setText("At the hotel: ");
    }
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
