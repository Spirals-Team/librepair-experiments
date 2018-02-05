package io.descoped.client.external.test;

import io.descoped.client.external.google.GeoLocation;
import io.descoped.client.external.google.GoogleMapsClient;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 21/11/2017
 */
public class GoogleMapsClientTest {

    private static final Logger log = LoggerFactory.getLogger(GoogleMapsClientTest.class);

    @Test
    @Ignore
    public void should_resolve_geo_location() throws Exception {
        GoogleMapsClient client = new GoogleMapsClient();
        GeoLocation geoLocation = client.getGeoLocation("Oslo");
        log.trace("{}", geoLocation.toString());
    }
}
