package pl.hycom.ip2018.searchengine.wiki.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.net.URI;


/**
 * Strategy class that specifies a converter from HTTP to appropriate object we need
 */
public class JsonResponse {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Create a new Object of T type with given URI and Json MediaType
     * @param url the URL on which we make the request
     * @param responseType the type of the desired object
     * @param <T> the type of the desired object
     * @return  representation of Json in the form of a <T> type
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
