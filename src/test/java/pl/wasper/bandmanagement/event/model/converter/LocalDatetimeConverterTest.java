package pl.wasper.bandmanagement.event.model.converter;

import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.*;

public class LocalDatetimeConverterTest {
    private LocalDatetimeConverter converter;
    private LocalDateTime dateTime;
    private Timestamp timestamp;

    @Before
    public void before() {
        converter = new LocalDatetimeConverter();

        dateTime = LocalDateTime.of(2018, Month.FEBRUARY, 26, 23, 30);
        timestamp = Timestamp.valueOf(dateTime);
    }

    @Test
    public void itShouldConvertLocalDateTimeToTimestamp() {
        assertEquals(converter.convertToDatabaseColumn(dateTime), timestamp);
    }

    @Test
    public void itShouldConvertTimestampToLocalDatetime() {
        assertEquals(converter.convertToEntityAttribute(timestamp), dateTime);
    }
}
