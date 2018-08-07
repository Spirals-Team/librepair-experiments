package com.hedvig.productPricing.service.configuration;

import com.hedvig.productPricing.service.events.upcaster.ContractSignedEvent_v1;
import com.hedvig.productPricing.service.events.upcaster.ProductCreatedEvent_v1;
import com.hedvig.productPricing.service.sagas.ContractSignedSaga;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.config.EventHandlingConfiguration;
import org.axonframework.config.SagaConfiguration;

import org.axonframework.eventhandling.async.AsynchronousEventProcessingStrategy;
import org.axonframework.eventhandling.async.SequentialPolicy;
import org.axonframework.eventsourcing.eventstore.jpa.JpaEventStorageEngine;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.upcasting.event.EventUpcasterChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Configuration
public class AxonConfiguration {
    @Bean
    public JpaEventStorageEngine eventStorageEngine(Serializer serializer,
                                                    DataSource dataSource,
                                                    EventUpcasterChain myUpcaster,
                                                    EntityManagerProvider entityManagerProvider,
                                                    TransactionManager transactionManager) throws SQLException {
        return new JpaEventStorageEngine(serializer,
                myUpcaster::upcast,
                dataSource,
                entityManagerProvider,
                transactionManager);
    }

    @Bean
    public EventUpcasterChain eventUpcasters(){
        return new EventUpcasterChain(
                new ContractSignedEvent_v1(),
                new ProductCreatedEvent_v1());
    }

    @Autowired
    public void configure(EventHandlingConfiguration config) {
    }

    @Bean
    Executor executor() {
        return Executors.newFixedThreadPool(3);
    }

    @Bean
    SagaConfiguration<ContractSignedSaga> contractSignedSagaConfiguration(Executor executor) {
        return SagaConfiguration.subscribingSagaManager(
            ContractSignedSaga.class,
            org.axonframework.config.Configuration::eventBus,
            (c) -> new AsynchronousEventProcessingStrategy(executor, new SequentialPolicy()));
    }

}
