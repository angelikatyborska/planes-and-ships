package stopovers;

import core.Coordinates;
import vehicles.MilitaryAirplane;
import vehicles.Vehicle;

import java.util.Arrays;
import java.util.List;

public class MilitaryAirport extends Airport {
  public MilitaryAirport(Coordinates coordinates, int vehicleCapacity) {
    super(coordinates, vehicleCapacity);
  }

  protected List<Class<? extends Vehicle>> allowedVehicles(){
    return Arrays.asList(MilitaryAirplane.class);
  }
}
