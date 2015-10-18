package stopovers;

import core.Coordinates;
import core.PassengerZone;
import vehicles.CivilAirplane;
import vehicles.Vehicle;

import java.util.Arrays;
import java.util.List;

public class CivilAirport extends Airport {
  public final PassengerZone passengerZone;

  public CivilAirport(Coordinates coordinates, int vehicleCapacity) {
    super(coordinates, vehicleCapacity);
    passengerZone = new PassengerZone(Integer.MAX_VALUE);
  }

  protected List<Class<? extends Vehicle>> allowedVehicles(){
    return Arrays.asList(CivilAirplane.class);
  }
}
