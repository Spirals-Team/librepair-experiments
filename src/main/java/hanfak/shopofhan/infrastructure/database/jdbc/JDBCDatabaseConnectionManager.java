package hanfak.shopofhan.infrastructure.database.jdbc;

import java.sql.Connection;
import java.util.Optional;

public interface JDBCDatabaseConnectionManager {
    Optional<Connection> getDBConnection();
}
