package py.org.fundacionparaguaya.pspserver.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SurveyUISchema;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by rodrigovillalba on 8/28/17.
 */
public class TestHelper {

    private final static ObjectMapper mapper = new ObjectMapper();

    public static String mapToJson(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

    public static Object mapToObject(String contentAsString, Class<?> clazz) throws JsonParseException, JsonMappingException, IOException {
        return mapper.readValue(contentAsString, clazz);
    }

    public static Object mapToObjectFromFile(String fileName, Class<?> clazz) {
        try {
            InputStream is = TestHelper.class.getResourceAsStream(fileName);
            return mapper.readValue(is, clazz);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
