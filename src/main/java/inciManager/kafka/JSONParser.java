package inciManager.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import inciManager.entities.Incidencia;

public class JSONParser {

	public static String parseIncidence(Incidencia incidencia) {
		ObjectMapper mapper = new ObjectMapper();
		String str = null;
		try {
			str = mapper.writerWithView(Incidencia.class).writeValueAsString(incidencia);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return str;
	}

}
