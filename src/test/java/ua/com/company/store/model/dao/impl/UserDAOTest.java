package ua.com.company.store.model.dao.impl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.com.company.store.model.dao.connection.JDBCConnectionPool;
import ua.com.company.store.model.entity.User;

import static org.junit.Assert.*;

/**
 * Created by Владислав on 12.12.2017.
 */
public class UserDAOTest {
    UserDAO userDAO;
    @Before
    public void setUp() throws Exception {
        JDBCConnectionPool jdbcConnectionPool = JDBCConnectionPool.getInstanceConnectionPool();
        userDAO = new UserDAO(jdbcConnectionPool);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getByParameter() throws Exception {
       // Assert.assertNotNull(userDAO.getByParameter("Vlad"));
    }

}