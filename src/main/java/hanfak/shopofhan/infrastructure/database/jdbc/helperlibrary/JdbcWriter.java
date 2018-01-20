package hanfak.shopofhan.infrastructure.database.jdbc.helperlibrary;


import hanfak.shopofhan.infrastructure.database.jdbc.JDBCDatabaseConnectionManager;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class JdbcWriter<Record> {
    private final String insertSql;
    private final PreparedStatementSetter<Record> insertPreparedStatementSetter;
    private final JDBCDatabaseConnectionManager databaseConnectionProvider;
    private final Logger logger;

    public JdbcWriter(String insertSql, PreparedStatementSetter<Record> insertPreparedStatementSetter, JDBCDatabaseConnectionManager databaseConnectionProvider, Logger logger) {
        this.insertSql = insertSql;
        this.insertPreparedStatementSetter = insertPreparedStatementSetter;
        this.databaseConnectionProvider = databaseConnectionProvider;
        this.logger = logger;
    }

    // TODO More tests and exceptions to catch
    public void write(final Record stock) {
        try {
            Optional<Connection> connection = databaseConnectionProvider.getDBConnection();
            if (connection.isPresent()) {
                try (Connection dbConnection = connection.get();
                     PreparedStatement insertStatement = dbConnection.prepareStatement(insertSql)) {
                    insertPreparedStatementSetter.setParameters(new EnhancedPreparedStatement(insertStatement), stock);
                    insertStatement.execute();
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to insert'", e);
        }
    }
}
