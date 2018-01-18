package pw.spn.crawler.livelib.entity.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import pw.spn.crawler.livelib.entity.BookStatus;

public class StringToBookStatusDeserializer extends JsonDeserializer<BookStatus> {

    @Override
    public BookStatus deserialize(JsonParser parser, DeserializationContext context)
            throws IOException, JsonProcessingException {
        int status = -1;
        String text = parser.getText();
        if (text != null) {
            try {
                status = Integer.parseInt(text);
            } catch (Exception e) {
            }
        }
        return BookStatus.fromId(status);
    }

}
