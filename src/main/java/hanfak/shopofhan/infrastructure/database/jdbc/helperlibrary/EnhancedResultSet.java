package hanfak.shopofhan.infrastructure.database.jdbc.helperlibrary;


import hanfak.shopofhan.domain.crosscutting.ValueType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;

// TODO implement interface for different databases
public class EnhancedResultSet extends ValueType {

    private final ResultSet resultSet;

    public EnhancedResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public long getLong(String columnName) throws SQLException {
        return resultSet.getLong(columnName);
    }

    public int getInt(String columnName) throws SQLException {
        return resultSet.getInt(columnName);
    }

    public ZonedDateTime getZonedDateTime(String columnName) throws SQLException {
        return OracleColumnAdapters.adaptTimeStampToZonedDateTime(resultSet.getTimestamp(columnName));
    }

    public String getString(String columnName) throws SQLException {
        return resultSet.getString(columnName);
    }

    public boolean getBoolean(String columnName) throws SQLException {
        return resultSet.getBoolean(columnName);
    }
}
