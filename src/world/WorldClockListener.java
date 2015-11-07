package world;

/**
 * Subscribes to the WorldClock.
 */
public abstract class WorldClockListener implements Runnable {
  /**
   * Gets called every time the clock ticks.
   * @throws InterruptedException
   */
  public abstract void tick() throws InterruptedException;

  public void run() {
    while(!Thread.currentThread().isInterrupted()) {
      try {
        synchronized (this) {
          wait();
        }
        tick();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }
}
