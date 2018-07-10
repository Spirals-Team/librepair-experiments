package org.hesperides.batch.service;

import lombok.extern.java.Log;
import org.axonframework.eventhandling.GenericEventMessage;
import org.axonframework.eventsourcing.GenericDomainEventMessage;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.messaging.MetaData;
import org.hesperides.batch.legacy.entities.LegacyEvent;
import org.hesperides.batch.legacy.events.LegacyInterface;
import org.hesperides.batch.legacy.events.technos.LegacyTechnoDeletedEvent;
import org.hesperides.batch.legacy.events.technos.LegacyTechnoTemplateCreatedEvent;
import org.hesperides.batch.legacy.events.technos.LegacyTechnoTemplateDeletedEvent;
import org.hesperides.batch.legacy.events.technos.LegacyTechnoTemplateUpdatedEvent;
import org.hesperides.batch.token.MongoTokenRepository;
import org.hesperides.batch.token.Token;
import org.hesperides.domain.security.User;
import org.hesperides.domain.technos.TechnoCreatedEvent;
import org.hesperides.domain.technos.entities.Techno;
import org.hesperides.domain.templatecontainers.entities.TemplateContainer;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

@Log
public class TechnoMigrationService extends AbstractMigrationService {

    static {
        AGGREGATE_TYPE = "TechnoAggregate";
        LEGACY_EVENTS_DICTIONARY = new HashMap<>();

        LEGACY_EVENTS_DICTIONARY.put("com.vsct.dt.hesperides.templating.packages.TemplateCreatedEvent", LegacyTechnoTemplateCreatedEvent.class);
        LEGACY_EVENTS_DICTIONARY.put("com.vsct.dt.hesperides.templating.packages.TemplateUpdatedEvent", LegacyTechnoTemplateUpdatedEvent.class);
        LEGACY_EVENTS_DICTIONARY.put("com.vsct.dt.hesperides.templating.packages.TemplatePackageDeletedEvent", LegacyTechnoDeletedEvent.class);
        LEGACY_EVENTS_DICTIONARY.put("com.vsct.dt.hesperides.templating.packages.TemplateDeletedEvent", LegacyTechnoTemplateDeletedEvent.class);


    }

    public TechnoMigrationService(RestTemplate restTemplate, ListOperations<String, LegacyEvent> listOperations, MongoTokenRepository mongoTokenRepository, EmbeddedEventStore eventBus, String legacyURI, String refonteURI) {
        super(restTemplate, listOperations, mongoTokenRepository, eventBus, legacyURI, refonteURI);
    }


    @Override
    protected List<GenericDomainEventMessage<Object>> convertToDomainEvent(List<LegacyEvent> events) {
        List<GenericDomainEventMessage<Object>> domainEventMessages = new ArrayList<>();
        //Ce booléen force la création d'un nouvel évenement TechnoCreatedEvent, si l'évennement précédent celui traité est de type TemplatePackageDeletedEvent
        AtomicBoolean shouldCreateTechno = new AtomicBoolean(true);
        token.setRefonteKey(convertToLegacyEvent(events.get(0)).getKey());
        events.forEach(event -> {
            try {
                LegacyInterface legacyInterface = convertToLegacyEvent(event);
                String aggregateId = legacyInterface.getKey().toString();
                Long timestamp = event.getTimestamp();
                Supplier<Instant> supplier = () -> Instant.ofEpochMilli(timestamp);
                User user = new User(event.getUser(), true, true);
                if (shouldCreateTechno.get()) {
                    domainEventMessages.add(new GenericDomainEventMessage<>(
                            AGGREGATE_TYPE,
                            aggregateId,
                            domainEventMessages.size(),
                            new GenericEventMessage<>(new TechnoCreatedEvent(new Techno(legacyInterface.getKey(), null), user)),
                            supplier));
                    shouldCreateTechno.set(false);
                }

                GenericEventMessage<Object> eventMessage = new GenericEventMessage<>(legacyInterface.toDomainEvent(user), MetaData.emptyInstance());
                if ("com.vsct.dt.hesperides.templating.packages.TemplatePackageDeletedEvent".equals(event.getEventType())) {
                    shouldCreateTechno.set(true);
                }
                domainEventMessages.add(new GenericDomainEventMessage<>(AGGREGATE_TYPE, aggregateId, domainEventMessages.size(), eventMessage, supplier));
            } catch (Exception ignored) {
            }
        });
        return domainEventMessages;
    }

    @Override
    protected void verify(TemplateContainer.Key key) {
        //getURI met lui même le prefix au pluriel, et rajoute un / à la fin
        final String legacyUri = LEGACY_URI + key.getURI();
        final String refonteUri = REFONTE_URI + key.getURI();

        if (checkModel(legacyUri, refonteUri)) {
            checkTemplatesList(legacyUri, refonteUri);
        }
    }

    @Override
    protected void checkIfDeleted(Token token) {
        String event = listOperations.index(token.getKey(), -1).getEventType();
        if ("com.vsct.dt.hesperides.templating.packages.TemplatePackageDeletedEvent".equals(event))
            this.token.setStatus(Token.DELETED);
        else
            log.info("le dernier event n'est pas une suppression");
    }
}
