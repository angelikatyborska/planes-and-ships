package world;

import org.junit.Test;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

public class WorldClockListenerTest {
  private class StubWorldClockListener extends WorldClockListener {
    public int counter;

    public StubWorldClockListener() { super(); counter = 0; }

    @Override
    public void tick() { counter++; }
  }

  @Test
  public void notifiedThreadShouldCallTick() throws InterruptedException {
    StubWorldClockListener listener = new StubWorldClockListener();

    Thread listenerThread = new Thread(listener);
    listenerThread.start();

    for (int i = 0; i < 2; i++){
      sleep(100);
      synchronized (listener) {
        listener.notify();
      }
    }

    listenerThread.interrupt();

    assertTrue(1 <= listener.counter);
    assertTrue(2 >= listener.counter);
  }
}
