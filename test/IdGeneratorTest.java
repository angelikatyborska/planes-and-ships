import static org.junit.Assert.assertEquals;
import org.junit.Test;
import core.IdGenerator;

public class IdGeneratorTest {
    @Test
    public void shouldGenerateSequentialIds() {
        int id1 = IdGenerator.getId();
        int id2 = IdGenerator.getId();
        assertEquals(id1 + 1, id2);
    }
}
