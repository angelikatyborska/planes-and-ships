package stopovers;

import core.Coordinates;
import gui.Drawer;

/**
 * A Stopover at which only one vehicle can be accommodated.
 * @see Stopover
 * @see vehicles.Vehicle
 */
public class Junction extends Stopover {
  public Junction(Coordinates coordinates) {
    super("Junction", coordinates, 1);
  }

  @Override
  public void draw(Drawer drawer) {
    drawer.drawStopover(this);
  }
}
