package gui.objectdetails;

import core.Passenger;
import core.PassengerZone;
import gui.passport.Passport;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PassengerList extends ListView {
  static class PassengerCell extends ListCell<Passenger> {
    @Override
    public void updateItem(Passenger passenger, boolean empty) {
      super.updateItem(passenger, empty);
      if (passenger != null) {
        String s = passenger.getFirstName().substring(0, 1) + ". " + passenger.getLastName() + " -> " + passenger.getNextCivilStopover().getName();
        setText(s);
      } else {
        setText("");
      }
      setOnMouseClicked(e -> {
        openPassengerInfoWindow(passenger);
      });
    }
  }

  private List<Passenger> passengers;

  public PassengerList() {
    passengers = new ArrayList<>();

    setCellFactory(list -> new PassengerCell());
  }

  public void setPassengerZone(PassengerZone passengerZone) {
    synchronized (this) {
      if (passengerZone != null) {
        passengers = passengerZone.getPassengers();
        setVisible(true);
      } else {
        passengers = new ArrayList<>();
        setVisible(false);
      }
    }
  }

  // TODO: how can I avoid having to manually refresh the ListView?
  // I cannot use the goodies of ObservableList, because the list has to be created in Passenger class
  // and Passenger is not a Java FX Application thread. WARNING! - side effect - no state (no selected item)
  public void refresh() {
    super.refresh();
    setItems(FXCollections.observableArrayList(passengers));
  }

  private static void openPassengerInfoWindow(Passenger passenger) {
    Stage stage = new Stage();
    stage.setTitle(passenger.getFirstName() + " " + passenger.getLastName());

    GridPane passportPane = new GridPane();

    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(Passport.class.getResource("passport.fxml"));
    try {
      passportPane = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    Passport passport = loader.getController();
    passport.setPassenger(passenger);

    Scene scene = new Scene(passportPane);

    stage.setScene(scene);
    stage.show();
  }
}
