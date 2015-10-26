package gui;

import core.Passenger;
import core.PassengerZone;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class PassengerList extends ListView {
  static class PassengerCell extends ListCell<Passenger> {
    @Override
    public void updateItem(Passenger passenger, boolean empty) {
      // for some magic reason -fx-font-family in css doesn't want to accept any value, so I have to set the font here
      setFont(Font.font("Courier", 12));
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

    Group root = new Group();

    // TODO: find out how to do all this tedious stuff below in a fxml file
    ImageView photo = new ImageView(new Image("/images/photo.png"));
    photo.setLayoutX(10);
    photo.setLayoutY(10);

    ImageView background = new ImageView(new Image("/images/passport.png"));
    root.getChildren().add(background);
    root.getChildren().add(photo);

    Label firstNameLabel = new Label("First name:");
    Label firstName = new Label(passenger.getFirstName());

    Label lastNameLabel = new Label("Last name:");
    Label lastName = new Label(passenger.getLastName());

    Label ageLabel = new Label("Age:");
    Label age = new Label(passenger.getAge() + " years old");

    Label PESELLabel = new Label("PESEL:");
    Label PESEL = new Label(passenger.getPESEL());

    Label hometownLabel = new Label("Hometown:");
    Label hometown = new Label(passenger.getHometown());

    firstNameLabel.setFont(Font.font("Courier", 12));
    firstName.setFont(Font.font("Courier", 14));

    lastNameLabel.setFont(Font.font("Courier", 12));
    lastName.setFont(Font.font("Courier", 14));

    ageLabel.setFont(Font.font("Courier", 12));
    age.setFont(Font.font("Courier", 14));

    PESELLabel.setFont(Font.font("Courier", 12));
    PESEL.setFont(Font.font("Courier", 14));

    hometownLabel.setFont(Font.font("Courier", 12));
    hometown.setFont(Font.font("Courier", 14));

    firstNameLabel.setLayoutX(110);
    firstNameLabel.setLayoutY(10);
    firstName.setLayoutX(110);
    firstName.setLayoutY(25);

    lastNameLabel.setLayoutX(110);
    lastNameLabel.setLayoutY(55);
    lastName.setLayoutX(110);
    lastName.setLayoutY(70);

    ageLabel.setLayoutX(250);
    ageLabel.setLayoutY(10);
    age.setLayoutX(250);
    age.setLayoutY(25);

    PESELLabel.setLayoutX(250);
    PESELLabel.setLayoutY(55);
    PESEL.setLayoutX(250);
    PESEL.setLayoutY(70);

    hometownLabel.setLayoutX(110);
    hometownLabel.setLayoutY(105);
    hometown.setLayoutX(110);
    hometown.setLayoutY(120);

    root.getChildren().add(firstNameLabel);
    root.getChildren().add(firstName);
    root.getChildren().add(lastNameLabel);
    root.getChildren().add(lastName);
    root.getChildren().add(ageLabel);
    root.getChildren().add(age);
    root.getChildren().add(PESELLabel);
    root.getChildren().add(PESEL);
    root.getChildren().add(hometownLabel);
    root.getChildren().add(hometown);

    Scene scene = new Scene(root);

    stage.setScene(scene);
    stage.show();
  }
}
