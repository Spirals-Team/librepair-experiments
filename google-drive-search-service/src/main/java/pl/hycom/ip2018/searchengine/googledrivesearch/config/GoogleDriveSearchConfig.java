package pl.hycom.ip2018.searchengine.googledrivesearch.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import pl.hycom.ip2018.searchengine.googledrivesearch.service.*;

/**
 * Class intended for being processed by the Spring container
 * to generate bean definitions and service requests for those beans at runtime
 */
@Configuration
public class GoogleDriveSearchConfig {

    @Bean
    public GoogleDriveSearch googleDriveSearch() {
        return new DefaultGoogleDriveSearch();
    }

    @Bean
    public GoogleDriveAuth googleDriveAuth() {
        return new DefaultGoogleDriveAuth();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(new SimpleClientHttpRequestFactory());
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder().create();
    }

    @Bean
    public JsonResponse jsonResponse() {
        return new JsonResponse();
    }

    @Bean
    public ResponsePropertiesExtractor responsePropertiesExtractor() {
        return new ResponsePropertiesExtractor();
    }
}
