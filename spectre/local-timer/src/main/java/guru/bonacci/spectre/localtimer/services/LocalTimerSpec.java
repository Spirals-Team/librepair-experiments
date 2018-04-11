package guru.bonacci.spectre.localtimer.services;

import java.util.Map;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import guru.bonacci.spectre.spectreshared.persistence.Spec;

/**
 * Specific fields for localtimer
 */
public class LocalTimerSpec extends Spec {

	@Field(type = FieldType.Object)
	public Map<String,Object> localtimer;
}
