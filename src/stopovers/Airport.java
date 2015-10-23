package stopovers;

import core.Coordinates;
import gui.WorldDrawer;
import vehicles.*;

public abstract class Airport extends Stopover {
  public Airport(Coordinates coordinates, int vehicleCapacity) {
    super(coordinates, vehicleCapacity);
  }

  // TODO: show to the teacher - using casting
  public boolean accommodateVehicle(Vehicle vehicle) throws InvalidVehicleAtStopoverException {
    throw new InvalidVehicleAtStopoverException(vehicle, this);
  }

  public boolean accommodateVehicle(Airplane airplane) throws InvalidVehicleAtStopoverException {
    return super.accommodateVehicle((Vehicle) airplane);
  }

  public boolean accommodateVehicle(CivilAirplane airplane) throws InvalidVehicleAtStopoverException {
    return accommodateVehicle((Airplane) airplane);
  }

  public boolean accommodateVehicle(MilitaryAirplane airplane) throws InvalidVehicleAtStopoverException {
    return accommodateVehicle((Airplane) airplane);
  }

  public boolean accommodateVehicle(CivilShip vehicle) throws InvalidVehicleAtStopoverException {
    return accommodateVehicle((Vehicle) vehicle);
  }

  public boolean accommodateVehicle(MilitaryShip vehicle) throws InvalidVehicleAtStopoverException {
    return accommodateVehicle((Vehicle) vehicle);
  }

  public void prepareVehicleForTravel(Airplane vehicle) throws InterruptedException {
    super.prepareVehicleForTravel(vehicle);
    vehicle.refillFuel();
  }

  public void prepareVehicleForTravel(CivilAirplane vehicle) throws InterruptedException {
    prepareVehicleForTravel((Airplane) vehicle);
  }

  public void prepareVehicleForTravel(MilitaryAirplane vehicle) throws InterruptedException {
    prepareVehicleForTravel((Airplane) vehicle);
  }

  @Override
  public void draw(WorldDrawer drawer) {
    drawer.draw(this);
  }
}
