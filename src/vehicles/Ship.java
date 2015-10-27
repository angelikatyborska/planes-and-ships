package vehicles;

import gui.canvas.Drawer;

public abstract class Ship extends Vehicle {
  public Ship(double velocity) {
    super(velocity);
  }

  @Override
  public void draw(Drawer drawer) {
    drawer.drawShip(this);
  }
}
