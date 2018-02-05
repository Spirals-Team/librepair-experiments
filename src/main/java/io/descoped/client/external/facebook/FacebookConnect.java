package io.descoped.client.external.facebook;

import com.github.kevinsawicki.http.HttpRequest;
import io.descoped.client.api.config.Configuration;

import java.util.Objects;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 22/11/2017
 */
public class FacebookConnect {

    private static final String API_VERSION = "v2.11";

    private static String checkURI(String uri) {
        if (uri != null && uri.startsWith("/")) {
            return uri.substring(1);
        }
        return uri;
    }

    public static HttpRequest GET(String uri, String[]... headers) {
        Objects.requireNonNull(uri);
        HttpRequest req = HttpRequest.get(String.format("https://graph.facebook.com/%s/%s", API_VERSION, checkURI(uri)));
//        log.trace("URL => {}", req.url().toString());
        req.header("Authorization", "OAuth " + Configuration.getDeveloperAccessToken());
        for(String[] header : headers) {
            req.header(header[0], header[1]);
        }
        return req;
    }

    public static HttpRequest POST(String uri, String[]... headers) {
        HttpRequest req = HttpRequest.post(String.format("https://graph.facebook.com/%s/%s", API_VERSION, checkURI(uri)));
        req.header("Authorization", "OAuth " + Configuration.getDeveloperAccessToken());
        for(String[] header : headers) {
            req.header(header[0], header[1]);
        }
        return req;
    }

    public static HttpRequest PUT(String uri, String[]... headers) {
        HttpRequest req = HttpRequest.put(String.format("https://graph.facebook.com/%s/%s", API_VERSION, checkURI(uri)));
        req.header("Authorization", "OAuth " + Configuration.getDeveloperAccessToken());
        for(String[] header : headers) {
            req.header(header[0], header[1]);
        }
        return req;
    }

    public static HttpRequest DELETE(String uri, String[]... headers) {
        HttpRequest req = HttpRequest.delete(String.format("https://graph.facebook.com/%s/%s", API_VERSION, checkURI(uri)));
        req.header("Authorization", "OAuth " + Configuration.getDeveloperAccessToken());
        for(String[] header : headers) {
            req.header(header[0], header[1]);
        }
        return req;
    }


}
