package gui;

import core.Passenger;
import core.PassengerZone;
import gui.passport.Passport;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PassengerList extends ListView {
  static class PassengerCell extends ListCell<Passenger> {
    @Override
    public void updateItem(Passenger passenger, boolean empty) {
      // for some magic reason -fx-font-family in css doesn't want to accept any value, so I have to set the font here
      super.updateItem(passenger, empty);
      if (passenger != null) {
        String s = passenger.getFirstName().substring(0, 1) + ". " + passenger.getLastName() + " -> " + passenger.getNextCivilStopover().getName();
        setText(s);
      }
      else {
        setText("");
      }
      setOnMouseClicked(e -> {
        if (e.getClickCount() > 1) {
          openPassengerInfoWindow(passenger);
        }
      });
    }
  }

  private List<Passenger> passengers;

  public PassengerList(double width, double x, double y) {
    setPrefWidth(width);
    setPrefHeight(105);
    setLayoutX(x);
    setLayoutY(y);
    setBackground(Background.EMPTY);
    passengers = new ArrayList<>();

    setCellFactory(list -> new PassengerCell());
  }

  public void setPassengerZone(PassengerZone passengerZone) {
    if (passengerZone != null) {
      passengers = passengerZone.getPassengers();
      setVisible(true);
    }
    else {
      passengers = new ArrayList<>();
      setVisible(false);
    }
  }

  // TODO: how can I avoid having to manually refresh the ListView?
  // I cannot use the goodies of ObservableList, because the list has to be created in Passenger class
  // and Passenger is not a Java FX Application thread. WARNING! - side effect - no state (no selected item)
  // TODO: investigate having trouble double clicking list items
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
