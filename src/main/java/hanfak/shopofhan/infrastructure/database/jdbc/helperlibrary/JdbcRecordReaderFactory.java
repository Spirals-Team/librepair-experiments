package hanfak.shopofhan.infrastructure.database.jdbc.helperlibrary;

import hanfak.shopofhan.infrastructure.database.jdbc.JDBCDatabaseConnectionManager;
import org.slf4j.Logger;

public class JdbcRecordReaderFactory {

    private final Logger logger;
    private final JDBCDatabaseConnectionManager databaseConnectionProvider;

    public JdbcRecordReaderFactory(Logger logger, JDBCDatabaseConnectionManager databaseConnectionProvider) {
        this.logger = logger;
        this.databaseConnectionProvider = databaseConnectionProvider;
    }

    public <LookupId, Record> JdbcRecordReader<LookupId, Record> create(String querySql, ResultSetExtractor<Record> resultSetExtractor, PreparedStatementSetter<LookupId> query, Class<Record> recordClass) {
        return new JdbcRecordReader<>(logger, querySql, resultSetExtractor, query, databaseConnectionProvider, recordClass);
    }
}
