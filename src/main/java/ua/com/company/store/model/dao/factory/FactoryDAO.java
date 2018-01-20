package ua.com.company.store.model.dao.factory;

import ua.com.company.store.model.dao.connection.JDBCConnectionPool;
import ua.com.company.store.model.dao.daoAbstract.GenericDAO;
import ua.com.company.store.model.dao.exceptions.PersistException;

/**
 * Created by Владислав on 22.11.2017.
 */
public interface FactoryDAO {
    GenericDAO getDao(Class daoClass) throws PersistException;
   }
