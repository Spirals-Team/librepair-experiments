package org.hesperides.batch.service;

import com.google.gson.Gson;
import lombok.extern.java.Log;
import org.axonframework.commandhandling.model.ConcurrencyException;
import org.axonframework.eventhandling.GenericEventMessage;
import org.axonframework.eventsourcing.GenericDomainEventMessage;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.messaging.MetaData;
import org.hesperides.batch.legacy.entities.LegacyEvent;
import org.hesperides.batch.legacy.events.LegacyInterface;
import org.hesperides.batch.token.MongoTokenRepository;
import org.hesperides.batch.token.Token;
import org.hesperides.domain.security.User;
import org.hesperides.domain.templatecontainers.entities.TemplateContainer;
import org.hesperides.presentation.io.templatecontainers.ModelOutput;
import org.hesperides.presentation.io.templatecontainers.PartialTemplateIO;
import org.hesperides.presentation.io.templatecontainers.TemplateIO;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.reflect.Type;
import java.time.Instant;
import java.util.*;
import java.util.function.Supplier;

@Log
public abstract class AbstractMigrationService {
    static String AGGREGATE_TYPE;
    static Map<String, Type> LEGACY_EVENTS_DICTIONARY;
    static String LEGACY_URI;
    static String REFONTE_URI;

    ListOperations<String, LegacyEvent> listOperations;
    RestTemplate legacyRestTemplate;
    RestTemplate refonteRestTemplate;
    MongoTokenRepository mongoTokenRepository;
    EmbeddedEventStore eventBus;
    Token token;

    AbstractMigrationService(RestTemplate restTemplate,
                             ListOperations<String, LegacyEvent> listOperations, MongoTokenRepository mongoTokenRepository, EmbeddedEventStore eventBus, String legacyURI, String refonteURI) {
        this.legacyRestTemplate = restTemplate;
        this.refonteRestTemplate = restTemplate;
        this.listOperations = listOperations;
        this.mongoTokenRepository = mongoTokenRepository;
        this.eventBus = eventBus;
        this.LEGACY_URI = legacyURI;
        this.REFONTE_URI = refonteURI;
        refonteRestTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("adrien_auffredou", "P@wook07"));
        legacyRestTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("adrien_auffredou", "P@wook07"));
    }

    public void migrate(List<Token> tokenList) {
        tokenList.forEach(token -> {
            this.token = token;
            processOps();

        });
    }

    protected void processOps() {
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

    protected List<GenericDomainEventMessage<Object>> convertToDomainEvent(List<LegacyEvent> events) {
        List<GenericDomainEventMessage<Object>> domainEventMessages = new ArrayList<>();
        this.token.setRefonteKey(convertToLegacyEvent(events.get(0)).getKey());
        events.forEach(event -> {
            try {
                LegacyInterface legacyInterface = convertToLegacyEvent(event);
                Long timestamp = event.getTimestamp();
                Supplier<Instant> supplier = () -> Instant.ofEpochMilli(timestamp);
                User user = new User(event.getUser(), true, true);
                GenericEventMessage<Object> eventMessage = new GenericEventMessage<>(legacyInterface.toDomainEvent(user), MetaData.emptyInstance());
                String aggregateId = legacyInterface.getKey().toString();
                domainEventMessages.add(new GenericDomainEventMessage<>(AGGREGATE_TYPE, aggregateId, domainEventMessages.size(), eventMessage, supplier));
            } catch (Exception e) {
                log.severe(e.getLocalizedMessage());
            }
        });
        token.setRefonteEventCount(domainEventMessages.size());
        return domainEventMessages;
    }

    LegacyInterface convertToLegacyEvent(LegacyEvent event) {
        Gson gson = new Gson();
        String eventType = event.getEventType();
        LegacyInterface result = null;
        if (LEGACY_EVENTS_DICTIONARY.containsKey(event.getEventType())) {
            result = gson.fromJson(event.getData(), LEGACY_EVENTS_DICTIONARY.get(eventType));
        } else {
            log.info("Event conversion " + event.toString() + " not yet implemented");
            throw new NotImplementedException();
        }
        return result;
    }

    abstract void verify(TemplateContainer.Key key);

    void checkTemplatesList(String legacyUri, String refonteUri) {
        String tempLegacyUri = legacyUri + "/templates/";
        String tempRefonteUri = refonteUri + "/templates/";

        try {
            ResponseEntity<PartialTemplateIO[]> legacyResponse = legacyRestTemplate.getForEntity(tempLegacyUri, PartialTemplateIO[].class);
            ResponseEntity<PartialTemplateIO[]> refonteResponse = legacyRestTemplate.getForEntity(tempRefonteUri, PartialTemplateIO[].class);
            PartialTemplateIO[] legacyArray = legacyResponse.getBody();
            PartialTemplateIO[] refonteArray = refonteResponse.getBody();

            Arrays.sort(legacyArray);
            Arrays.sort(refonteArray);


            if (Arrays.equals(legacyArray, refonteArray)) {
                Arrays.stream(legacyArray).forEach(template -> checkTemplate(template.getName(), legacyUri, refonteUri));
            } else {
                log.severe("Liste des templates différente : " + tempLegacyUri);
                this.token.setStatus(Token.KO);

            }
        }
        //Une gestion d'erreur plus fine aurait été appréciable, mais le Legacy renvoie une HttpServerErrorException, HttpClientErrorException
        catch (Exception e) {
            checkIfDeleted(token);
        }


    }

    protected abstract void checkIfDeleted(Token token);

    private void checkTemplate(String templateName, String legacyUri, String refonteUri) {
        if (token.getStatus() == Token.KO)
            return;


        String tempLegacyUri = legacyUri + "/templates/" + templateName;
        String tempRefonteUri = refonteUri + "/templates/" + templateName;

        ResponseEntity<TemplateIO> legacyResponse = legacyRestTemplate.getForEntity(tempLegacyUri, TemplateIO.class);
        ResponseEntity<TemplateIO> refonteResponse = refonteRestTemplate.getForEntity(tempRefonteUri, TemplateIO.class);

        TemplateIO legacyTemplate = legacyResponse.getBody();
        TemplateIO refonteTemplate = refonteResponse.getBody();

        if (legacyTemplate.getRights() == null) {
            TemplateIO.RightsIO rights = new TemplateIO.RightsIO(new TemplateIO.FileRightsIO(null, null, null)
                    , new TemplateIO.FileRightsIO(null, null, null)
                    , new TemplateIO.FileRightsIO(null, null, null));

            legacyTemplate = new TemplateIO(legacyTemplate.getName(),
                    legacyTemplate.getNamespace(),
                    legacyTemplate.getFilename(),
                    legacyTemplate.getLocation(),
                    legacyTemplate.getContent(),
                    rights,
                    legacyTemplate.getVersionId());
        }

        if (!refonteTemplate.equals(legacyTemplate)) {
            log.severe("Template " + templateName + " différent : " + tempLegacyUri);
            this.token.setStatus(Token.KO);
        } else {
            this.token.setStatus(Token.OK);
        }
    }

    boolean checkModel(String legacyUri, String refonteUri) {
        String tempLegacyUri = legacyUri + "/model";
        String tempRefonteUri = refonteUri + "/model";

        Boolean result = false;

        try {
            ResponseEntity<ModelOutput> legacyResponse = legacyRestTemplate.getForEntity(tempLegacyUri, ModelOutput.class);
            ResponseEntity<ModelOutput> refonteResponse = refonteRestTemplate.getForEntity(tempRefonteUri, ModelOutput.class);

            ModelOutput legacyModel = legacyResponse.getBody();
            ModelOutput refonteModel = refonteResponse.getBody();
            Collections.sort(legacyModel.getProperties());
            Collections.sort(legacyModel.getIterableProperties());
            Collections.sort(refonteModel.getProperties());
            Collections.sort(refonteModel.getIterableProperties());

            if (refonteModel.equals(legacyModel)) {
                result = true;
                this.token.setStatus(Token.OK);
            } else if (refonteModel.getProperties().size() != legacyModel.getProperties().size()) {
                this.token.setStatus(Token.PROPERTIES_DIFFERENCE);
            } else if (refonteModel.getIterableProperties().size() != legacyModel.getIterableProperties().size()) {
                this.token.setStatus(Token.ITERABLE_PROPERTIES_DIFFERENCE);
            } else {
                this.token.setStatus(Token.KO);
            }
        } catch (Exception e) {
            checkIfDeleted(token);
        } finally {
            return result;
        }
    }

}
