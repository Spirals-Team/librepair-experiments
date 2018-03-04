package pl.wasper.bandmanagement.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jayway.jsonpath.JsonPath;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.*;

public class LocalDateTimeSerializerTest {
    private ObjectMapper mapper;

    @Before
    public void before() {
        mapper = new ObjectMapper();
    }

    @Test
    public void itShouldSerializeLocalDateTime() throws JsonProcessingException {
        LocalDateTimeTest localDateTimeTest = new LocalDateTimeTest();
        localDateTimeTest.setTestField(LocalDateTime.of(2018, Month.FEBRUARY, 28, 8, 32));

        String jsonString = mapper.writeValueAsString(localDateTimeTest);

        String dateStringValue = JsonPath.parse(jsonString).read("$.testField");

        assertEquals(dateStringValue, "2018-02-28 08:32");
    }

    @Test
    public void itShouldSerializeNullValue() throws JsonProcessingException {
        LocalDateTimeTest localDateTimeTest = new LocalDateTimeTest();

        String jsonString = mapper.writeValueAsString(localDateTimeTest);

        String dateStringValue = JsonPath.parse(jsonString).read("$.testField");

        assertNull(dateStringValue);
    }

    private class LocalDateTimeTest {
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        private LocalDateTime testField;

        public LocalDateTime getTestField() {
            return testField;
        }

        public void setTestField(LocalDateTime testField) {
            this.testField = testField;
        }
    }
}
