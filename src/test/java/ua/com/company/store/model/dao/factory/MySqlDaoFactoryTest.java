package ua.com.company.store.model.dao.factory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.com.company.store.model.dao.connection.JDBCConnectionPool;

import static org.junit.Assert.*;

/**
 * Created by Владислав on 10.01.2018.
 */
public class MySqlDaoFactoryTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getDao() throws Exception {

    }

    @Test
    public void getMysqlDaoFactory() throws Exception {
        System.out.println(JDBCConnectionPool.getInstanceConnectionPool());
        System.out.println(JDBCConnectionPool.getInstanceConnectionPool());
        System.out.println(JDBCConnectionPool.getInstanceConnectionPool());
        System.out.println(JDBCConnectionPool.getInstanceConnectionPool());
        System.out.println(JDBCConnectionPool.getInstanceConnectionPool());

        System.out.println(MySqlDaoFactory.getMysqlDaoFactory(JDBCConnectionPool.getInstanceConnectionPool()).toString());
        System.out.println(MySqlDaoFactory.getMysqlDaoFactory(JDBCConnectionPool.getInstanceConnectionPool()).toString());
        System.out.println(MySqlDaoFactory.getMysqlDaoFactory(JDBCConnectionPool.getInstanceConnectionPool()).toString());
        System.out.println(MySqlDaoFactory.getMysqlDaoFactory(JDBCConnectionPool.getInstanceConnectionPool()).toString());
        System.out.println(MySqlDaoFactory.getMysqlDaoFactory(JDBCConnectionPool.getInstanceConnectionPool()).toString());
    }

}