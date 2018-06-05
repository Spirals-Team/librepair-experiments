package pl.hycom.ip2018.searchengine.wiki.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import pl.hycom.ip2018.searchengine.wiki.converter.WikiResponseConverter;
import pl.hycom.ip2018.searchengine.wiki.service.DefaultWikiSearch;
import pl.hycom.ip2018.searchengine.wiki.service.JsonResponse;
import pl.hycom.ip2018.searchengine.wiki.service.WikiSearch;


/**
 * Class intended for being processed by the Spring container
 * to generate bean definitions and service requests for those beans at runtime
 */
@Configuration
public class AppConfig {

    @Bean
    public WikiSearch googleSearch() {
        return new DefaultWikiSearch();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(new SimpleClientHttpRequestFactory());
    }

    @Bean
    public JsonResponse jsonResponse() {
        return new JsonResponse();
    }

    @Bean
    public WikiResponseConverter googleResponseConverter() {
        return new WikiResponseConverter();
    }

}
