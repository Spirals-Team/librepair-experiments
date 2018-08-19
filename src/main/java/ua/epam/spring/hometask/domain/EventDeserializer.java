package ua.epam.spring.hometask.domain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by Vladimir_Vysokomorny on 15-Dec-17.
 */
/*1st approach use custom Deserializer and native Form class for uploading Event's file*/
public class EventDeserializer extends JsonDeserializer<Event> {

    @Override
    public Event deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        Event event = new Event();
        event.setName(node.get("name").asText());
        event.setBasePrice(node.get("basePrice").asDouble());
        event.setRating(EventRating.valueOf(node.get("rating").asText()));

        NavigableMap<LocalDateTime, Auditorium> auditoriums = new TreeMap<>();

        Iterator<Map.Entry<String, JsonNode>> it = node.get("auditoriums").fields();
        while(it.hasNext()){
            Map.Entry<String, JsonNode> entry = it.next();
            LocalDateTime ldt = LocalDateTime.parse(entry.getKey(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            String auditoriumName = entry.getValue().get("name").asText();
            Auditorium auditorium = new Auditorium();
            auditorium.setName(auditoriumName);
            auditoriums.put(ldt,auditorium);

        }
        event.setAuditoriums(auditoriums);
        event.setAirDates(new TreeSet<>(auditoriums.keySet()));

        return event;
    }
}
