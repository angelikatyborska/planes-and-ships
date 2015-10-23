package gui;

import static java.lang.Thread.sleep;

public class WorldDrawerClock implements Runnable {
  private final WorldDrawer listener;
  private int timeInterval;

  public WorldDrawerClock(int timeInterval, WorldDrawer drawer) {
    this.timeInterval = timeInterval;
    this.listener = drawer;
  }

  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      try {
        sleep(timeInterval);
        synchronized (listener) {
          listener.notify();
        }
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }
}
