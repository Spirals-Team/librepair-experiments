package org.hesperides.batch.service;

import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.hesperides.batch.legacy.entities.LegacyEvent;
import org.hesperides.batch.token.MongoTokenRepository;
import org.hesperides.batch.token.Token;
import org.hesperides.domain.templatecontainers.entities.TemplateContainer;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

public class PlatformMigrationService extends AbstractMigrationService {
    static {
        //TODO : être sur de cet aggregat
        AGGREGATE_TYPE = "PlatformAggregate";
        LEGACY_EVENTS_DICTIONARY = new HashMap<>();


    }

    public PlatformMigrationService(RestTemplate restTemplate, ListOperations<String, LegacyEvent> listOperations, MongoTokenRepository mongoTokenRepository, EmbeddedEventStore eventBus, String legacyURI, String refonteURI) {
        super(restTemplate, listOperations, mongoTokenRepository, eventBus, legacyURI, refonteURI);
    }


    @Override
    void verify(TemplateContainer.Key key) {

    }

    @Override
    protected void checkIfDeleted(Token token) {

    }
}
