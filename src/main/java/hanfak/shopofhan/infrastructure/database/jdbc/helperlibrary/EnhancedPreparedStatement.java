package hanfak.shopofhan.infrastructure.database.jdbc.helperlibrary;


import hanfak.shopofhan.domain.crosscutting.ValueType;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;

public class EnhancedPreparedStatement extends ValueType {

    private final PreparedStatement preparedStatement;
    private int index = 1;

    public EnhancedPreparedStatement(PreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
    }

    public void setLong(long aLong) throws SQLException {
        preparedStatement.setLong(index++, aLong);
    }

    public void setInt(int anInt) throws SQLException {
        preparedStatement.setInt(index++, anInt);
    }

    public void setBoolean(boolean aBoolean) throws SQLException {
        preparedStatement.setBoolean(index++, aBoolean);
    }

    public void setZonedDateTime(ZonedDateTime zonedDateTime) throws SQLException {
        preparedStatement.setTimestamp(index++, Timestamp.from(zonedDateTime.toInstant()));
    }

    public void setString(String string) throws SQLException {
        preparedStatement.setString(index++, string);
    }
}
