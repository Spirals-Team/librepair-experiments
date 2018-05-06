package pl.hycom.ip2018.searchengine.aggregate.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.hycom.ip2018.searchengine.aggregate.service.DefaultAggregateSearch;

@Configuration
public class AppConfig {

    @Bean
    public DefaultAggregateSearch defaultAggregateSearch() {
        return new DefaultAggregateSearch(); }
}
