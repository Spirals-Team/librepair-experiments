import com.gdc.aerodev.dao.specific.UserDao;
import com.gdc.aerodev.model.User;
import com.gdc.aerodev.service.specific.UserService;
import com.opentable.db.postgres.embedded.FlywayPreparer;
import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.PreparedDbRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserServiceTest {

    private String tableName = "user_test";
    private String userName = "Bob";
    private String userPassword = "p@ssw0rd";
    private String userEmail = "email";
    private short level = 100;

    @Rule
    public PreparedDbRule db = EmbeddedPostgresRules.preparedDatabase(FlywayPreparer.forClasspathLocation("user-service"));

    //Create User tests

    @Test
    public void testCreateUser(){
        UserService service = getService();
        UserDao dao = service.getDao();
        int size = dao.count();
        service.createUser(userName, userPassword, userEmail);
        assertNotNull(dao.getByName(userName));
        assertEquals(size + 1, dao.count());
    }

    @Test
    public void testCreateExistentUser(){
        UserService service = getService();
        UserDao dao = service.getDao();
        assertNotNull(service.createUser(userName, userPassword, userEmail));
        int size = dao.count();
        assertNull(service.createUser(userName, userPassword, userEmail));
        assertEquals(size, dao.count());
    }

    @Test
    public void testCreateEmptyName(){
        UserService service = getService();
        UserDao dao = service.getDao();
        int size = dao.count();
        assertNull(service.createUser("", userPassword, userEmail));
        assertEquals(size, dao.count());
    }

    @Test
    public void testCreateExistentEmail(){
        UserService service = getService();
        UserDao dao = service.getDao();
        assertNotNull(service.createUser(userName, userPassword, userEmail));
        int size = dao.count();
        assertNull(service.createUser("second", "new", userEmail));
        assertEquals(size, dao.count());
    }

    //Update User tests

    @Test
    public void testUpdateUser(){
        UserService service = getService();
        UserDao dao = service.getDao();
        User before = dao.getById(1L);
        service.updateUser(1L, userName, userPassword, userEmail, level);
        assertNotEquals(before, dao.getById(1L));
    }

    @Test
    public void testUpdateExistentUser(){
        UserService service = getService();
        UserDao dao = service.getDao();
        Long id = service.createUser(userName, userPassword, userEmail);
        int size = dao.count();
        assertNull(service.updateUser(id, userName, "", "", (short) 0));
        assertEquals(size, dao.count());
    }

    @Test
    public void testUpdateWithEmptyParams(){
        UserService service = getService();
        UserDao dao =service.getDao();
        Long id = service.createUser(userName, userPassword, userEmail);
        User before = dao.getById(id);
        assertNull(service.updateUser(id, "", "", "", (short) 0));
        User after = dao.getById(id);
        assertEquals(before.getUserName(), after.getUserName());
        assertEquals(before.getUserPassword(), after.getUserPassword());
        assertEquals(before.getUserEmail(), after.getUserEmail());
        assertEquals(before.getUserLevel(), after.getUserLevel());
    }

    @Test


    private UserService getService(){
        return new UserService(db.getTestDatabase(), tableName);
    }

}
