package br.com.reclameaqui.teste.utils;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.reclameaqui.teste.exceptions.JsonConverterException;

@Component
public class Utils  {
	
	public static String asJsonString(final Object obj) {
	    try {
	        final ObjectMapper mapper = new ObjectMapper();
	        final String jsonContent = mapper.writeValueAsString(obj);
	        return jsonContent;
	    } catch (Exception e) {
	        throw new JsonConverterException("Nao foi possivel converter o objeto em json");
	    }
	}

}