package com.objectia.twostep.net;

import java.util.HashMap;
import java.util.Map;

import com.objectia.Twostep;
import com.objectia.twostep.exception.AuthException;
import com.objectia.twostep.exception.TwostepException;
import com.objectia.twostep.model.Response;
import com.objectia.twostep.model.Token;
import com.objectia.twostep.model.TokenStore;

public class RestClient {

    private static <T> T request(String method, String path, Object payload, Class<T> cls) throws TwostepException {
        String uri = Twostep.apiBase + path;

        Token token = getToken();
        if (token == null) {
            throw new AuthException("Unable to retrieve a valid access token");
        }

        return HttpClient.request(method, uri, token.getAccessToken(), payload, cls);
    }

    private static <T> Response<T> request2(String method, String path, Object payload, Class<T> cls) throws TwostepException {
        String uri = Twostep.apiBase + path;

        Token token = getToken();
        if (token == null) {
            throw new AuthException("Unable to retrieve a valid access token");
        }

        return HttpClient.request2(method, uri, token.getAccessToken(), payload, cls);
    }

    public static <T> T get(String path, Class<T> cls) throws TwostepException {
        return request("GET", path, null, cls);
    }

    public static <T> T post(String path, Object params, Class<T> cls) throws TwostepException {
        return request("POST", path, params, cls);
    }

    public static <T> Response<T> post2(String path, Object params, Class<T> cls) throws TwostepException {
        return request2("POST", path, params, cls);
    }


    public static <T> T put(String path, Object params, Class<T> cls) throws TwostepException {
        return request("PUT", path, params, cls);
    }

    public static <T> T delete(String path, Class<T> cls) throws TwostepException {
        return request("DELETE", path, null, cls);
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

        Token token = new Token();
        token = HttpClient.post("https://api.twostep.io/auth/token", params, token.getClass());

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

        Token token = new Token();
        token = HttpClient.post("https://api.twostep.io/auth/token", params, token.getClass());

        TokenStore store = Twostep.getTokenStore();
        if (store != null) {
            store.save(token);
        }

        return token;
    }
}
