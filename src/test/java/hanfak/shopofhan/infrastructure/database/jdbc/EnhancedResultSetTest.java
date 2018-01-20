package hanfak.shopofhan.infrastructure.database.jdbc;

import hanfak.shopofhan.infrastructure.database.jdbc.helperlibrary.EnhancedResultSet;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import static hanfak.shopofhan.infrastructure.database.jdbc.helperlibrary.OracleColumnAdapters.adaptTimeStampToZonedDateTime;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EnhancedResultSetTest implements WithAssertions{
    private static final String COLUMN_NAME = "COLUMN_NAME";
    private final ResultSet resultSet = mock(ResultSet.class);
    private final EnhancedResultSet enhancedResultSet = new EnhancedResultSet(resultSet);

    @Test
    public void shouldGetLong() throws SQLException {
        when(resultSet.getLong(COLUMN_NAME)).thenReturn(999L);

        assertThat(enhancedResultSet.getLong(COLUMN_NAME)).isEqualTo(999L);
    }

    @Test
    public void shouldGetInt() throws SQLException {
        when(resultSet.getInt(COLUMN_NAME)).thenReturn(998);

        assertThat(enhancedResultSet.getInt(COLUMN_NAME)).isEqualTo(998);
    }

    @Test
    public void shouldGetZonedDateTimeByAdaptingTimestamp() throws SQLException {
        Timestamp timestamp = new Timestamp(1000);
        when(resultSet.getTimestamp(COLUMN_NAME)).thenReturn(timestamp);

        assertThat(enhancedResultSet.getZonedDateTime(COLUMN_NAME)).isEqualTo(adaptTimeStampToZonedDateTime(timestamp));
    }

    @Test
    public void shouldGetEmptyString() throws SQLException {
        when(resultSet.getString(COLUMN_NAME)).thenReturn("");

        assertThat(enhancedResultSet.getString(COLUMN_NAME)).isEqualTo("");
    }

    @Test
    public void shouldGetNonEmptyString() throws SQLException {
        when(resultSet.getString(COLUMN_NAME)).thenReturn("string");

        assertThat(enhancedResultSet.getString(COLUMN_NAME)).isEqualTo("string");
    }

    @Test
    public void shouldGetFalse() throws SQLException {
        when(resultSet.getBoolean(COLUMN_NAME)).thenReturn(false);

        assertThat(enhancedResultSet.getBoolean(COLUMN_NAME)).isEqualTo(false);
    }

    @Test
    public void shouldGetTrue() throws SQLException {
        when(resultSet.getBoolean(COLUMN_NAME)).thenReturn(true);

        assertThat(enhancedResultSet.getBoolean(COLUMN_NAME)).isEqualTo(true);
    }
}