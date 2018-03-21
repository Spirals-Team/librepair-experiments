package com.objectia.twostep.net;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.objectia.Twostep;
import com.objectia.twostep.exception.AuthException;
import com.objectia.twostep.exception.TwostepException;
import com.objectia.twostep.model.Resource;
import com.objectia.twostep.model.Response;
import com.objectia.twostep.model.Token;
import com.objectia.twostep.model.TokenStore;

public class RestClient {

    public static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();

    public static <T> Resource<T> get(String path, Class<T> cls) throws TwostepException {
        return request("GET", path, null, cls);
    }

    public static <T> Resource<T> post(String path, Object params, Class<T> cls) throws TwostepException {
        return request("POST", path, params, cls);
    }

    public static <T> Resource<T> put(String path, Object params, Class<T> cls) throws TwostepException {
        return request("PUT", path, params, cls);
    }

    public static <T> Resource<T> delete(String path, Class<T> cls) throws TwostepException {
        return request("DELETE", path, null, cls);
    }

    private static <T> Resource<T> request(String method, String path, Object payload, Class<T> cls) throws TwostepException {
        String uri = Twostep.apiBase + path;

        Token token = getToken();
        if (token == null) {
            throw new AuthException("Unable to retrieve a valid access token");
        }

        Response resp = HttpClient.request(method, uri, token.getAccessToken(), payload);
    
        Type type = TypeToken.getParameterized(Resource.class, cls).getType();
        return GSON.fromJson(resp.getBody(), type); 
    }

    private static Token getToken() throws TwostepException {
        Token token = null;

        // Try get from token store
        TokenStore store = Twostep.getTokenStore();
        if (store != null) {
            token = store.load();
        }

        if (token != null && token.isValid()) {
            if (token.isExpired()) {
                // Use existing refresh_token to get a new token
                return refreshToken(token);
            }
            return token;
        }

        // Get a new token from server
        return newToken();
    }

    private static Token newToken() throws TwostepException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("client_id", Twostep.clientId);
        params.put("client_secret", Twostep.clientSecret);
        params.put("grant_type", "client_credentials");

        Response resp = HttpClient.post("https://api.twostep.io/auth/token", params);
        Token token = GSON.fromJson(resp.getBody(), Token.class);

        TokenStore store = Twostep.getTokenStore();
        if (store != null) {
            store.save(token);
        }

        return token;
    }

    private static Token refreshToken(Token oldToken) throws TwostepException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("client_id", Twostep.clientId);
        params.put("client_secret", Twostep.clientSecret);
        params.put("grant_type", "refresh_token");
        params.put("refresh_token", oldToken.getRefreshToken());

        Response resp = HttpClient.post("https://api.twostep.io/auth/token", params);
        Token token = GSON.fromJson(resp.getBody(), Token.class);

        TokenStore store = Twostep.getTokenStore();
        if (store != null) {
            store.save(token);
        }

        return token;
    }
}
