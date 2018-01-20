package hanfak.shopofhan.infrastructure.database.jdbc.helperlibrary;


import hanfak.shopofhan.infrastructure.database.jdbc.JDBCDatabaseConnectionManager;
import org.slf4j.Logger;

public class JdbcWriterFactory {

    private final Logger logger;
    private final JDBCDatabaseConnectionManager databaseConnectionProvider;

    public JdbcWriterFactory(Logger logger, JDBCDatabaseConnectionManager databaseConnectionProvider) {
        this.logger = logger;
        this.databaseConnectionProvider = databaseConnectionProvider;
    }

    public <ActualEvent> JdbcWriter<ActualEvent> create(String insertSql, PreparedStatementSetter<ActualEvent> insert) {
        return new JdbcWriter<>(insertSql, insert, databaseConnectionProvider, logger);
    }
}
