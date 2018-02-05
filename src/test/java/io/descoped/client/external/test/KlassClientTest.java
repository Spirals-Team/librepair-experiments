package io.descoped.client.external.test;

import io.descoped.client.external.klass.KlassClient;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KlassClientTest {

    private static final Logger log = LoggerFactory.getLogger(PostenPostalCodesClientTest.class);

    @Test
//    @Ignore
    public void shouldGetClassificationsFromKlass() {
        KlassClient client = new KlassClient();
        String json = client.getClassifications();

        log.trace("{}", json);
    }
}
