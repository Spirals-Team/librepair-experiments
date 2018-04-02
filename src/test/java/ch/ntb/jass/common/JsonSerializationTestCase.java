package ch.ntb.jass.common;

import ch.ntb.jass.common.entities.CardColorEntity;
import ch.ntb.jass.common.entities.CardEntity;
import ch.ntb.jass.common.entities.CardValueEntity;
import ch.ntb.jass.common.proto.Message;
import ch.ntb.jass.common.proto.server_info_messages.TurnInfoMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JsonSerializationTestCase {
    private ObjectMapper objectMapper = new ObjectMapper();
    private ObjectReader objectReader = objectMapper.readerFor(Message.class);
    private ObjectWriter objectWriter = objectMapper.writerFor(Message.class);

    private List<Message> testData = new ArrayList<>();

    {
        {
            TurnInfoMessage message = new TurnInfoMessage();
            message.id = "42";
            message.laidCard = new CardEntity();
            message.laidCard.color = CardColorEntity.SCHILTE;
            message.laidCard.value = CardValueEntity.KOENIG;

            testData.add(message);
        }
    }

    @Test
    public void testSerialization() throws IOException {
        //test generic serialization and deserialization
        for (Message message : testData) {
            String jsonString = objectMapper.writeValueAsString(message);
            Message deserializedMessage = objectMapper.readValue(jsonString, Message.class);
            String jsonStringReserialized = objectMapper.writeValueAsString(message);

            assertEquals(jsonString, jsonStringReserialized);
            assertEquals(message, deserializedMessage);
            assertEquals(message.getClass(), deserializedMessage.getClass());
        }
    }

    @Test
    public void testSerializationWithSpecificReaderAndWriter() throws IOException {
        //test serialization and deserialization with specific reader and writer
        for (Message message : testData) {
            String jsonString = objectWriter.writeValueAsString(message);
            Message deserializedMessage = objectReader.readValue(jsonString);
            String jsonStringReserialized = objectWriter.writeValueAsString(message);

            assertTrue("Json string must not contain line endings", jsonString.matches("[^\r\n]+"));
            assertEquals(jsonString, jsonStringReserialized);
            assertEquals(message, deserializedMessage);
            assertEquals(message.getClass(), deserializedMessage.getClass());
        }
    }
}
