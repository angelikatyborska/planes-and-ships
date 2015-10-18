package stopovers;

import core.Coordinates;
import vehicles.Airplane;
import vehicles.Vehicle;

import java.util.Arrays;
import java.util.List;

public abstract class Airport extends Destination {
  public Airport(Coordinates coordinates, int vehicleCapacity) {
    super(coordinates, vehicleCapacity);
  }

  protected List<Class<? extends Vehicle>> allowedVehicles(){
    return Arrays.asList(Airplane.class);
  }
}
