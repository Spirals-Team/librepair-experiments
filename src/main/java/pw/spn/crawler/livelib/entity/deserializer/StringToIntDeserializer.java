package pw.spn.crawler.livelib.entity.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class StringToIntDeserializer extends JsonDeserializer<Integer> {

    @Override
    public Integer deserialize(JsonParser parser, DeserializationContext context)
            throws IOException, JsonProcessingException {
        String text = parser.getText();
        if (text != null) {
            try {
                return Integer.parseInt(text);
            } catch (Exception e) {
            }
        }
        return 0;
    }

}
