package com.gdc.aerodev.dao.test;

import com.gdc.aerodev.dao.postgres.PostgresUserDao;
import com.gdc.aerodev.dao.exception.DaoException;
import com.gdc.aerodev.model.User;
import com.opentable.db.postgres.embedded.FlywayPreparer;
import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.PreparedDbRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PostgresUserDaoTest {

    private String tableName = "user_test";
    private String name = "Novichok";
    private String password = "p@ssw0rd";
    private String email = "email";
    private User user = new User(name, password, email);

    @Rule
    public PreparedDbRule db = EmbeddedPostgresRules.preparedDatabase(FlywayPreparer.forClasspathLocation("user"));

    //Standard tests

    @Test
    public void testGetById() {
        PostgresUserDao dao = getDao();
        User user = dao.getById(1L);
        assertEquals("Petr", user.getUserName());
        assertEquals("velikii@spb.ru", user.getUserEmail());
        assertEquals(0, user.getUserLevel());
    }

    @Test
    public void testGetByName() {
        PostgresUserDao dao = getDao();
        User user = dao.getByName("Petr");
        assertEquals("velikii@spb.ru", user.getUserEmail());
        assertEquals(0, user.getUserLevel());
    }

    @Test
    public void testGetAll() {
        PostgresUserDao dao = getDao();
        List<User> list = dao.getAll();
        assertEquals(3, list.size());
    }

    @Test
    public void testInsert() {
        PostgresUserDao dao = getDao();
        Long id = dao.save(user);
        assertEquals(name, dao.getById(id).getUserName());
    }

    @Test
    public void testUpdate() {
        PostgresUserDao dao = getDao();
        Long id = 1L;
        String name = "Denis";
        short level = 1;
        User user = dao.getById(id);
        assertEquals("Petr", user.getUserName());
        user.setUserName(name);
        user.setUserLevel(level);
        dao.save(user);
        assertEquals(name, dao.getById(id).getUserName());
        assertEquals(level, dao.getById(id).getUserLevel());
    }

    @Test
    public void testDelete() {
        PostgresUserDao dao = getDao();
        int size = dao.getAll().size();
        dao.delete(2L);
        assertEquals(size - 1, dao.getAll().size());
    }

    @Test
    public void testExistentEmail(){
        PostgresUserDao dao = getDao();
        assertEquals("Petr", dao.existentEmail("velikii@spb.ru"));
    }

    @Test
    public void testNonExistentEmail(){
        PostgresUserDao dao = getDao();
        assertNull(dao.existentEmail("!!!"));
    }

    @Test
    public void testCount(){
        PostgresUserDao dao = getDao();
        assertEquals(3, dao.count());
    }

    //Abnormal tests

    @Test
    public void testGetByIdNonExistent() {
        PostgresUserDao dao = getDao();
        assertNull(dao.getById(-1L));
    }

    @Test
    public void testGetByNameNonExistent() {
        PostgresUserDao dao = getDao();
        assertNull(dao.getByName("!!!"));
    }

    @Test(expected = DaoException.class)
    public void testInsertExistentUserException() {
        PostgresUserDao dao = getDao();
        String newName = "Petr";
        user.setUserName(newName);
        assertNull(dao.save(user));
    }

    @Test
    public void testInsertExistentUserDbSize() {
        PostgresUserDao dao = getDao();
        String newName = "Petr";
        user.setUserName(newName);
        int size = dao.getAll().size();
        try {
            assertNull(dao.save(user));
        } catch (DaoException e) {
            assertEquals(size, dao.getAll().size());
        }
    }

    @Test
    public void testDeleteNonExistentUser(){
        PostgresUserDao dao = getDao();
        assertFalse(dao.delete(-1L));
    }

    private PostgresUserDao getDao() {
        return new PostgresUserDao(new JdbcTemplate(db.getTestDatabase()), tableName);
    }

}
