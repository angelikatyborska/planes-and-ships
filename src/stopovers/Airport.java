package stopovers;

import core.Coordinates;
import gui.canvas.Drawer;
import vehicles.*;

/**
 * A Stopover that can accommodate Airplanes.
 * @see Stopover
 * @see Airplane
 */
public abstract class Airport extends Stopover {
  public Airport(Coordinates coordinates, int vehicleCapacity) {
    this("", coordinates, vehicleCapacity);
  }

  public Airport(String name, Coordinates coordinates, int vehicleCapacity) {
    super(name, coordinates, vehicleCapacity);
  }

  public boolean accommodateCivilAirplane(CivilAirplane vehicle) throws InvalidVehicleAtStopoverException {
    return accommodate(vehicle);
  }

  public boolean accommodateMilitaryAirplane(MilitaryAirplane vehicle) throws InvalidVehicleAtStopoverException {
    return accommodate(vehicle);
  }

  public void prepareAirplaneForTravel(Airplane vehicle) throws InterruptedException {
    super.prepareAirplaneForTravel(vehicle);
    vehicle.refillFuel();
  }

  public void prepareCivilAirplaneForTravel(CivilAirplane vehicle) throws InterruptedException {
    super.prepareCivilAirplaneForTravel(vehicle);
  }


  @Override
  public void draw(Drawer drawer) {
    drawer.drawAirport(this);
  }
}
