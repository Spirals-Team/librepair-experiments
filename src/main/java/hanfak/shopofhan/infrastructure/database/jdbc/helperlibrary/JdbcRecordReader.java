package hanfak.shopofhan.infrastructure.database.jdbc.helperlibrary;

//import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import hanfak.shopofhan.infrastructure.database.jdbc.JDBCDatabaseConnectionManager;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static java.lang.String.format;
// TODO test
//@SuppressFBWarnings(value = "SQL_PREPARED_STATEMENT_GENERATED_FROM_NONCONSTANT_STRING", justification = "We trust that the query SQL will be a constant elsewhere that is passed in")
public class JdbcRecordReader<LookupId, Event> {

    private final Logger logger;
    private final String querySql;
    private final Class<Event> eventClass;
    private final ResultSetExtractor<Event> resultSetExtractor;
    private final PreparedStatementSetter<LookupId> queryPreparedStatementSetter;
    private final JDBCDatabaseConnectionManager databaseConnectionProvider;

    public JdbcRecordReader(Logger logger, String querySql, ResultSetExtractor<Event> resultSetExtractor, PreparedStatementSetter<LookupId> queryPreparedStatementSetter, JDBCDatabaseConnectionManager databaseConnectionProvider, Class<Event> eventClass) {
        this.logger = logger;
        this.querySql = querySql;
        this.eventClass = eventClass;
        this.resultSetExtractor = resultSetExtractor;
        this.queryPreparedStatementSetter = queryPreparedStatementSetter;
        this.databaseConnectionProvider = databaseConnectionProvider;
    }

    public Optional<Event> readRecord(LookupId lookupId) {
        logger.info(format("Reading '%s' for '%s'", eventClass.getSimpleName(), lookupId));
        logger.debug(format("Using SQL:%n%s", querySql));
        try {
            Optional<Connection> connection = databaseConnectionProvider.getDBConnection();
            if (connection.isPresent()) {
                try (Connection dbConnection = connection.get();
                     PreparedStatement queryStatement = dbConnection.prepareStatement(querySql)) {

                    queryPreparedStatementSetter.setParameters(new EnhancedPreparedStatement(queryStatement), lookupId);

                    return executeQuery(lookupId, queryStatement);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException(format("Failed to read '%s' for '%s'", eventClass.getSimpleName(), lookupId), e);
        }
        return Optional.empty();
    }

    private Optional<Event> executeQuery(LookupId lookupId, PreparedStatement queryStatement) throws SQLException {
        try (ResultSet resultSet = queryStatement.executeQuery()) {
            if (!resultSet.next()) {
                logger.info(format("Could not find '%s' for '%s'", eventClass.getSimpleName(), lookupId));
                return Optional.empty();
            }
            EnhancedResultSet enhancedResultSet = new EnhancedResultSet(resultSet);
            Event event = resultSetExtractor.extract(enhancedResultSet);
            if (resultSet.next()) {
                throw new IllegalStateException(format("Found more than one '%s' for '%s'", eventClass.getSimpleName(), lookupId));
            }
            logger.info(format("Found '%s' for '%s'", event, lookupId));
            return Optional.of(event);
        }
    }
}
