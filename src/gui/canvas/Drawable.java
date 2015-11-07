package gui.canvas;

/**
 * An interface for getting drawn by an object implementing the Drawer interface.
 * @see Drawer
 */

/**
 * A method responsible for calling the proper method from Drawer's interface with 'this' as the argument.
 * @see Drawer
 */
public interface Drawable {
  void draw(Drawer drawer);
}
