package io.descoped.client.external.facebook;

import com.github.kevinsawicki.http.HttpRequest;
import io.descoped.client.exception.APIClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 21/11/2017
 */
public class FacebookClient extends FacebookConnect {

    private static final Logger log = LoggerFactory.getLogger(FacebookClient.class);

    // https://developers.facebook.com/docs/graph-api/using-graph-api/v2.11#search
    public String getPageList(String query, Double lat, Double lng, Integer radius) throws UnsupportedEncodingException {
        String fbURI = "/search?%stype=place&center=%s,%s&distance=%s&fields=%s"; // query, lat/long, radius
        String q = (query != null ? "q=" + URLEncoder.encode(query, StandardCharsets.UTF_8.name()) + "&" : "");
        fbURI = String.format(fbURI, q, lat, lng, radius, "id,name,location,category_list,genre,hometown");
        log.trace("fbURI: {}", fbURI);

        HttpRequest req = GET(fbURI);
        if (req.code() == HTTP_OK) {
            return req.body();
        } else {
            throw new APIClientException(req.code(), req.body());
        }
    }

}
