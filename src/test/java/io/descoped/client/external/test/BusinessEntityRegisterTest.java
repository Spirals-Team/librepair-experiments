package io.descoped.client.external.test;

import io.descoped.client.external.enhreg.BusinessEntityRegisterClient;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 21/11/2017
 */
public class BusinessEntityRegisterTest {

    private static final Logger log = LoggerFactory.getLogger(BusinessEntityRegisterTest.class);

    @Test
    public void should_get_business_entity_register() throws Exception {
        BusinessEntityRegisterClient client = new BusinessEntityRegisterClient();
        client.fetch();
        client.unpack();
        client.build(null);
    }

}
