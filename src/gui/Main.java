package gui;

import core.Coordinates;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import stopovers.CivilAirport;
import stopovers.MilitaryAirport;
import world.StopoverNetwork;
import world.World;
import world.WorldClock;
import world.WorldMap;

import java.awt.event.WindowEvent;

public class Main extends Application {
  Thread drawerClockThread;
  Thread drawerThread;
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("Planes and Ships");
    primaryStage.setWidth(800);
    primaryStage.setHeight(640);

    Group root = new Group();
    Canvas canvas = new Canvas(800, 640);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    root.getChildren().add(canvas);
    primaryStage.setScene(new Scene(root));

    StopoverNetwork network = new StopoverNetwork();

    CivilAirport airportWest = new CivilAirport(new Coordinates(20, 140), 4);
    CivilAirport airportNorth = new CivilAirport(new Coordinates(160, 20), 4);
    CivilAirport airportEast = new CivilAirport(new Coordinates(240, 170), 4);
    CivilAirport airportCenter = new CivilAirport(new Coordinates(130, 120), 4);

    network.add(airportWest);
    network.add(airportNorth);
    network.add(airportEast);
    network.add(airportCenter);

    network.connect(airportWest, airportNorth);
    network.connect(airportNorth, airportEast);
    network.connect(airportEast, airportWest);

    network.connect(airportCenter, airportNorth);
    network.connect(airportCenter, airportEast);
    network.connect(airportCenter, airportWest);

    World world = new World(new WorldClock(100), new WorldMap(network));
    world.addCivilAirplane();
    WorldDrawer drawer = new WorldDrawer(3, world, gc);
    drawerThread = new Thread(drawer);
    drawerThread.start();

    primaryStage.setOnCloseRequest(we -> { world.shutDown(); drawerThread.interrupt(); });
    primaryStage.show();
  }
}
