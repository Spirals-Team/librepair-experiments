import app.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
public class UserTest {

    User user1;
    String testEmail = "test";
    String testName = "Donald";

    @Before
    public void setUp() throws Exception {
        user1 = new User();
    }
    @Test
    public void testSetUsername() throws Exception {
        user1.setUsername("test");
        assertEquals(user1.getUsername(), testEmail);
    }
    @Test
    public void testSetName() throws Exception {
        user1.setName("Donald");
        assertEquals(user1.getName(), testName);

    }



}
