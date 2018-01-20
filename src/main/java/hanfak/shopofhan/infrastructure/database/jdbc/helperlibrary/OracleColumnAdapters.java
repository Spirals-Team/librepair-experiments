package hanfak.shopofhan.infrastructure.database.jdbc.helperlibrary;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

import static java.time.Instant.ofEpochMilli;
import static java.time.ZoneId.systemDefault;

//TODO: these methods should probably only be used in the EnhancedResultSet and EnhancedPreparedStatement
//If that is not the case, it means that we need to migrate Readers and Writers into repositories
public class OracleColumnAdapters {

    public static ZonedDateTime adaptTimeStampToZonedDateTime(Timestamp timestamp) {
        return ofEpochMilli(timestamp.getTime()).atZone(systemDefault());
    }
}
