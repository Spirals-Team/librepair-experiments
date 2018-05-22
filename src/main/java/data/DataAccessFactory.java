package data;

import interfaces.IDataAccessor;
import interfaces.IDataAccessFactory;

public class DataAccessFactory implements IDataAccessFactory {
    @Override
    public IDataAccessor getDataAccessor(String setting) {
        IDataAccessor dataAccessor;
        switch (setting) {
            case "stub":  dataAccessor = new DataAccessStub();
                break;
            case "neo4j":  dataAccessor = new DataAccessNeo4J();
                break;
            case "mongodb":  dataAccessor = new DataAccessMongoDB();
                break;
            default: dataAccessor = new DataAccessStub();
                break;
        }
        return dataAccessor;
    }
}
