package pl.hycom.ip2018.searchengine.googlesearch.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.hycom.ip2018.searchengine.googlesearch.exception.GoogleSearchException;
import pl.hycom.ip2018.searchengine.googlesearch.model.GoogleSearchResponse;
import pl.hycom.ip2018.searchengine.googlesearch.service.GoogleSearch;
import pl.hycom.ip2018.searchengine.providercontract.ProviderResponse;

import java.util.ArrayList;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Controller returns json objects
 */
@RestController
public class GoogleSearchServiceController {

    @Autowired
    private GoogleSearch googleSearch;

    /**
     * Endpoint for aggregate service
     *
     * @param query phrase which we search
     * @return GoogleSearchResponse
     */
    @HystrixCommand(fallbackMethod = "getResponseFromGoogleFallBack",commandKey = "Google-Search-Service", groupKey = "Google-Response")
    @RequestMapping(value = "/res/{query}", method = GET)
    public ProviderResponse getResponseFromGoogle(@PathVariable String query) throws GoogleSearchException {
        return googleSearch.getResponse(query);
    }

    public ProviderResponse getResponseFromGoogleFallBack(String query) {
        return new ProviderResponse(new ArrayList<>());
    }
}
