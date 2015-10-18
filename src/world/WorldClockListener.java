package world;

public abstract class WorldClockListener implements Runnable {
  public abstract void tick();

  public void run() {
    while(!Thread.currentThread().isInterrupted()) {
      synchronized (this) {
        try {
          wait();
          tick();
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    }
  }
}
