package ua.com.company.store.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.com.company.store.utils.JDBCConnectionPoolManager;
import ua.com.company.store.model.dao.connection.JDBCConnectionPool;

import static org.junit.Assert.*;

/**
 * Created by Владислав on 08.01.2018.
 */
public class UserServiceTest {
   private UserService userService;
   private JDBCConnectionPoolManager jdbcConnectionPoolManager;
    @Before
    public void setUp() throws Exception {
  userService = UserService.getInstance();
  jdbcConnectionPoolManager = JDBCConnectionPoolManager.getInstance();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getUserByNickName() throws Exception {
       assertNotNull(JDBCConnectionPool.getInstanceConnectionPool());
       assertNotNull(jdbcConnectionPoolManager);
       assertEquals(jdbcConnectionPoolManager.getJdbcConnectionPool(),jdbcConnectionPoolManager.getJdbcConnectionPool());

        //User user  = userService.getUserByNickName("Vlad");
    }

}