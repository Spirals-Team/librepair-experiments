package com.factern.api;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import com.factern.ApiClient;
import com.factern.Pair;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class OAuthUtils {
    private static final String PROP_ACCESS_TOKEN = "access_token";
    private static final String FACTERN_CLIENT_SECRET = "FACTERN_CLIENT_SECRET";

    private static final MediaType REQUEST_MEDIA_TYPE = MediaType.parse("application/json");
    private static final String AUTH_ENDPOINT = "https://factern-test.eu.auth0.com/oauth/token";
    private static final String client_secret = "client_secret";

    private static final Pair grant_type = new Pair("grant_type", "client_credentials");
    private static final Pair client_id = new Pair("client_id", "iGqdQKPPvrt52GiBRk6DqFwGHWTV5o9y");
    private static final Pair audience = new Pair("audience", "https://api.factern.com");

    private String accessToken;

    public void setSecurity(final ApiClient client) throws IOException {
        client.setAccessToken(getOrCreateAccessToken());
    }

    public String createAccessToken() throws IOException {
        return requestAccessToken();
    }

    public String getOrCreateAccessToken() throws IOException {
        if (accessToken == null) {
            accessToken = createAccessToken();
        }
        return accessToken;
    }

    private String requestAccessToken() throws IOException {
        final OkHttpClient client = new OkHttpClient();

        final JsonObject jo = new JsonObject();
        jo.addProperty(grant_type.getName(), grant_type.getValue());
        jo.addProperty(client_id.getName(), client_id.getValue());
        jo.addProperty(client_secret, getClientSecret());
        jo.addProperty(audience.getName(), audience.getValue());

        final RequestBody body = RequestBody.create(REQUEST_MEDIA_TYPE, jo.toString());
        final Request request = new Request.Builder()
            .url(new URL(AUTH_ENDPOINT))
            .post(body)
            .build();

        final Response response = client.newCall(request).execute();

        final JsonElement json = new JsonParser().parse(response.body().string());

        return json.getAsJsonObject().getAsJsonPrimitive(PROP_ACCESS_TOKEN).getAsString();
    }

    private String getClientSecret() {
        final String secret = System.getProperty(FACTERN_CLIENT_SECRET, System.getenv(FACTERN_CLIENT_SECRET));
        Objects.requireNonNull(secret);
        return secret;
    }

}
