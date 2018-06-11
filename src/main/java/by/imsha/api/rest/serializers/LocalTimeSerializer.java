package by.imsha.api.rest.serializers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
/**
 * Used to serialize java.time.LocalTime, which is not a common JSON
 * type, so we have to create a custom serialize method;.
 *
 * @author Andrei Misan
 */
@Component
public class LocalTimeSerializer extends JsonSerializer<LocalTime> {


    private String timeFormat = "HH:mm";

    @Override
    public void serialize(LocalTime time, JsonGenerator gen, SerializerProvider provider)
            throws IOException{
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(timeFormat);
        gen.writeString(time.format(formatter));
    }
}