package hanfak.shopofhan.infrastructure.database.jdbc;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static hanfak.shopofhan.infrastructure.database.jdbc.helperlibrary.OracleColumnAdapters.adaptTimeStampToZonedDateTime;

public class OracleColumnAdaptersTest implements WithAssertions {
    @Test
    public void timestampsAreAdaptedUpToMilliseconds() throws Exception {
        ZonedDateTime zonedDateTime = adaptTimeStampToZonedDateTime(Timestamp.valueOf(LocalDateTime.of(2016, 1, 5, 23, 45, 59, 453)));
        ZonedDateTime expected = ZonedDateTime.of(2016, 1, 5, 23, 45, 59, 0, ZoneId.systemDefault());
        assertThat(zonedDateTime).isEqualTo(expected);
    }
}