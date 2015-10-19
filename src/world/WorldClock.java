package world;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class WorldClock implements Runnable {
  private ArrayList<WorldClockListener> listeners;
  private int timeInterval;
  private boolean paused;
  private boolean bounded;
  private int repetitions;

  public WorldClock(int timeInterval) {
    this.listeners = new ArrayList<>();
    this.timeInterval = timeInterval;
    paused = false;
    bounded = false;
    repetitions = 0;
  }

  public WorldClock(int timeInterval, int repetitions) {
    this(timeInterval);
    bounded = true;
    this.repetitions = repetitions;
  }

  public void addListener(WorldClockListener listener) {
    listeners.add(listener);
  }

  public void addListeners(List<WorldClockListener> listeners) {
    this.listeners.addAll(listeners);
  }

  public void run() {
    if (bounded) {
      for (int i = 0; i < repetitions; i++) {
        tick();
      }
    } else {
      while (!Thread.currentThread().isInterrupted()) {
        tick();
      }
    }
  }

  private void tick() {
    try {
      sleep(timeInterval);
      listeners.forEach(listener -> {
        synchronized (listener) {
          listener.notify();
        }
      });
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}