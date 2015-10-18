package world;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class WorldClockTest {
  private class StubWorldClockListener extends WorldClockListener {
    public int counter;

    public StubWorldClockListener() { counter = 0; }

    @Override
    public void tick() {}

    public void run() {
      while (!Thread.currentThread().isInterrupted()) {
        synchronized (this) {
          try {
            wait();
            counter++;
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          }
        }
      }
    }
  }

  @Test
  public void shouldCallNotifyOnListenersNTimes() throws InterruptedException {
    WorldClock clock = new WorldClock(100, 3);
    StubWorldClockListener listener = new StubWorldClockListener();
    Thread listenerThread = new Thread(listener);

    clock.addListener(listener);

    listenerThread.start();
    clock.run();

    listenerThread.interrupt();

    assertTrue(2 <= listener.counter);
    assertTrue(3 >= listener.counter);
  }

  @Test
  public void shouldCallNotifyOnAllListeners() throws InterruptedException {
    WorldClock clock = new WorldClock(100, 2);
    StubWorldClockListener listener1 = new StubWorldClockListener();
    StubWorldClockListener listener2 = new StubWorldClockListener();
    Thread listenerThread1 = new Thread(listener1);
    Thread listenerThread2 = new Thread(listener2);

    clock.addListener(listener1);
    clock.addListener(listener2);

    listenerThread1.start();
    listenerThread2.start();

    clock.run();

    listenerThread1.interrupt();
    listenerThread2.interrupt();

    assertTrue(1 <= listener1.counter);
    assertTrue(2 >= listener1.counter);
    assertTrue(1 <= listener2.counter);
    assertTrue(2 >= listener2.counter);

  }
}
