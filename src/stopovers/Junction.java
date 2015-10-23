package stopovers;

import core.Coordinates;
import gui.WorldDrawer;

public class Junction extends Stopover {
  public Junction(Coordinates coordinates) {
    super(coordinates, 1);
  }

  @Override
  public void draw(WorldDrawer drawer) {
    drawer.draw(this);
  }
}
