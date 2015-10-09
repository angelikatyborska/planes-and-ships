package core;

/**
 * Created by angelika on 09/10/15.
 */
public class IdGenerator {
    private static int nextId = 1;

    public static int getId() {
        return nextId++;
    }
}
