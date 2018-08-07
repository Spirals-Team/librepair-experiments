package guru.bonacci.spectre.spectreshared.persistence;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@ComponentScan
@EnableElasticsearchRepositories
public class PersistenceConfig {
}
