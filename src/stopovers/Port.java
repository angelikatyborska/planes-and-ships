package stopovers;

import core.Coordinates;
import core.PassengerZone;
import vehicles.CivilShip;
import vehicles.Vehicle;

import java.util.Arrays;
import java.util.List;

public class Port extends Destination {
  public final PassengerZone passengerZone;

  public Port(Coordinates coordinates, int vehicleCapacity) {
    super(coordinates, vehicleCapacity);
    passengerZone = new PassengerZone(Integer.MAX_VALUE);
  }

  protected List<Class<? extends Vehicle>> allowedVehicles(){
    return Arrays.asList(CivilShip.class);
  }
}
