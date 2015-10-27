package vehicles;

import core.Weapon;
import gui.canvas.Drawer;
import stopovers.InvalidVehicleAtStopoverException;
import stopovers.MilitaryAirport;
import stopovers.Stopover;
import world.StopoverNotFoundInStopoverNetworkException;

import java.util.List;

public class MilitaryAirplane extends Airplane {
  private Weapon weapon;

  public MilitaryAirplane(double velocity, int fuelCapacity, Weapon.WeaponType weaponType) {
    super(velocity, fuelCapacity);
    weapon = new Weapon(weaponType);
  }

  @Override
  public void draw(Drawer drawer) {
    drawer.drawMilitaryAirplane(this);
  }

  @Override
  public void arrivedAtStopover(Stopover stopover) throws InvalidVehicleAtStopoverException, InterruptedException {
    while (!stopover.accommodateMilitaryAirplane(this)) {}
    stopover.prepareMilitaryAirplaneForTravel(this);
    stopover.releaseVehicle(this);
  }

  @Override
  public List<Stopover> newSubRoute() {
    try {
      return worldMap.getRouteGenerator().newRoute(route.get(previousStopoverNumber + 1), MilitaryAirport.class, 4);
    } catch (StopoverNotFoundInStopoverNetworkException e) {
      e.printStackTrace();
    };
    return null;
  }
}
