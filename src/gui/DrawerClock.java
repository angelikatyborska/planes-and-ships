package gui;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class DrawerClock implements Runnable {
  private final List<Drawer> listeners;
  private int timeInterval;

  public DrawerClock(int timeInterval) {
    this.timeInterval = timeInterval;
    this.listeners = new ArrayList<>();
  }

  public void addListener(Drawer listener) {
    listeners.add(listener);
  }

  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      try {
        sleep(timeInterval);
        for (Drawer listener : listeners) {
          synchronized (listener) {
            listener.notify();
          }
        }
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }
}
