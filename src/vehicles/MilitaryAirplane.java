package vehicles;

import core.Weapon;
import gui.canvas.Drawer;
import stopovers.InvalidVehicleAtStopoverException;
import stopovers.Junction;
import stopovers.MilitaryAirport;
import stopovers.Stopover;
import world.StopoverNotFoundInStopoverNetworkException;

import java.util.List;

/**
 * An airplane with weapons
 * @see Airplane
 * @see Weapon
 * @see MilitaryAirport
 */
public class MilitaryAirplane extends Airplane {
  private Weapon weapon;

  public MilitaryAirplane(double velocity, int fuelCapacity, Weapon.WeaponType weaponType) {
    super(velocity, fuelCapacity);
    weapon = new Weapon(weaponType);
  }

  public Weapon getWeapon() {
    return weapon;
  }

  @Override
  public void draw(Drawer drawer) {
    drawer.drawMilitaryAirplane(this);
  }

  @Override
  public void arrivedAtStopover(Stopover stopover) throws InvalidVehicleAtStopoverException, InterruptedException {
    if (stopover.accommodateMilitaryAirplane(this)) {
      if (!shouldCrash() || stopover instanceof Junction) {
        stopover.prepareMilitaryAirplaneForTravel(this);
        stopover.releaseVehicle(this);
      } else {
        setCrashed(true);
      }
    }
  }

  @Override
  public List<Stopover> newSubRoute() {
    try {
      return worldMap.getRouteGenerator().newRoute(route.get(previousStopoverNumber + 1), MilitaryAirport.class, 4);
    } catch (StopoverNotFoundInStopoverNetworkException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public Stopover closestAirport() {
    return worldMap.findClosestMetricallyMilitaryAirport(getCoordinates());
  }
}
