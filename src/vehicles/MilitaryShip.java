package vehicles;

import core.Weapon;
import gui.canvas.Drawer;
import stopovers.InvalidVehicleAtStopoverException;
import stopovers.Stopover;
import world.StopoverNotFoundInStopoverNetworkException;

import java.util.List;


public class MilitaryShip extends Ship {
  private Weapon weapon;

  public MilitaryShip(double velocity, Weapon.WeaponType weaponType) {
    super(velocity);
    weapon = new Weapon(weaponType);
  }

  public Weapon getWeapon() {
    return weapon;
  }

  @Override
  public void draw(Drawer drawer) {
    drawer.drawMilitaryShip(this);
  }

  @Override
  public void arrivedAtStopover(Stopover stopover) throws InvalidVehicleAtStopoverException, InterruptedException {
    if (stopover.accommodateMilitaryShip(this)) {
      stopover.prepareMilitaryShipForTravel(this);
      stopover.releaseVehicle(this);
    }
  }

  @Override
  public void updateRoute() {
    synchronized (route) {
      previousStopoverNumber++;

      if (previousStopoverNumber == route.size() - 1) {
        pickNewRandomRoute();
        previousStopoverNumber = 0;
      }
    }
  }

  @Override
  protected List<Stopover> newSubRoute() {
    // can't change the route of MilitaryShip
    return null;
  }

  private void pickNewRandomRoute() {
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
