package net.posesor;

import org.axonframework.commandhandling.AsynchronousCommandBus
import org.axonframework.commandhandling.CommandBus
import org.axonframework.commandhandling.SimpleCommandBus
import org.axonframework.eventhandling.saga.repository.SagaStore
import org.axonframework.eventhandling.saga.repository.inmemory.InMemorySagaStore
import org.axonframework.eventsourcing.eventstore.EventStorageEngine
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine
import org.axonframework.spring.config.EnableAxon
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@ComponentScan("net.posesor")
open class TestContext {


//    @Bean(destroyMethod = "shutdown")
//    open fun commandBus(): CommandBus {
//        return AsynchronousCommandBus()
//    }
//
//
//    @Bean
//    open fun sagaStore(): SagaStore<*> {
//        return InMemorySagaStore()
//    }
}