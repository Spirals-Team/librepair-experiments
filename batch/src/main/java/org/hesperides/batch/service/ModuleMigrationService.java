package org.hesperides.batch.service;

import lombok.extern.java.Log;
import org.axonframework.commandhandling.model.ConcurrencyException;
import org.axonframework.eventsourcing.GenericDomainEventMessage;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.hesperides.batch.legacy.entities.LegacyEvent;
import org.hesperides.batch.legacy.events.modules.*;
import org.hesperides.batch.token.MongoTokenRepository;
import org.hesperides.batch.token.Token;
import org.hesperides.domain.templatecontainers.entities.TemplateContainer;
import org.hesperides.presentation.io.ModuleIO;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

//import org.hesperides.domain.templatecontainers.entities.TemplateContainer;

@Log
public class ModuleMigrationService extends AbstractMigrationService {

    static {
        AGGREGATE_TYPE = "ModuleAggregate";
        LEGACY_EVENTS_DICTIONARY = new HashMap<>();

        LEGACY_EVENTS_DICTIONARY.put("com.vsct.dt.hesperides.templating.modules.ModuleCreatedEvent", LegacyModuleCreatedEvent.class);
        LEGACY_EVENTS_DICTIONARY.put("com.vsct.dt.hesperides.templating.modules.ModuleWorkingCopyUpdatedEvent", LegacyModuleUpdatedEvent.class);
        LEGACY_EVENTS_DICTIONARY.put("com.vsct.dt.hesperides.templating.modules.ModuleDeletedEvent", LegacyModuleDeletedEvent.class);

        LEGACY_EVENTS_DICTIONARY.put("com.vsct.dt.hesperides.templating.modules.ModuleTemplateCreatedEvent", LegacyModuleTemplateCreatedEvent.class);
        LEGACY_EVENTS_DICTIONARY.put("com.vsct.dt.hesperides.templating.modules.ModuleTemplateUpdatedEvent", LegacyModuleTemplateUpdatedEvent.class);
        LEGACY_EVENTS_DICTIONARY.put("com.vsct.dt.hesperides.templating.modules.ModuleTemplateDeletedEvent", LegacyModuleTemplateDeletedEvent.class);

    }

    public ModuleMigrationService(RestTemplate restTemplate, ListOperations<String, LegacyEvent> listOperations, MongoTokenRepository mongoTokenRepository, EmbeddedEventStore eventBus, String legacyURI, String refonteURI) {
        super(restTemplate, listOperations, mongoTokenRepository, eventBus, legacyURI, refonteURI);
    }

    @Override
    protected void processOps() {

        if (checkModuleCreatedFirst(token.getKey(), listOperations.index(token.getKey(), 0))) {
            List<GenericDomainEventMessage<Object>> eventsList = convertToDomainEvent(listOperations.range(token.getKey(), 0, -1));
            try {
                log.info("Processing: " + token.getKey() + " (" + eventsList.size() + (eventsList.size() > 1 ? " events)" : " event)"));
                token.setLegacyEventCount(eventsList.size());
                eventBus.publish(eventsList);
                verify(token.getRefonteKey());

            } catch (ConcurrencyException e) {
                log.info("L'entité est déja insérée dans l'EventStore");
                verify(token.getRefonteKey());
            } catch (Exception e) {
                log.severe(e.getMessage() + " c'est pour voir quand ça pète");
                token.setStatus(Token.DELETED);
            } finally {
                mongoTokenRepository.save(token);
            }
        }
    }

    public Boolean checkModuleCreatedFirst(String key, LegacyEvent event) {
        Boolean ret = true;
        if (!"com.vsct.dt.hesperides.templating.modules.ModuleCreatedEvent".equals(event.getEventType())) {
            log.severe(key);
            ret = false;
            token.setStatus(Token.MODULE_ERRORED);
        }
        return ret;
    }

    @Override
    protected void verify(TemplateContainer.Key key) {
        final String legacyUri = LEGACY_URI + key.getURI();
        final String refonteUri = REFONTE_URI + key.getURI();

        try {
            ResponseEntity<ModuleIO> legacyResponse = legacyRestTemplate.getForEntity(legacyUri, ModuleIO.class);
            ResponseEntity<ModuleIO> refonteResponse = refonteRestTemplate.getForEntity(refonteUri, ModuleIO.class);
            if (refonteResponse.getBody().equals(legacyResponse.getBody())) {
                if(checkModel(legacyUri,refonteUri)) {
                    checkTemplatesList(legacyUri, refonteUri);
                }
            } else {
                token.setStatus(Token.KO);
            }
        } catch (HttpClientErrorException e) {
            if (e.getRawStatusCode() == 404) {
                token.setStatus(Token.DELETED);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    @Override
    protected void checkIfDeleted(Token token) {
        if ("com.vsct.dt.hesperides.templating.modules.ModuleTemplateDeletedEvent".equals(listOperations.index(token.getKey(), -1).getEventType()))
            this.token.setStatus(Token.DELETED);
        else
            log.info("le dernier event n'est pas une suppression");

    }
}
