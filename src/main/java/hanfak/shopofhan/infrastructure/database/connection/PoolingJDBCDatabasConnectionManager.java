package hanfak.shopofhan.infrastructure.database.connection;

import hanfak.shopofhan.infrastructure.database.jdbc.JDBCDatabaseConnectionManager;
import org.slf4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

// TODO: inject logger to log stack trace
public class PoolingJDBCDatabasConnectionManager implements JDBCDatabaseConnectionManager {
    private final Logger logger;

    public PoolingJDBCDatabasConnectionManager(Logger logger) {
        this.logger = logger;
    }

    @Override
    public Optional<Connection> getDBConnection() {
        DataSource dataSource = HikariDatabaseConnectionPooling.getDataSource();

        try {
            logger.info("Getting connection...");
            return Optional.of(dataSource.getConnection());
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
