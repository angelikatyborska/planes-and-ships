package stopovers;

import core.Coordinates;
import gui.Drawer;

public class Junction extends Stopover {
  public Junction(Coordinates coordinates) {
    super("Junction", coordinates, 1);
  }

  @Override
  public void draw(Drawer drawer) {
    drawer.drawStopover(this);
  }
}
