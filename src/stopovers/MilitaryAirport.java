package stopovers;

import core.Coordinates;
import gui.canvas.Drawer;
import vehicles.CivilAirplane;

public class MilitaryAirport extends Airport {
  public MilitaryAirport(Coordinates coordinates, int vehicleCapacity) {
    this("", coordinates, vehicleCapacity);
  }

  public MilitaryAirport(String name, Coordinates coordinates, int vehicleCapacity) {
    super(name, coordinates, vehicleCapacity);
  }

  public boolean accommodateCivilAirplane(CivilAirplane vehicle) throws InvalidVehicleAtStopoverException {
    throw new InvalidVehicleAtStopoverException(vehicle, this);
  }

  @Override
  public void draw(Drawer drawer) {
    drawer.drawMilitaryAirport(this);
  }
}
