package me.pagar.converter;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

import me.pagar.PagarMeError;
import me.pagar.exception.PagarMeException;
import me.pagar.PagarMeError;
import me.pagar.exception.PagarMeException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JSonConverter {


	public static final Gson gson;

	static{
		gson = new GsonBuilder().setPrettyPrinting().create();
	}

	public static <T> T getAsObject(String json, Class<T> clazz) throws PagarMeException {
		if(clazz != PagarMeError.class)
			validate(json);
		return (T)gson.fromJson(json, clazz);
	}

	private static <T> Collection<T> getAsList(String json,Type listType)throws PagarMeException{
		validate(json);
		return gson.fromJson(json, listType);
	}
	public static Map<String,Object> objectToMap(Object object){
		String json = gson.toJson(object);
		Map<String, Object> map = gson.fromJson(json, new TypeToken<Map<String, Object>>() {}.getType());
		return map;
	}

	private static void validate(String json) throws PagarMeException{
		if (json.contains("errors"))
			throw new PagarMeException(json);
	}
}