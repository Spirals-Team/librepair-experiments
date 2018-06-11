package by.imsha.api.rest.serializers;


import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
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
public class LocalDateSerializer extends JsonSerializer<LocalDate> {

    private String dateFormat = "MM/dd/yyyy";

    @Override
    public void serialize(LocalDate date, JsonGenerator gen, SerializerProvider provider)
            throws IOException{
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        gen.writeString(date.format(formatter));
    }
}