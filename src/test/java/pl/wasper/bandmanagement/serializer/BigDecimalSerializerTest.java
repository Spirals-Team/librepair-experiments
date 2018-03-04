package pl.wasper.bandmanagement.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jayway.jsonpath.JsonPath;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.Assert.*;

public class BigDecimalSerializerTest {
    private ObjectMapper mapper;

    @Before
    public void before() {
        mapper = new ObjectMapper();
    }

    @Test
    public void itShouldSerializeDoubleValue() throws IOException {
        BigDecimalTest test = new BigDecimalTest();
        test.setBigDecimal(new BigDecimal("123.234"));

        String jsonString = mapper.writeValueAsString(test);

        String parsedValue = JsonPath.parse(jsonString).read("$.bigDecimal").toString();
        System.out.printf(parsedValue);

        assertTrue(new BigDecimal("123.23").compareTo(new BigDecimal(parsedValue)) == 0);
    }

    @Test
    public void itShouldMissNullValue() throws IOException {
        BigDecimalTest test = new BigDecimalTest();

        String jsonString = mapper.writeValueAsString(test);

        assertNull(JsonPath.parse(jsonString).read("$.bigDecimal"));
    }

    private class BigDecimalTest {
        @JsonSerialize(using = BigDecimalSerializer.class)
        private BigDecimal bigDecimal;

        public BigDecimal getBigDecimal() {
            return bigDecimal;
        }

        public void setBigDecimal(BigDecimal bigDecimal) {
            this.bigDecimal = bigDecimal;
        }
    }
}
