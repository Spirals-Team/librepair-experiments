package pl.hycom.ip2018.searchengine.googlesearch;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import pl.hycom.ip2018.searchengine.googlesearch.converter.GoogleResponseConverter;
import pl.hycom.ip2018.searchengine.googlesearch.service.DefaultGoogleSearch;
import pl.hycom.ip2018.searchengine.googlesearch.service.GoogleSearch;
import pl.hycom.ip2018.searchengine.googlesearch.service.JsonResponse;

/**
 * IoC Container processed it to generate beans
 */
@Configuration
public class GoogleSearchConfiguration {

    @Bean
    public GoogleSearch googleSearch() {
        return new DefaultGoogleSearch();
    }

    /**
     * Rest Template Bean for receiving data from API
     *
     * @return RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(new SimpleClientHttpRequestFactory());
    }

    /**
     * Json Response Bean for operations on response from API
     *
     * @return JsonResponse
     */
    @Bean
    public JsonResponse jsonResponse() {
        return new JsonResponse();
    }

    @Bean
    public GoogleResponseConverter googleResponseConverter() {
        return new GoogleResponseConverter();
    }
}
