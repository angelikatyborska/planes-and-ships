package gui;

import core.Coordinates;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import stopovers.*;
import vehicles.Vehicle;
import world.*;

public class WorldPanel extends Group {
  private Canvas worldCanvas;
  private Canvas detailCanvas;
  private World world;
  private WorldMap map;
  private WorldDrawer drawer;
  private ObjectDetailDrawer detailDrawer;
  private DrawerClock drawerClock;
  private Thread drawerThread;
  private Thread detailDrawerThread;
  private Thread drawerClockThread;
  private final double clickErrorMargin = 15;
  private final double worldWidth;
  private final double worldHeight;
  private final double detailPanelWidth;

  public WorldPanel() {
    this.worldWidth = 800;
    this.detailPanelWidth = 300;
    this.worldHeight = 640;
    worldCanvas = new Canvas(worldWidth, worldHeight);
    detailCanvas = new Canvas(detailPanelWidth, worldHeight);

    buildWorld();

    drawer = new WorldDrawer(map, worldCanvas.getGraphicsContext2D(), worldWidth, worldHeight);
    detailDrawer = new ObjectDetailDrawer(detailCanvas.getGraphicsContext2D(), detailPanelWidth, worldHeight);
    drawerThread = new Thread(drawer);
    detailDrawerThread = new Thread(detailDrawer);
    drawerClock = new DrawerClock(33);
    drawerClock.addListener(drawer);
    drawerClock.addListener(detailDrawer);
    drawerClockThread = new Thread(drawerClock);
    addEventHandlers();

    worldCanvas.setLayoutX(0);
    worldCanvas.setLayoutY(0);
    detailCanvas.setLayoutX(worldWidth);
    detailCanvas.setLayoutY(0);

    getChildren().add(worldCanvas);
    getChildren().add(detailCanvas);
  }

  public void start() {
    // cannot start drawing threads in the constructor, because they will start drawing on a canvas that doesn't exist
    drawerThread.start();
    detailDrawerThread.start();
    drawerClockThread.start();

  }

  public void shutDown() {
    world.shutDown();
    drawerThread.interrupt();
    detailDrawerThread.interrupt();
    drawerClockThread.interrupt();
  }

  private void addEventHandlers() {
    addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
      System.out.println(e.getX() + " "  + e.getY());
      Vehicle vehicle = world.findVehicleAtCoordinates(e.getX(), e.getY(), clickErrorMargin);
      Stopover stopover = world.findStopoverAtCoordinates(e.getX(), e.getY(), clickErrorMargin);

      if (vehicle != null) {
        System.out.println("clicked vehicle " + vehicle + " because " + vehicle.getCoordinates().toString());
        // not sure if this synchronized block is needed
        synchronized (detailDrawer) {
          detailDrawer.setObject(vehicle);
          detailDrawer.notify();
        }
      }
      else if (stopover != null){
        System.out.println("clicked stopover " + stopover  + " because " + stopover.getCoordinates().toString());
        synchronized (detailDrawer) {
          detailDrawer.setObject(stopover);
          detailDrawer.notify();
        }
      }
    });
  }

  private void buildWorld() {
    try {
      StopoverNetwork network = new StopoverNetwork();

      CivilAirport civilAirport1 = new CivilAirport(new Coordinates(450, 40), 5);
      CivilAirport civilAirport2 = new CivilAirport(new Coordinates(60, 45), 5);
      CivilAirport civilAirport3 = new CivilAirport(new Coordinates(170, 200), 5);
      CivilAirport civilAirport4 = new CivilAirport(new Coordinates(620, 230), 5);
      CivilAirport civilAirport5 = new CivilAirport(new Coordinates(490, 450), 5);
      CivilAirport civilAirport6 = new CivilAirport(new Coordinates(700, 590), 5);
      CivilAirport civilAirport7 = new CivilAirport(new Coordinates(230, 600), 5);

      network.add(civilAirport1);
      network.add(civilAirport2);
      network.add(civilAirport3);
      network.add(civilAirport4);
      network.add(civilAirport5);
      network.add(civilAirport6);
      network.add(civilAirport7);

      Junction airJunction1 = new Junction(new Coordinates(230, 100));
      Junction airJunction2 = new Junction(new Coordinates(620, 110));
      Junction airJunction3 = new Junction(new Coordinates(460, 180));
      Junction airJunction4 = new Junction(new Coordinates(720, 220));
      Junction airJunction5 = new Junction(new Coordinates(550, 330));
      Junction airJunction6 = new Junction(new Coordinates(650, 470));
      Junction airJunction7 = new Junction(new Coordinates(400, 550));

      network.add(airJunction1);
      network.add(airJunction2);
      network.add(airJunction3);
      network.add(airJunction4);
      network.add(airJunction5);
      network.add(airJunction6);
      network.add(airJunction7);

      network.connect(airJunction1, airJunction3);
      network.connect(airJunction2, airJunction3);
      network.connect(airJunction2, airJunction4);
      network.connect(airJunction3, airJunction5);
      network.connect(airJunction5, airJunction6);
      network.connect(airJunction6, airJunction7);


      network.connect(airJunction1, civilAirport1);

      network.connect(airJunction1, civilAirport2);
      network.connect(airJunction1, civilAirport3);

      network.connect(airJunction2, civilAirport1);
      network.connect(airJunction2, civilAirport4);

      network.connect(airJunction3, civilAirport1);
      network.connect(airJunction3, civilAirport4);

      network.connect(airJunction4, civilAirport4);

      network.connect(airJunction5, civilAirport4);
      network.connect(airJunction5, civilAirport5);

      network.connect(airJunction6, civilAirport5);
      network.connect(airJunction6, civilAirport6);

      network.connect(airJunction7, civilAirport5);
      network.connect(airJunction7, civilAirport7);

      MilitaryAirport militaryAirport1 = new MilitaryAirport(new Coordinates(760, 40), 4);
      MilitaryAirport militaryAirport2 = new MilitaryAirport(new Coordinates(340, 260), 4);
      MilitaryAirport militaryAirport3 = new MilitaryAirport(new Coordinates(770, 380), 4);
      MilitaryAirport militaryAirport4 = new MilitaryAirport(new Coordinates(500, 610), 4);

      network.add(militaryAirport1);
      network.add(militaryAirport2);
      network.add(militaryAirport3);
      network.add(militaryAirport4);

      network.connect(militaryAirport1, airJunction2);
      network.connect(militaryAirport1, airJunction4);

      network.connect(militaryAirport2, airJunction1);
      network.connect(militaryAirport2, airJunction3);

      network.connect(militaryAirport3, airJunction4);
      network.connect(militaryAirport3, airJunction6);

      network.connect(militaryAirport4, airJunction6);
      network.connect(militaryAirport4, airJunction7);

      Port port1 = new Port(new Coordinates(50, 230), 4);
      Port port2 = new Port(new Coordinates(230, 280), 4);
      Port port3 = new Port(new Coordinates(430, 350), 4);
      Port port4 = new Port(new Coordinates(390, 490), 4);
      Port port5 = new Port(new Coordinates(230, 530), 4);
      Port port6 = new Port(new Coordinates(100, 620), 4);

      network.add(port1);
      network.add(port2);
      network.add(port3);
      network.add(port4);
      network.add(port5);
      network.add(port6);

      Junction seaJunction1 = new Junction(new Coordinates(120, 330));
      Junction seaJunction2 = new Junction(new Coordinates(320, 370));
      Junction seaJunction3 = new Junction(new Coordinates(170, 430));
      Junction seaJunction4 = new Junction(new Coordinates(70, 470));
      Junction seaJunction5 = new Junction(new Coordinates(300, 480));

      network.add(seaJunction1);
      network.add(seaJunction2);
      network.add(seaJunction3);
      network.add(seaJunction4);
      network.add(seaJunction5);

      network.connect(seaJunction1, seaJunction3);
      network.connect(seaJunction1, seaJunction4);

      network.connect(seaJunction2, seaJunction3);
      network.connect(seaJunction2, seaJunction5);

      network.connect(seaJunction3, seaJunction4);

      network.connect(seaJunction1, port1);
      network.connect(seaJunction1, port2);

      network.connect(seaJunction2, port2);
      network.connect(seaJunction2, port3);

      network.connect(seaJunction3, port5);

      network.connect(seaJunction4, port5);
      network.connect(seaJunction4, port6);

      network.connect(seaJunction5, port4);
      network.connect(seaJunction5, port5);

      network.connect(port1, civilAirport3);
      network.connect(port2, civilAirport3);
      network.connect(port3, civilAirport5);
      network.connect(port4, civilAirport5);
      network.connect(port5, civilAirport7);
      network.connect(port6, civilAirport7);


      map = new WorldMap(network);
      world = new World(new WorldClock(50), map);

      world.addCivilAirplane();
      world.addCivilAirplane();
      world.addCivilAirplane();
      world.addCivilShip();
      world.addCivilShip();
      world.addCivilShip();
      world.addCivilShip();
      world.addCivilShip();
    }
    catch (StopoverNotFoundInStopoverNetworkException e) {
      e.printStackTrace();
    }
  }
}
