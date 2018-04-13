package guru.bonacci.spectre.spectreshared.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * To retrieve some spectre data from ES
 * Define what you need...
 */
@Document(indexName = "logstash-spectre", type = "logs", shards = 1, replicas = 0, refreshInterval = "-1")
public class Spec {

	// even too lazy for getters and setters today...
	@Id
	public String id;

	@Field(type = FieldType.text)
	public String message;

	@Field(type = FieldType.Nested)
	public Geoip geoip = new Geoip();

	// Define what you need...
	public static class Geoip {

		public Geoip() {}

		@Field(type = FieldType.keyword)
		public String country_code2;

		@Field(type = FieldType.Double)
		public Double latitude;

		@Field(type = FieldType.Double)
		public Double longitude;
	}
}
