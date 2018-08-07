package com.hedvig.productPricing;

import com.hedvig.productPricing.testHelpers.TestTool;
import org.axonframework.eventsourcing.eventstore.AbstractEventStore;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

//@ComponentScan("com.hedvig.productPricing")
public class AxonTestConfiguration {

    @Bean
    public EventStorageEngine axonConfiguration() {

        InMemoryEventStorageEngine inMemoryEventStorageEngine = new InMemoryEventStorageEngine();
        return inMemoryEventStorageEngine;
    }


    /*
    @Bean
    public void configure(EventHandlingConfiguration config) {
        System.out.println("Configuring EventHandling!");
        config.registerSubscribingEventProcessor("com.hedvig.productPricing.service.query");
        //config.usingTrackingProcessors();
    }*/


    /*
    @Bean
    PlatformTransactionManager transactionManager(EntityManagerFactory dataSource) {
        return new JpaTransactionManager(dataSource);
    }*/

    @Qualifier("eventStore")
    @Bean(name = "eventBus")
    public EmbeddedEventStore eventStore(EventStorageEngine storageEngine, AxonConfiguration configuration) {
        return new EmbeddedEventStore(storageEngine, configuration.messageMonitor(EventStore.class, "eventStore"));
    }

    @Bean TestTool testTool(AbstractEventStore eventStore){
        return new TestTool(eventStore);
    }
}
