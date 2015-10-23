package stopovers;

import core.Coordinates;
import gui.WorldDrawer;
import vehicles.Airplane;
import vehicles.MilitaryAirplane;

public class MilitaryAirport extends Airport {
  public MilitaryAirport(Coordinates coordinates, int vehicleCapacity) {
    super(coordinates, vehicleCapacity);
  }

  public boolean accommodateVehicle(Airplane airplane) throws InvalidVehicleAtStopoverException {
    throw new InvalidVehicleAtStopoverException(airplane, this);
  }

  public boolean accommodateVehicle(MilitaryAirplane militaryAirplane) throws InvalidVehicleAtStopoverException {
    return super.accommodateVehicle((Airplane) militaryAirplane);
  }

  @Override
  public void draw(WorldDrawer drawer) {
    drawer.draw(this);
  }
}
