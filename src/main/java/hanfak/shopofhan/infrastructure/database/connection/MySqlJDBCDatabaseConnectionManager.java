package hanfak.shopofhan.infrastructure.database.connection;

import hanfak.shopofhan.infrastructure.database.jdbc.JDBCDatabaseConnectionManager;
import hanfak.shopofhan.infrastructure.properties.Settings;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Optional;

// TODO: Redundent delete
// Good example of open/closed principle. change database connection using new class instead of changing this one
// example of using straight up connection
public class MySqlJDBCDatabaseConnectionManager implements JDBCDatabaseConnectionManager {

    private static final String DATABASE_NAME = "shop_of_han_database";
    // TODO properties for timeout
    private static final String DATABASE_FLAGS = "?connectTimeout=3000&verifyServerCertificate=false&useSSL=true";

    private final Settings settings;
    private final Logger logger;

    public MySqlJDBCDatabaseConnectionManager(Settings settings, Logger logger) {
        this.settings = settings;
        this.logger = logger;
    }

    @Override
    public Optional<Connection> getDBConnection() {
        try {
            Connection connection = DriverManager.getConnection( // tODOTry with resuorces
                    settings.databaseURL() + DATABASE_NAME + DATABASE_FLAGS,
                    settings.databaseUsername(),
                    settings.databasePassword());
            logger.info("db url " + settings.databaseURL());

            return Optional.of(connection);
        } catch(Exception e) {
            logger.info("db url " + settings.databaseURL());
            logger.error("exception in connection " + e);
            logger.error(e.getMessage());
        }
        return Optional.empty();
    }
}
