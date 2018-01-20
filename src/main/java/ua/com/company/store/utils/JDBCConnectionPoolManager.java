package ua.com.company.store.utils;

import ua.com.company.store.model.dao.connection.JDBCConnectionPool;

/**
 * Created by Владислав on 22.12.2017.
 */
public final class JDBCConnectionPoolManager {
    private JDBCConnectionPool jdbcConnectionPool;


    private JDBCConnectionPoolManager() {
    }

    static class Holder{
        static JDBCConnectionPoolManager jdbcConnectionPoolManager = new JDBCConnectionPoolManager();

    }
    public static JDBCConnectionPoolManager getInstance(){
        return Holder.jdbcConnectionPoolManager;
    }

    public JDBCConnectionPool getJdbcConnectionPool() {
        return jdbcConnectionPool;
    }

    public void setJdbcConnectionPool(JDBCConnectionPool jdbcConnectionPool) {
        this.jdbcConnectionPool = jdbcConnectionPool;
    }


}
