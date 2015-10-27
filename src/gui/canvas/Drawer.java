package gui.canvas;

import stopovers.*;
import vehicles.*;

// Visitor design pattern
public interface Drawer {
  void drawVehicle(Vehicle vehicle);
  void drawAirplane(Airplane vehicle);
  void drawCivilAirplane(CivilAirplane vehicle);
  void drawMilitaryAirplane(MilitaryAirplane vehicle);
  void drawShip(Ship vehicle);
  void drawCivilShip(CivilShip vehicle);
  void drawMilitaryShip(MilitaryShip vehicle);
  void drawStopover(Stopover stopover);
  void drawAirport(Airport stopover);
  void drawCivilAirport(CivilAirport stopover);
  void drawMilitaryAirport(MilitaryAirport stopover);
  void drawPort(Port port);
  void drawJunction(Junction junction);
}
