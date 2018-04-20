package org.sql2o;

import java.sql.Connection;
import javax.sql.DataSource;
import org.junit.Test;
import org.sql2o.quirks.NoQuirks;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Test to check if the autoCommit state has been reset upon close
 */
public class ConnectionTransactionTest {

    @Test
    public void beginTransaction() throws Exception {
        final DataSource dataSource = mock(DataSource.class);
        final java.sql.Connection connectionMock = mock(Connection.class);

        // mocked behaviour
        when(dataSource.getConnection()).thenReturn(connectionMock);
        when(connectionMock.getAutoCommit()).thenReturn(true);
        when(connectionMock.isClosed()).thenReturn(false);

        final Sql2o sql2o = new Sql2o(dataSource, new NoQuirks());
        final org.sql2o.Connection sql2oConnection = sql2o.beginTransaction();
        sql2oConnection.close();

        // Verifications
        verify(dataSource).getConnection();
        verify(connectionMock, atLeastOnce()).getAutoCommit();
        // called on beginTransaction
        verify(connectionMock, times(1)).setAutoCommit(eq(false));
        // called on closeConnection to reset autocommit state
        verify(connectionMock, times(1)).setAutoCommit(eq(true));
        verify(connectionMock, atLeastOnce()).setTransactionIsolation(anyInt());
        verify(connectionMock, times(1)).isClosed();
        verify(connectionMock, times(1)).close();
        verifyNoMoreInteractions(connectionMock, dataSource);
    }
}