package ua.com.company.store.model.dao.factory;

import ua.com.company.store.model.dao.connection.JDBCConnectionPool;
import ua.com.company.store.model.dao.daoAbstract.GenericDAO;

/**
 * Created by Владислав on 22.11.2017.
 */
public interface CreatorDao {
    GenericDAO create();
}
