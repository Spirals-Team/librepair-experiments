package guru.bonacci.spectre.weather.services;

import java.util.Map;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import guru.bonacci.spectre.spectreshared.persistence.Spec;

/**
 * Specific fields for weather
 */
public class WeatherSpec extends Spec {

	@Field(type = FieldType.Object)
	public Map<String,Object> weather;
}
