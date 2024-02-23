import org.example.Main;
import org.junit.Test;

import static org.junit.Assert.*;

public class DBTest {
    final Main main = new Main();
    @Test
    public void testDBConnection(){
        assertEquals("connected",main.dbConnection());
    }
}
