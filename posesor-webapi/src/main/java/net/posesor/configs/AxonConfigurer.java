package net.posesor.configs;

import com.mongodb.MongoClient;
import lombok.val;
import org.axonframework.commandhandling.AsynchronousCommandBus;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventHandlingConfiguration;
import org.axonframework.eventhandling.saga.repository.SagaStore;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.mongo.DefaultMongoTemplate;
import org.axonframework.mongo.MongoTemplate;
import org.axonframework.mongo.eventhandling.saga.repository.MongoSagaStore;
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.mongo.eventsourcing.tokenstore.MongoTokenStore;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfigurer {

    @Autowired
    public void configure(EventHandlingConfiguration eventHandlingConfiguration) {
        // default all processors to tracking mode.
        // https://docs.axonframework.org/part3/spring-boot-autoconfig.html#event-handling-configuration
        eventHandlingConfiguration.usingTrackingProcessors();
    }


    /**
     *
     * @param mongoClient connection of Mongo engine used as EventStorage.
     * @param databaseName name of database used as EventStorage.
     * @return
     */
    @Bean
    public MongoTemplate defaultMongoTemplate(MongoClient mongoClient, @Value("${spring.data.mongodb.database}") String databaseName) {
        return new DefaultMongoTemplate(mongoClient, databaseName);
    }

    /**
     * Creates storage for event to support Axon event sourcing in Posesor.
     * <p>
     *     @implNote
     * @return EventStorage used by axon to store and replay events.
     */
    @Bean
    public EventStorageEngine storageEngine(MongoTemplate template) {
        return new MongoEventStorageEngine(template);
    }

    @Bean
    public TokenStore tokenStore(MongoTemplate mongoTemplate) {
        val serializer = new XStreamSerializer();
        return new MongoTokenStore(mongoTemplate, serializer);
    }

    @Bean
    public EventStore eventBus(EventStorageEngine storageEngine) {
        return new EmbeddedEventStore(storageEngine);
    }

    @Bean
    public SagaStore sagaStore(MongoTemplate mongoTemplate) {
        return new MongoSagaStore(mongoTemplate);
    }

    @Bean
    CommandBus commandBus() {
        return new AsynchronousCommandBus();
    }
}
