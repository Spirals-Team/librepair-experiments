package io.descoped.client.external.google;

import com.github.kevinsawicki.http.HttpRequest;
import io.descoped.client.api.config.Configuration;
import io.descoped.client.exception.APIClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 21/11/2017
 */
public class GoogleMapsClient {

    private static final Logger log = LoggerFactory.getLogger(GoogleMapsClient.class);

    public GeoLocation getGeoLocation(String address) {
        HttpRequest req = HttpRequest.get(String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=%s", address, Configuration.getGoogleApiKey()));
        if (req.code() == HTTP_OK) {
            try {
                String json = new String(req.bytes(), StandardCharsets.UTF_8.name());
                GeoLocation geoLocation = new GeoLocation(json);
                return geoLocation;
            } catch (UnsupportedEncodingException e) {
                log.error( req.message() );
                throw new APIClientException(e);
            }
        }
        return null;
    }

}
