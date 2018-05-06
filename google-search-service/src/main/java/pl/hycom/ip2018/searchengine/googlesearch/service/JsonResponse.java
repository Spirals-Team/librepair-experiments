package pl.hycom.ip2018.searchengine.googlesearch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * Class for managing responses
 */
public class JsonResponse {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * This method give us response from external API through HTTP protocol,
     * From default, java mapped response to Map type, and this type we use
     * to next operations
     *
     * @param url          http address
     * @param responseType Type which we expected
     * @param <T>          generic Type
     * @return T generic Type which we expected
     */
    public <T> T invoke(URI url, Class<T> responseType) {
        RequestEntity<?> request = RequestEntity
                .get(url)
                .accept(MediaType.APPLICATION_JSON)
                .build();
        ResponseEntity<T> exchange = restTemplate.exchange(request, responseType);
        return exchange.getBody();
    }
}
