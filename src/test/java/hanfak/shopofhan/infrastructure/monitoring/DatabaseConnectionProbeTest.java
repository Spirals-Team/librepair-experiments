package hanfak.shopofhan.infrastructure.monitoring;

import hanfak.shopofhan.domain.crosscutting.Logger;
import hanfak.shopofhan.domain.monitoring.ProbeResult;
import hanfak.shopofhan.domain.monitoring.ProbeStatus;
import hanfak.shopofhan.infrastructure.database.jdbc.JDBCDatabaseConnectionManager;
import hanfak.shopofhan.infrastructure.properties.Settings;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DatabaseConnectionProbeTest implements WithAssertions {

    @Test
    public void nameMentionsDatabaseURL() {
        DatabaseConnectionProbe databaseConnectionProbe = new DatabaseConnectionProbe(logger, settings, connectionProvider);

        assertThat(databaseConnectionProbe.name()).isEqualTo("Database Connection to 'jdbc:mysql://172.17.0.3:3306/'");
    }

    @Test
    public void failsOnSQLException() throws Exception {
        SQLException sqlException = new SQLException("message");
        when(connectionProvider.getDBConnection()).thenReturn(Optional.of(connection));
        when(connection.prepareStatement("SELECT 1 FROM DUAL")).thenThrow(sqlException);

        DatabaseConnectionProbe databaseConnectionProbe = new DatabaseConnectionProbe(logger, settings, connectionProvider);

        ProbeResult result = databaseConnectionProbe.probe();

        assertThat(result.description).isEqualTo("SQLException: message");
        assertThat(result.status).isEqualTo(ProbeStatus.FAIL);
        verify(logger).error("SQLException: message");
    }

    @Test
    public void failsWhenTestQueryHasNoResult() throws Exception {
        when(connectionProvider.getDBConnection()).thenReturn(Optional.of(connection));
        when(connection.prepareStatement("SELECT 1 FROM DUAL")).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        DatabaseConnectionProbe databaseConnectionProbe = new DatabaseConnectionProbe(logger, settings, connectionProvider);

        ProbeResult result = databaseConnectionProbe.probe();

        assertThat(result.description).isEqualTo("Database test query 'SELECT 1 FROM DUAL' was not successful. Result was not 1.");
        assertThat(result.status).isEqualTo(ProbeStatus.FAIL);
    }

    @Test
    public void failsWhenTestQueryHasBadResult() throws Exception {
        when(connectionProvider.getDBConnection()).thenReturn(Optional.of(connection));
        when(connection.prepareStatement("SELECT 1 FROM DUAL")).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(999);

        DatabaseConnectionProbe databaseConnectionProbe = new DatabaseConnectionProbe(logger, settings, connectionProvider);

        ProbeResult result = databaseConnectionProbe.probe();

        assertThat(result.description).isEqualTo("Database test query 'SELECT 1 FROM DUAL' was not successful. Result was not 1.");
        assertThat(result.status).isEqualTo(ProbeStatus.FAIL);
    }

    private Settings settings() {
        Settings settings = mock(Settings.class);
        when(settings.databaseURL()).thenReturn(DATABASE_URL);
        return settings;
    }

    private static final String DATABASE_URL = "jdbc:mysql://172.17.0.3:3306/";

    private final Logger logger = mock(Logger.class);
    private final Settings settings = settings();
    private final JDBCDatabaseConnectionManager connectionProvider = mock(JDBCDatabaseConnectionManager.class);
    private final Connection connection = mock(Connection.class);
    private final PreparedStatement preparedStatement = mock(PreparedStatement.class);
    private final ResultSet resultSet = mock(ResultSet.class);
}