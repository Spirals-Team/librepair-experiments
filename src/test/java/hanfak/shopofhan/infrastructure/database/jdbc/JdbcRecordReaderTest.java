package hanfak.shopofhan.infrastructure.database.jdbc;

import hanfak.shopofhan.domain.crosscutting.ValueType;
import hanfak.shopofhan.infrastructure.database.jdbc.helperlibrary.*;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;
import org.mockito.InOrder;
import org.slf4j.Logger;
import testinfrastructure.WithMocktio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class JdbcRecordReaderTest implements WithAssertions, WithMocktio {

    private static final String QUERY = "query";

    private final ResultSetExtractor<TestEvent> resultSetExtractor = mockGeneric(ResultSetExtractor.class);
    private final JDBCDatabaseConnectionManager databaseConnectionProvider = mock(JDBCDatabaseConnectionManager.class);
    private final PreparedStatementSetter<TestLookupId> queryPreparedStatementSetter = mockGeneric(PreparedStatementSetter.class);
    private final Connection connection = mock(Connection.class);
    private final PreparedStatement queryPreparedStatement = mock(PreparedStatement.class);
    private final ResultSet resultSet = mock(ResultSet.class);
    private final Logger logger = mock(Logger.class);

    private final JdbcRecordReader<TestLookupId, TestEvent> jdbcReader = new JdbcRecordReader<>(logger, QUERY, resultSetExtractor, queryPreparedStatementSetter, databaseConnectionProvider, TestEvent.class);

    @Test
    public void missingEventBlowsUp() throws Exception {
        when(databaseConnectionProvider.getDBConnection()).thenReturn(Optional.of(connection));
        when(connection.prepareStatement(QUERY)).thenReturn(queryPreparedStatement);
        when(queryPreparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        TestLookupId lookupId = new TestLookupId();

        jdbcReader.readRecord(lookupId);

        verify(logger).info("Reading 'TestEvent' for 'JdbcRecordReaderTest.TestLookupId[]'");
    }

    @Test
    public void readsEvent() throws Exception {
        TestEvent testEvent = new TestEvent();

        EnhancedResultSet enhancedResultSet = new EnhancedResultSet(this.resultSet);
        when(databaseConnectionProvider.getDBConnection()).thenReturn(Optional.of(connection));
        when(connection.prepareStatement(QUERY)).thenReturn(queryPreparedStatement);
        when(queryPreparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSetExtractor.extract(enhancedResultSet)).thenReturn(testEvent);

        TestLookupId lookupId = new TestLookupId();

        assertThat(jdbcReader.readRecord(lookupId)).contains(testEvent);

        InOrder inOrder = inOrder(logger, connection, queryPreparedStatementSetter, queryPreparedStatement, resultSet, resultSetExtractor);
        inOrder.verify(logger).info("Reading 'TestEvent' for 'JdbcRecordReaderTest.TestLookupId[]'");
        inOrder.verify(logger).debug("Using SQL:\nquery");
        inOrder.verify(connection).prepareStatement(QUERY);
        inOrder.verify(queryPreparedStatementSetter).setParameters(new EnhancedPreparedStatement(queryPreparedStatement), lookupId);
        inOrder.verify(queryPreparedStatement).executeQuery();
        inOrder.verify(resultSet).next();

        inOrder.verify(resultSetExtractor).extract(enhancedResultSet);
        inOrder.verify(resultSet).next();
        inOrder.verify(logger).info("Found 'JdbcRecordReaderTest.TestEvent[]' for 'JdbcRecordReaderTest.TestLookupId[]'");
        inOrder.verify(resultSet).close();
        inOrder.verify(queryPreparedStatement).close();
        inOrder.verify(connection).close();
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void notFoundEvent() throws Exception {
        when(databaseConnectionProvider.getDBConnection()).thenReturn(Optional.of(connection));
        when(connection.prepareStatement(QUERY)).thenReturn(queryPreparedStatement);
        when(queryPreparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        TestLookupId lookupId = new TestLookupId();

        assertThat(jdbcReader.readRecord(lookupId)).isEmpty();

        InOrder inOrder = inOrder(logger, connection, queryPreparedStatementSetter, queryPreparedStatement, resultSet, resultSetExtractor);
        inOrder.verify(logger).info("Reading 'TestEvent' for 'JdbcRecordReaderTest.TestLookupId[]'");
        inOrder.verify(connection).prepareStatement(QUERY);
        inOrder.verify(queryPreparedStatementSetter).setParameters(new EnhancedPreparedStatement(queryPreparedStatement), lookupId);
        inOrder.verify(queryPreparedStatement).executeQuery();
        inOrder.verify(resultSet).next();
        inOrder.verify(logger).info("Could not find 'TestEvent' for 'JdbcRecordReaderTest.TestLookupId[]'");
        inOrder.verify(resultSet).close();
        inOrder.verify(queryPreparedStatement).close();
        inOrder.verify(connection).close();
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void moreThanOneEvent() throws Exception {
        when(databaseConnectionProvider.getDBConnection()).thenReturn(Optional.of(connection));
        when(connection.prepareStatement(QUERY)).thenReturn(queryPreparedStatement);
        when(queryPreparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, true);
        EnhancedResultSet enhancedResultSet = new EnhancedResultSet(resultSet);
        when(resultSetExtractor.extract(enhancedResultSet)).thenReturn(new TestEvent());

        TestLookupId lookupId = new TestLookupId();

        assertThatThrownBy(() -> jdbcReader.readRecord(lookupId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Found more than one 'TestEvent' for 'JdbcRecordReaderTest.TestLookupId[]'");

        InOrder inOrder = inOrder(logger, connection, queryPreparedStatementSetter, queryPreparedStatement, resultSet, resultSetExtractor);
        inOrder.verify(logger).info("Reading 'TestEvent' for 'JdbcRecordReaderTest.TestLookupId[]'");
        inOrder.verify(connection).prepareStatement(QUERY);
        inOrder.verify(queryPreparedStatementSetter).setParameters(new EnhancedPreparedStatement(queryPreparedStatement), lookupId);
        inOrder.verify(queryPreparedStatement).executeQuery();
        inOrder.verify(resultSet).next();
        inOrder.verify(resultSetExtractor).extract(enhancedResultSet);
        inOrder.verify(resultSet).next();
        inOrder.verify(resultSet).close();
        inOrder.verify(queryPreparedStatement).close();
        inOrder.verify(connection).close();
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void connectionThrowsSQLException() throws Exception {
        SQLException cause = new SQLException("uh oh");
        when(databaseConnectionProvider.getDBConnection()).thenReturn(Optional.of(connection));
        when(connection.prepareStatement(QUERY)).thenThrow(cause);

        TestLookupId lookupId = new TestLookupId();

        assertThatThrownBy(() -> jdbcReader.readRecord(lookupId))
                .isInstanceOf(IllegalStateException.class)
                .hasCause(cause)
                .hasMessage("Failed to read 'TestEvent' for 'JdbcRecordReaderTest.TestLookupId[]'");

        InOrder inOrder = inOrder(connection);
        inOrder.verify(connection).close();
    }

    private class TestLookupId extends ValueType {

    }

    private class TestEvent extends ValueType {

    }
}