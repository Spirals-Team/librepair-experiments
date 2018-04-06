package ch.ntb.jass.common;

import ch.ntb.jass.common.proto.Message;
import ch.ntb.jass.common.util.TestDataGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.matchprocessor.SubclassMatchProcessor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class JsonSerializationTestCase {
    private static final Class<Message> ROOT_CLASS = Message.class;
    public static final String ENTITY_PACKAGE = "ch.ntb.jass.common.entities";

    private static final long RANDOM_SEED = 3094214091L;

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<Object[]> params() {
        // find all subclasses
        Set<Class<?>> allSubclasses = new HashSet<>();

        // find all non-abstract message classes
        new FastClasspathScanner()
                .matchSubclassesOf(ROOT_CLASS, new SubclassMatchProcessor<Message>() {
                    @Override
                    public void processMatch(Class<? extends Message> subclass) {
                        if (!Modifier.isAbstract(subclass.getModifiers()))
                            allSubclasses.add(subclass);
                    }
                }).scan();

        // return test parameters
        return allSubclasses.stream()
                .map(aClass -> new Object[]{aClass, Collections.unmodifiableSet(allSubclasses)})
                .collect(Collectors.toList());
    }

    private final Class<? extends Message> messageType;
    private final Set<Class<? extends Message>> allSubclasses;

    private ObjectMapper objectMapper = new ObjectMapper();
    private ObjectReader objectReader = objectMapper.readerFor(Message.class);
    private ObjectWriter objectWriter = objectMapper.writerFor(Message.class);

    private List<Message> testData;

    public JsonSerializationTestCase(Class<? extends Message> messageType, Set<Class<? extends Message>> allSubclasses) {
        this.messageType = messageType;
        this.allSubclasses = allSubclasses;
    }

    @Before
    public void generateTestData() throws Exception {
        testData = new ArrayList<>();

        // default constructed message
        {
            Message message = messageType.newInstance();
            testData.add(message);
        }

        // randomly generated messages
        Random random = new Random(RANDOM_SEED);
        TestDataGenerator testDataGenerator = new TestDataGenerator(Collections.singleton(ENTITY_PACKAGE), 10);
        for (int i = 0; i < 10; ++i) {
            Message pojo = testDataGenerator.generatePOJO(random, messageType);
            testData.add(pojo);
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

            assertTrue("Json string must not contain line endings", jsonString.matches("^[^\r\n]+$"));
            assertEquals(jsonString, jsonStringReserialized);
            assertEquals(message, deserializedMessage);
            assertEquals(message.getClass(), deserializedMessage.getClass());
        }
    }
}
