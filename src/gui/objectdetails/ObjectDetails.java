package gui.objectdetails;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
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


  public ObjectDetails() {

  }

  public void initialize() {
    setObject(null);
  }

  public void setObject(Object object) {
    this.object = object;
  }

  public void refresh() {
    clear();
    if (object instanceof Vehicle) {
      printVehicleInfo((Vehicle) object);
    }
    else if (object instanceof Stopover) {
      printStopoverInfo((Stopover) object);
    }
  }

  private void printVehicleInfo(Vehicle vehicle) {
    title.setText("#" + Integer.toString(vehicle.getId()).substring(0, 4));
    coordinates.setText(vehicle.getCoordinates().toString());

    if (vehicle instanceof CivilShip) {
      subtitle.setText("Owned by: " + ((CivilShip) vehicle).getCompany());
    }

    if (vehicle instanceof Airplane) {
      personnel.setText("Personnel: " + ((Airplane) vehicle).getPersonnel());
      fuel.setText("Fuel: " + ((Airplane) vehicle).getFuel() + "%");
    }

    if (vehicle instanceof CivilVehicle) {
      nextDestination.setText("-> " + vehicle.getNextCivilStopover().getName());
    }
  }

  private void printStopoverInfo(Stopover stopover) {
    title.setText(stopover.getName());
    coordinates.setText(stopover.getCoordinates().toString());
  }

  private void clear() {
    title.setText("");
    subtitle.setText("");
    coordinates.setText("");
    personnel.setText("");
    fuel.setText("");
    nextDestination.setText("");
  }
}
