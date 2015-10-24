package vehicles;

import core.Weapon;
import gui.Drawer;
import stopovers.InvalidVehicleAtStopoverException;
import stopovers.Stopover;
import world.StopoverNotFoundInStopoverNetworkException;

import java.util.Arrays;


public class MilitaryShip extends Ship {

  public MilitaryShip(double velocity, Weapon.WeaponType weaponType) {
    super(velocity);
    weapon = new Weapon(weaponType);
  }

  public Weapon getWeapon() {
    return weapon;
  }

  private Weapon weapon;

  @Override
  public void draw(Drawer drawer) {
    drawer.drawMilitaryShip(this);
  }

  @Override
  public void arrivedAtStopover(Stopover stopover) throws InvalidVehicleAtStopoverException, InterruptedException {
    while (!stopover.accommodateVehicle(this)) {}
    stopover.prepareVehicleForTravel(this);
    stopover.releaseVehicle(this);
  }

  @Override
  public void updateRoute() {
    synchronized (route) {
      previousStopoverNumber++;

      if (previousStopoverNumber == route.size() - 1) {
        randomizeRoute();
        previousStopoverNumber = 0;
      }
    }
  }

  private void randomizeRoute() {
    synchronized (route) {
      Stopover previousStopover = route.get(route.size() - 1);
      try {
        Stopover nextStopover = worldMap.getAdjecentJunction(previousStopover);
        route.clear();
        route.add(previousStopover);
        route.add(nextStopover);
      }
      catch (StopoverNotFoundInStopoverNetworkException e) {
        e.printStackTrace();
      }
    }
  }
}
