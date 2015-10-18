package core;

public class IdGenerator {
  private static int nextId = 1;

  public static int getId() {
    return nextId++;
  }
}
