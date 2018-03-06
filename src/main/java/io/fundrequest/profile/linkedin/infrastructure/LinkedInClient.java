package io.fundrequest.profile.linkedin.infrastructure;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.fundrequest.profile.linkedin.dto.LinkedInShare;
import io.fundrequest.profile.linkedin.dto.LinkedInUpdateResult;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LinkedInClient {

    private final ObjectMapper objectMapper;

    public LinkedInClient() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public LinkedInUpdateResult postNetworkUpdate(String accessToken, LinkedInShare content) {
        String url = "https://api.linkedin.com/v1/people/~/shares?format=json";
        HttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Authorization", "Bearer " + accessToken);
        httpPost.addHeader("Content-Type", "application/json");
        String json = "{\n" +
                "  \"comment\": \"Check out developer.linkedin.com!\",\n" +
                "  \"content\": {\n" +
                "    \"title\": \"LinkedIn Developers Resources\",\n" +
                "    \"description\": \"Leverage LinkedIn's APIs to maximize engagement\",\n" +
                "    \"submitted-url\": \"https://developer.linkedin.com\",  \n" +
                "    \"submitted-image-url\": \"https://example.com/logo.png\"\n" +
                "  },\n" +
                "  \"visibility\": {\n" +
                "    \"code\": \"anyone\"\n" +
                "  }  \n" +
                "}";
        try {
            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);
            HttpResponse response = httpclient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() != 201) {
                throw new RuntimeException("An error occurred while posting a LinkedIn network update");
            }
            return objectMapper.readValue(EntityUtils.toString(response.getEntity()), LinkedInUpdateResult.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
