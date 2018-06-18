package com.tul.ta.util;

import com.tul.ta.client.ApiAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class HttpQueryUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpQueryUtils.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ApiAuthentication apiAuthenticationClient;

    public <T> T executeQuery(String uriString, Class<T> responseType) {
        return executeQuery(uriString, responseType, apiAuthenticationClient, restTemplate);
    }

    public <T> T executeQuery(String uriString, Class<T> responseType, ApiAuthentication apiAuthentication, RestTemplate restTemplate) {
        return executeQuery(uriString, responseType, apiAuthentication, restTemplate, 3);
    }

    public <T> T executeQuery(String uriString, Class<T> responseType, ApiAuthentication apiAuthentication, RestTemplate restTemplate, final int maxRetries) {
        int counter = 0;
        while (true) {
            HttpHeaders headers = new HttpHeaders();
            logger.info(apiAuthentication.getAuthHeader());
            headers.add("Authorization", apiAuthentication.getAuthHeader());
            headers.add("Accept", "application/json");

            HttpEntity request = new HttpEntity(headers);

            ResponseEntity<T> response = restTemplate.exchange(uriString,
                    HttpMethod.GET,
                    request,
                    responseType);
            int status = response.getStatusCodeValue();

            if (status > 299) {
                if (status == 401) {
                    logger.info("Authentication error. Token will be refreshed");
                    if (counter < maxRetries) {
                        counter++;
                        if (apiAuthentication.updateAccessToken()) {
                            logger.info("Token succesfully refreshed");
                            continue;
                        }
                    }
                }
                throw new HttpClientErrorException(response.getStatusCode());
            }
            return response.getBody();
        }
    }

    //TODO think about what is better to use, HttpEntity or RequestEntity?
    public <T> T invoke(URI url, Class<T> responseType) {
        RequestEntity<?> request = RequestEntity
                .get(url)
                .accept(MediaType.APPLICATION_JSON)
                .build();
        ResponseEntity<T> exchange = restTemplate.exchange(request, responseType);
        return exchange.getBody();
    }
}
