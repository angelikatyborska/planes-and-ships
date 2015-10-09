import static org.junit.Assert.assertEquals;

import core.IdGenerator;
import org.junit.Test;

public class IdGeneratorTest {
    @Test
    public void generatesSequentialIds() {
        int id1 = IdGenerator.getId();
        int id2 = IdGenerator.getId();
        assertEquals(id1 + 1, id2);
    }
}
