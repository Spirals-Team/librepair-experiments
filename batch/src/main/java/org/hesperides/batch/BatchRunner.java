package org.hesperides.batch;


import lombok.extern.java.Log;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.hesperides.batch.legacy.entities.LegacyEvent;
import org.hesperides.batch.service.AbstractMigrationService;
import org.hesperides.batch.service.ModuleMigrationService;
import org.hesperides.batch.service.TechnoMigrationService;
import org.hesperides.batch.token.MongoTokenRepository;
import org.hesperides.batch.token.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Log
@Component
@Profile("batch")
public class BatchRunner {

    @Value("#{'${hesperides.batch.migration.resources}'.split(',')}")
    List<String> resources;

    @Value("${legacy.uri}")
    String LEGACY_URI;
    @Value("${refonte.uri}")
    String REFONTE_URI;

    private final MongoTokenRepository mongoTokenRepository;
    private final MongoTemplate mongoTemplate;
    private EmbeddedEventStore eventBus;

    @Autowired
    public BatchRunner(MongoTokenRepository mongoTokenRepository, MongoTemplate mongoTemplate,EmbeddedEventStore eventBus) {
        this.mongoTokenRepository = mongoTokenRepository;
        this.mongoTemplate = mongoTemplate;
        this.eventBus = eventBus;
    }

    private ApplicationRunner titledRunner(String title, ApplicationRunner rr) {
        return args -> {
            log.info(title + " : ");
            rr.run(args);
        };
    }

    @Bean
    ApplicationRunner moduleImport(RedisTemplate<String, LegacyEvent> legacyTemplate, RestTemplate restTemplate) {
        return titledRunner("Convertion events Legacy", args -> {
            List<Token> technoList = new ArrayList<>();
            List<Token> moduleList = new ArrayList<>();
            if (mongoTemplate.collectionExists("token")) {
                log.info("Récupération de la liste de Tokens");
                technoList = mongoTokenRepository.findAllByTypeAndStatus("techno", Token.WIP);
//                templateList = mongoTokenRepository.findAllByTypeAndStatusNot("techno",Token.OK);
                log.info(technoList.size() + " technos à migrer");
//                moduleList = mongoTokenRepository.findAllByTypeAndStatusNot("module", Token.OK);
                moduleList = mongoTokenRepository.findAllByTypeAndStatus("module", Token.WIP);
                log.info(moduleList.size() + " modules à migrer");


            } else {
                log.info("Création de la liste de Tokens pour Technos");
                Set<String> legacySet = legacyTemplate.keys("template*");
                List<Token> finalTemplateList = technoList;
                legacySet.forEach(item -> finalTemplateList.add(new Token(item, "techno")));
                mongoTokenRepository.save(technoList);

                legacySet = legacyTemplate.keys("module*");
                List<Token> finalModuleList = moduleList;
                log.info("Création de la liste de Tokens pour modules");
                legacySet.forEach(item -> finalModuleList.add(new Token(item, "module")));
                mongoTokenRepository.save(moduleList);
            }
            log.info(REFONTE_URI + " " +LEGACY_URI + " batchrunner");
            if (this.resources.contains("technos")) {
                log.info("Migrate technos");
                AbstractMigrationService migrateTechno = new TechnoMigrationService(restTemplate,legacyTemplate.opsForList(),mongoTokenRepository, eventBus,LEGACY_URI,REFONTE_URI);
                migrateTechno.migrate(technoList);
            }
            if (this.resources.contains("modules")) {
                log.info("Migrate modules");
                AbstractMigrationService migrateModule = new ModuleMigrationService(restTemplate, legacyTemplate.opsForList(), mongoTokenRepository, eventBus,LEGACY_URI,REFONTE_URI );
                migrateModule.migrate(moduleList);
            }
//            if (this.resources.contains("platforms")) {
//                AbstractMigrationService migratePlatform = new PlatformMigrationService(eventStore, restTemplate, legacyTemplate.opsForList(), mongoTokenRepository);
//                migratePlatform.migrate(legacyTemplate, eventStore);
//            }
            log.info("finished");

        });
    }
}
