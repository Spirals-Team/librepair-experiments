package io.descoped.client.external.test;

import io.descoped.client.external.facebook.FacebookClient;
import io.descoped.client.external.google.GeoLocation;
import io.descoped.client.external.google.GoogleMapsClient;
import io.descoped.client.external.posten.PostalCode;
import io.descoped.client.external.posten.PostenPostalCodesClient;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 21/11/2017
 */
public class FlowExecutionTest {

    private static final Logger log = LoggerFactory.getLogger(FlowExecutionTest.class);

    @Test
    @Ignore
    public void should_execute_flow() throws Exception {
        PostenPostalCodesClient postenClient = new PostenPostalCodesClient();
        postenClient.fetch();
        PostalCode placeOslo = postenClient.getPostalCodeByCode("0001");
        log.trace("{}", placeOslo);

        GoogleMapsClient mapsClient = new GoogleMapsClient();
        GeoLocation geoLocation = mapsClient.getGeoLocation(placeOslo.getPlace());
        log.trace("{}", geoLocation);

        FacebookClient facebookClient = new FacebookClient();
        String json = facebookClient.getPageList(null,
                geoLocation.getCenterLatitude(),
                geoLocation.getCenterLongitude(),
                geoLocation.getNorthEastRadius().intValue());
        assertThat(json).isNotEmpty();

        log.trace("{}", json);
    }
}
