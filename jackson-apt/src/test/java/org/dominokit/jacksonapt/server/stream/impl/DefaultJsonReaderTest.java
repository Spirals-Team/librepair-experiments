package org.dominokit.jacksonapt.server.stream.impl;

import org.dominokit.jacksonapt.server.stream.AbstractJsonReaderTest;
import org.dominokit.jacksonapt.stream.JsonReader;
import org.dominokit.jacksonapt.stream.JsonToken;
import org.dominokit.jacksonapt.stream.impl.DefaultJsonReader;
import org.dominokit.jacksonapt.stream.impl.MalformedJsonException;
import org.dominokit.jacksonapt.stream.impl.StringReader;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

/**
 * @author Nicolas Morel
 */
public class DefaultJsonReaderTest extends AbstractJsonReaderTest {

    @Override
    public JsonReader newJsonReader(String input) {
        return new DefaultJsonReader(new StringReader(input));
    }

    @Test
    public void testStrictVeryLongNumber() {
        JsonReader reader = newJsonReader("[0." + repeat('9', 8192) + "]");
        reader.beginArray();
        try {
            assertThat(1d).isEqualTo(reader.nextDouble());
            fail("failed");
        } catch (MalformedJsonException expected) {
        }
    }

    @Test
    public void testLenientVeryLongNumber() {
        JsonReader reader = newJsonReader("[0." + repeat('9', 8192) + "]");
        reader.setLenient(true);
        reader.beginArray();
        assertThat(JsonToken.STRING).isEqualTo(reader.peek());
        assertThat(1d).isEqualTo(reader.nextDouble());
        reader.endArray();
        assertThat(JsonToken.END_DOCUMENT).isEqualTo(reader.peek());
    }
}
