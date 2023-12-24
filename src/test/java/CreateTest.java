import org.junit.Test;

import java.awt.*;
import java.io.IOException;

import static org.junit.Assert.*;

public class CreateTest {
    @Test
    public void testCreateTestPage() throws IOException {
        Rectangle bounds=new Rectangle(50,50,600,600);
       TextEditor page= new TextEditor(bounds);
        assertNotNull(page);
    }
    @Test
    public void testCreateLoginPage() {
        Rectangle bounds=new Rectangle(50,50,600,600);
        LoginPage page= new LoginPage(bounds);
        assertNotNull(page);
    }
}
