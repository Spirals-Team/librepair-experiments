package hanfak.shopofhan.infrastructure.database.jdbc;

import hanfak.shopofhan.infrastructure.database.jdbc.helperlibrary.EnhancedPreparedStatement;
import org.junit.Test;
import org.mockito.InOrder;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;


public class EnhancedPreparedStatementTest {
    private static final ZonedDateTime NOW = ZonedDateTime.now();
    private static final ZonedDateTime A_ZONED_DATE_TIME = ZonedDateTime.of(2016, 10, 20, 17, 30, 0, 0, ZoneId.systemDefault());
    private static final String A_STRING = "I am a string";
    private static final long A_LONG = 1L;
    private final PreparedStatement preparedStatement = mock(PreparedStatement.class);
    private final EnhancedPreparedStatement enhancedPreparedStatement = new EnhancedPreparedStatement(preparedStatement);

    @Test
    public void shouldPrepareTheStatements() throws SQLException {
        enhancedPreparedStatement.setLong(A_LONG);
        enhancedPreparedStatement.setZonedDateTime(NOW);
        enhancedPreparedStatement.setString(A_STRING);
        enhancedPreparedStatement.setZonedDateTime(A_ZONED_DATE_TIME);
        enhancedPreparedStatement.setBoolean(true);
        enhancedPreparedStatement.setBoolean(false);

        InOrder inOrder = inOrder(preparedStatement);
        inOrder.verify(preparedStatement).setLong(1, A_LONG);
        inOrder.verify(preparedStatement).setTimestamp(2, Timestamp.from(NOW.toInstant()));
        inOrder.verify(preparedStatement).setString(3, A_STRING);
        inOrder.verify(preparedStatement).setTimestamp(4, Timestamp.from(A_ZONED_DATE_TIME.toInstant()));
        inOrder.verify(preparedStatement).setBoolean(5, true);
        inOrder.verify(preparedStatement).setBoolean(6, false);
    }
}