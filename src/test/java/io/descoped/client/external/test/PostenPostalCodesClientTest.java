package io.descoped.client.external.test;

import io.descoped.client.external.posten.PostalCode;
import io.descoped.client.external.posten.PostenPostalCodesClient;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 21/11/2017
 */
public class PostenPostalCodesClientTest {

    private static final Logger log = LoggerFactory.getLogger(PostenPostalCodesClientTest.class);

    @Test
    public void should_get_posten_postal_codes() throws Exception {
        PostenPostalCodesClient client = new PostenPostalCodesClient();

        Map<String, PostalCode> map = client.fetch();
        assertThat(map.size()).isGreaterThan(4000);

        Set<String> places = client.getUniquePlaces();
        assertThat(places.size()).isLessThan(2000);

        assertThat(client.getPostalCodeByCode("0001").getPlace()).isEqualToIgnoringCase("Oslo");

        map.forEach((k,v) -> {
            log.trace("{}", v.toString());
        });

        places.forEach((p) -> {
            log.trace("Place: {}", p);
        });

        log.trace("map-size: {} -- place-size: {}", map.size(), places.size());
    }
}
