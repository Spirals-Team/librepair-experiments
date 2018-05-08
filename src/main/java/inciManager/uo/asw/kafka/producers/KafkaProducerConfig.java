package inciManager.uo.asw.kafka.producers;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import inciManager.uo.asw.dbManagement.model.Incidencia;


@Configuration
public class KafkaProducerConfig {
	
	/**
	 * Metodo encargado entre otras cosas de la comunicacion con
	 * kafka cloud.
	 * @return un objeto de tipo ProducerFactory
	 */
	@Bean
	public ProducerFactory<String, Incidencia> producerFactory() {
		String usuario = "fsj71lf2";
		String password = "1XUrUi1Zy9lopTAwx5q-Jho3UfkWSFZN";
		String jaasConfig = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"" + usuario + "\" password=\"" + password + "\";";
	    String btServer = "ark-01.srvs.cloudkafka.com:9094,ark-02.srvs.cloudkafka.com:9094,ark-03.srvs.cloudkafka.com:9094";
		Map<String, Object> configProps = new HashMap<>();
	    configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, btServer);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		configProps.put("security.protocol", "SASL_SSL");
		configProps.put("sasl.mechanism", "SCRAM-SHA-256");
		configProps.put("sasl.jaas.config", jaasConfig);
	    return new DefaultKafkaProducerFactory<>(configProps);
	}
 
	@Bean
	public KafkaTemplate<String, Incidencia> kafkaTemplate() {
	    return new KafkaTemplate<>(producerFactory());
	}
}
