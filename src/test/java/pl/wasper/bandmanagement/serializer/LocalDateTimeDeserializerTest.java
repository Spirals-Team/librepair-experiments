package pl.wasper.bandmanagement.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.*;

public class LocalDateTimeDeserializerTest {
    private ObjectMapper mapper;

    @Before
    public void before() {
        mapper = new ObjectMapper();
    }

    @Test
    public void itShouldDeserializeLocalDateTime() throws IOException {
        String testJson = "{\"testField\": \"2018-02-28 09:49\"}";

        LocalDateTimeTest localDateTimeTest = mapper.readValue(testJson, LocalDateTimeTest.class);

        assertNotNull(localDateTimeTest.getTestField());
        assertEquals(localDateTimeTest.getTestField().getYear(), 2018);
        assertEquals(localDateTimeTest.getTestField().getMonth(), Month.FEBRUARY);
        assertEquals(localDateTimeTest.getTestField().getDayOfMonth(), 28);
        assertEquals(localDateTimeTest.getTestField().getHour(), 9);
        assertEquals(localDateTimeTest.getTestField().getMinute(), 49);
    }

    @Test
    public void itShouldDeserializeNullValue() throws IOException {
        String testJson = "{\"testField\": null}";

        LocalDateTimeTest localDateTimeTest = mapper.readValue(testJson, LocalDateTimeTest.class);

        assertNull(localDateTimeTest.getTestField());
    }

    private static class LocalDateTimeTest {
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        private LocalDateTime testField;

        public LocalDateTime getTestField() {
            return testField;
        }

        public void setTestField(LocalDateTime testField) {
            this.testField = testField;
        }
    }
}
