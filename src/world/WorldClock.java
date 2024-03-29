package world;

import java.io.Serializable;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

/**
 * A clock to which WorldClockListeners can subscribe.
 */
public class WorldClock implements Runnable, Serializable {
  private ArrayList<WorldClockListener> listeners;
  private int timeInterval;
  private boolean bounded;
  private int repetitions;
  private boolean paused;

  /**
   *
   * @param timeInterval How often the clock should tick in milliseconds
   */
  public WorldClock(int timeInterval) {
    this.listeners = new ArrayList<>();
    this.timeInterval = timeInterval;
    bounded = false;
    repetitions = 0;
  }

  /**
   *
   * @param timeInterval How often the clock should tick in milliseconds
   * @param repetitions After how many ticks should the clock stop
   */
  public WorldClock(int timeInterval, int repetitions) {
    this(timeInterval);
    bounded = true;
    this.repetitions = repetitions;
  }

  public void pause() {
    paused = !paused;
  }
  public void addListener(WorldClockListener listener) {
    synchronized (listeners) {
      listeners.add(listener);
    }
  }

  @Override
  public void run() {
    if (bounded) {
      for (int i = 0; i < repetitions; i++) {
        tick();
      }
    }
    else {
      while (!Thread.currentThread().isInterrupted()) {
        tick();
      }
    }
  }

  private void tick() {
    try {
      sleep(timeInterval);
      if (!paused) {
        synchronized (listeners) {
          for (WorldClockListener listener : listeners) {
            synchronized (listener) {
              listener.notify();
            }
          }
        }
      }
    }
    catch(InterruptedException e){
      Thread.currentThread().interrupt();
    }
  }

  public boolean isPaused() {
    return paused;
  }
}
