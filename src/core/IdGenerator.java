package core;

// TODO: do I really need this class? maybe just use Java object HashCode
public class IdGenerator {
  private static int nextId = 1;

  public static int getId() {
    return nextId++;
  }
}
