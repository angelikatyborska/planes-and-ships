package stopovers;

import core.Coordinates;
import core.PassengerZone;
import vehicles.Airplane;
import vehicles.CivilAirplane;

public class CivilAirport extends Airport {
  public final PassengerZone passengerZone;

  public CivilAirport(Coordinates coordinates, int vehicleCapacity) {
    super(coordinates, vehicleCapacity);
    passengerZone = new PassengerZone(Integer.MAX_VALUE);
  }

  public boolean accommodateVehicle(Airplane airplane) throws InvalidVehicleAtStopoverException {
    throw new InvalidVehicleAtStopoverException(airplane, this);
  }

  public boolean accommodateVehicle(CivilAirplane civilAirplane) throws InvalidVehicleAtStopoverException {
    return super.accommodateVehicle(civilAirplane);
  }
}
