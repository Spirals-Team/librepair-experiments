package ua.com.company.store.model.dao.connection;

import org.apache.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Created by Владислав on 25.11.2017.
 */
public class ConnectionPoolDataSource {
    private Logger logger = Logger.getRootLogger();
    private DataSource dataSource;

    public ConnectionPoolDataSource() {
        try {
            Context context = new InitialContext();
            Context envContext = (Context) context.lookup("java:comp/env");
            logger.info("Created "    + context.toString() + " " + envContext.toString());
            dataSource = (DataSource) envContext.lookup("jdbc/store");
            logger.info("Created data source " + dataSource.toString());
        } catch (NamingException e) {
            logger.error("cant create context " + e.getMessage());
            e.printStackTrace();
        }
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
