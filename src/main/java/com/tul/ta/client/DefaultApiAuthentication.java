package com.tul.ta.client;

import com.tul.ta.response.Authenticate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class DefaultApiAuthentication implements ApiAuthentication {
    private static Logger logger = LoggerFactory.getLogger(DefaultApiAuthentication.class);

    private final static String BASE_API_AUTHENTICATE_URL = "https://api.lufthansa.com/v1/oauth/token";

    private final String clientId = "e6y4jpcraaxpwk8aduavwh34";
    private final String clientSecret = "tkQc5rfR2N";
    private String accessToken = "";

    private String tokenType = "";
    private final int maxRetries = 3;
    private boolean isAccessTokenGranted = false;

    private final RestTemplate restTemplateAuthorizationClient;

    public DefaultApiAuthentication() {
        restTemplateAuthorizationClient = new RestTemplate();
        int retry = 0;
        boolean isAccessTokenGranted = false;
        while (!isAccessTokenGranted && retry < maxRetries) {
            isAccessTokenGranted = updateAccessToken();
            ++retry;
        }
        if (!isAccessTokenGranted) {
            logger.info("Authentication aborted after " + maxRetries + " retries.");
            throw new IllegalStateException();
        }
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public String getAuthHeader() {
        return "Bearer" + " " + accessToken;
    }

    public boolean updateAccessToken() throws HttpClientErrorException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<Authenticate> response;
        try {
            response = restTemplateAuthorizationClient.exchange(BASE_API_AUTHENTICATE_URL, HttpMethod.POST, request, Authenticate.class);
            Authenticate authenticate = response.getBody();

            logger.info("Access Token granted succesfully");
            logger.info(authenticate.access_token);
            logger.info(authenticate.expires_in);
            accessToken = authenticate.access_token;
            tokenType = authenticate.token_type;
        } catch (HttpClientErrorException e) {
            logger.info("Authorization error while asking for token");
        }
        return true; //TODO bool logic
    }
}
