package com.gdc.aerodev.service.test;

import com.gdc.aerodev.dao.postgres.PostgresUserDao;
import com.gdc.aerodev.model.User;
import com.gdc.aerodev.service.impl.UserService;
import com.opentable.db.postgres.embedded.FlywayPreparer;
import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.PreparedDbRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

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
        int size = service.countUsers();
        service.createUser(userName, userPassword, userEmail);
        assertNotNull(service.getUser(userName));
        assertEquals(size + 1, service.countUsers());
    }

    @Test
    public void testCreateExistentUser(){
        UserService service = getService();
        assertNotNull(service.createUser(userName, userPassword, userEmail));
        int size = service.countUsers();
        assertNull(service.createUser(userName, userPassword, userEmail));
        assertEquals(size, service.countUsers());
    }

    @Test
    public void testCreateEmptyName(){
        UserService service = getService();
        int size = service.countUsers();
        assertNull(service.createUser("", userPassword, userEmail));
        assertEquals(size, service.countUsers());
    }

    @Test
    public void testCreateExistentEmail(){
        UserService service = getService();
        assertNotNull(service.createUser(userName, userPassword, userEmail));
        int size = service.countUsers();
        assertNull(service.createUser("second", "new", userEmail));
        assertEquals(size, service.countUsers());
    }

    //Update User tests

    @Test
    public void testUpdateUser(){
        UserService service = getService();
        User before = service.getUser(1L);
        assertNotNull(service.updateUser(1L, userName, userPassword, userEmail, level));
        assertNotEquals(before.getUserName(), service.getUser(1L).getUserName());
    }

    @Test
    public void testUpdateWithEmptyParams(){
        UserService service = getService();
        Long id = service.createUser(userName, userPassword, userEmail);
        User before = service.getUser(id);
        assertNull(service.updateUser(id, "", "", "", (short) 0));
        User after = service.getUser(id);
        assertEquals(before.getUserName(), after.getUserName());
        assertEquals(before.getUserPassword(), after.getUserPassword());
        assertEquals(before.getUserEmail(), after.getUserEmail());
        assertEquals(before.getUserLevel(), after.getUserLevel());
    }

    private UserService getService(){
        return new UserService(new PostgresUserDao(new JdbcTemplate(db.getTestDatabase()), tableName));
    }

}