
package coaching.json;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Properties;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assume.assumeNotNull;

public class JsonTest {

    private static final Logger LOG = LoggerFactory.getLogger(JsonTest.class);

    @Test
    public void testNew() {
        final JSONObject json = new JSONObject();
        assumeNotNull(json);

        json.put("aString", "Alice");
        json.put("aInteger", new Integer(100));
        json.put("aBalance", new BigDecimal("12345678.90"));
        json.put("aBoolean", new Boolean(true));

        LOG.info(json.toString());
    }

    @Test
    public void testSystem() {
        final JSONObject json = new JSONObject();
        assumeNotNull(json);
        final Properties properties = System.getProperties();

        final Set<Object> keySet = properties.keySet();
        for (final Object key : keySet) {
            final String keyString = key.toString();
            final String property = System.getProperty(keyString);
            json.put(keyString, property);
        }

        LOG.info(json.toString());
    }

    @Test
    public void test() {
        final JSONObject json = new JSONObject();
        json.put("name", "Alice");
        json.put("email", "alice@example.com");

        final JSONArray list = new JSONArray();
        list.add("value-1");
        list.add("value-2");
        list.add("value-3");

        json.put("list", list);

        write(json);

        LOG.info(json.toString());

    }

    private void write(final JSONObject json) {
        try (FileWriter file = new FileWriter("./target/test.json")) {

            file.write(json.toJSONString());
            file.flush();

        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
