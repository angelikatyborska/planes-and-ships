package stopovers;

import core.Coordinates;
import gui.Drawer;
import vehicles.CivilAirplane;
import vehicles.CivilShip;
import vehicles.MilitaryAirplane;
import vehicles.MilitaryShip;

/**
 * A Stopover at which only one vehicle can be accommodated.
 * @see Stopover
 * @see vehicles.Vehicle
 */
public class Junction extends Stopover {
  public Junction(Coordinates coordinates) {
    super("Junction", coordinates, 1);
  }

  @Override
  public void draw(Drawer drawer) {
    drawer.drawJunction(this);
  }

  public boolean accommodateCivilAirplane(CivilAirplane vehicle) throws InvalidVehicleAtStopoverException {
    return accommodate(vehicle);
  }

  public boolean accommodateMilitaryAirplane(MilitaryAirplane vehicle) throws InvalidVehicleAtStopoverException {
    return accommodate(vehicle);
  }

  public boolean accommodateCivilShip(CivilShip vehicle) throws InvalidVehicleAtStopoverException {
    return accommodate(vehicle);
  }

  public boolean accommodateMilitaryShip(MilitaryShip vehicle) throws InvalidVehicleAtStopoverException {
    return accommodate(vehicle);
  }
}
