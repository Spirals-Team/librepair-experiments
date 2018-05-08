package edu.unq.desapp.grupo_a.backend.utils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

public class JSONDateDeserialize extends JsonDeserializer<Date> {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	@Override
	public Date deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        String date = jp.getText();
        Date newDate = null;
		try {
			newDate = dateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return newDate;
	}
    
}