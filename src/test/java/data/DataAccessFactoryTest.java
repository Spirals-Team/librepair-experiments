package data;

import interfaces.IDataAccessor;
import interfaces.IDataAccessFactory;
import org.junit.Test;

import static org.junit.Assert.*;
import org.junit.Ignore;

public class DataAccessFactoryTest {
    @Test
    public void getDataAccessorTestStub() {
        IDataAccessFactory iDataAccessFactory = new DataAccessFactory();
        IDataAccessor dataAccessor = iDataAccessFactory.getDataAccessor("stub");
        assertTrue(dataAccessor.getClass().getName().equals("data.DataAccessStub"));
    }
    
    @Ignore
    @Test
    public void getDataAccessorTestNeo4J() {
        IDataAccessFactory iDataAccessFactory = new DataAccessFactory();
        IDataAccessor dataAccessor = iDataAccessFactory.getDataAccessor("neo4j");
        assertTrue(dataAccessor.getClass().getName().equals("data.DataAccessNeo4J"));
    }
    @Test
    public void getDataAccessorTestMongoDB() {
        IDataAccessFactory iDataAccessFactory = new DataAccessFactory();
        IDataAccessor dataAccessor = iDataAccessFactory.getDataAccessor("mongodb");
        assertTrue(dataAccessor.getClass().getName().equals("data.DataAccessMongoDB"));
    }
    @Test
    public void getDataAccessorTestDefault() {
        IDataAccessFactory iDataAccessFactory = new DataAccessFactory();
        IDataAccessor dataAccessor = iDataAccessFactory.getDataAccessor("blfgh");
        assertTrue(dataAccessor.getClass().getName().equals("data.DataAccessStub"));
    }
}