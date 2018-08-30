package ru.job4j;

import org.apache.commons.dbcp2.*;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * DbConnector class to interact with database.
 */
public class DbConnector {

    private static final Logger LOG = LoggerFactory.getLogger(DbConnector.class);

    private static final ResourceBundle RB = ResourceBundle.getBundle("app");
    private static final ResourceBundle RB_SQL = ResourceBundle.getBundle("init");
    private static final DbConnector INSTANCE = new DbConnector();

    private Connection conn;

    private DbConnector() {
        this.conn = connect();
        initDB();
//        addRootIfRequired();
    }

    /**
     * Singleton
     *
     * @return userstore
     */
    public static DbConnector getInstance() {
        return INSTANCE;
    }

    public void initDB() {
        try (Statement stmt = connect().createStatement()) {
            stmt.execute(RB_SQL.getString("create.table.role"));
            stmt.execute(RB_SQL.getString("init.role"));
            stmt.execute(RB_SQL.getString("create.table.music_type"));
            stmt.execute(RB_SQL.getString("init.music_type"));
            stmt.execute(RB_SQL.getString("create.table.address"));
            stmt.execute(RB_SQL.getString("init.address"));
            stmt.execute(RB_SQL.getString("create.table.users"));
            stmt.execute(RB_SQL.getString("init.users"));
            stmt.execute(RB_SQL.getString("create.table.catalog"));
            stmt.execute(RB_SQL.getString("init.catalog"));
            connect().commit();
            LOG.info("Create table if required.");
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            try {
                connect().rollback();
            } catch (SQLException e1) {
                LOG.error(e1.getMessage(), e1);
            }
        }
    }

    public void cleanDB() {
        try (Statement stmt = connect().createStatement()) {
            stmt.execute(RB_SQL.getString("drop.cascade.role"));
            stmt.execute(RB_SQL.getString("drop.cascade.music_type"));
            stmt.execute(RB_SQL.getString("drop.cascade.address"));
            stmt.execute(RB_SQL.getString("drop.cascade.catalog"));
            stmt.execute(RB_SQL.getString("drop.cascade.users"));
            connect().commit();
            LOG.info("Clean database.");
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            try {
                connect().rollback();
            } catch (SQLException e1) {
                LOG.error(e1.getMessage(), e1);
            }
        }
    }

//    public void addRootIfRequired() {
//        if (findFirstByLogin("root") == null) {
//            addUser(new User("root", "root", "root",
//                "root@email.com", "region", "country", Role.ADMIN));
//        }
//    }

    private static DataSource setupDataSource(String connectURI) {
        ConnectionFactory connectionFactory =
            new DriverManagerConnectionFactory(connectURI, null);
        PoolableConnectionFactory poolableConnectionFactory =
            new PoolableConnectionFactory(connectionFactory, null);
        ObjectPool<PoolableConnection> connectionPool =
            new GenericObjectPool<>(poolableConnectionFactory);
        return new PoolingDataSource<>(connectionPool);
    }

    public Connection connect() {
        if (conn == null) {
            try {
                invokeDBDriver();
                conn = setupDataSource(RB.getString("db.url")).getConnection();
                conn.setAutoCommit(false);
                LOG.info("Create connection to {}.", conn.getMetaData().getURL());
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return conn;
    }

    private void invokeDBDriver() {
        try {
            Class.forName(RB.getString("db.driver"));
        } catch (ClassNotFoundException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public void disconnect() {
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }
}
