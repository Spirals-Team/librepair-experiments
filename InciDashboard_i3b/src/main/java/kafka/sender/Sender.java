package kafka.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import model.Incidence;

@Service
public class Sender {

    private static final Logger LOG = LoggerFactory.getLogger(Sender.class);

    @Autowired
    private KafkaTemplate<String, Incidence> kafkaTemplate;

   // @Value("${app.topic.foo}")
    private String topic = "test";

    public void send(Incidence inci){
        LOG.info("sending message='{}' to topic='{}'", inci.toString(), topic);
        kafkaTemplate.send(topic, inci);
    }
}