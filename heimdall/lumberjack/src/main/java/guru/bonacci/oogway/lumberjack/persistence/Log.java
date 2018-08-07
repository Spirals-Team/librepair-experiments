package guru.bonacci.oogway.lumberjack.persistence;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "visits")
public class Log {

	@Id
	private Instant moment;

	@Field
	private String apiKey;

	public Log() {}

	public Log(String apiKey) {
		this.apiKey = apiKey;
	}

	public Instant getMoment() {
		return moment;
	}

	public void setMoment(Instant moment) {
		this.moment = moment;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
}
