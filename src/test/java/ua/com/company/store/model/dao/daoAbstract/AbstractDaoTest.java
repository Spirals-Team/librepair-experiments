package ua.com.company.store.model.dao.daoAbstract;


import org.junit.Assert;
import org.junit.Test;
import ua.com.company.store.model.dao.connection.JDBCConnectionPool;
import ua.com.company.store.model.dao.impl.UserDAO;

/**
 * Created by Владислав on 24.11.2017.
 */
public class AbstractDaoTest {
    AbstractDao abstractDao = new UserDAO(JDBCConnectionPool.getInstanceConnectionPool());


    @Test
    public void insert() throws Exception {
    }

    @org.junit.Test
    public void getById() throws Exception {

    }

    @org.junit.Test
    public void getAll() throws Exception {

    }

    @org.junit.Test
    public void getConnectionFromPool() throws Exception {
       // Assert.assertNotNull(abstractDao.getConnectionFromPool());
        //egrerge
    }

}