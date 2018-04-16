package com.aliyuncs.fc.utils;

import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.*;

public class UriBuilderTest {

    @Test
    public void testBuildUri() throws URISyntaxException {
        UriBuilder uriBuilder = UriBuilder.fromUri(new URI("http://www.test.com"));

        assertEquals("http://www.test.com", uriBuilder.build());

        uriBuilder = UriBuilder.fromUri(new URI("www.test.com"));

        assertEquals("www.test.com", uriBuilder.build());

        uriBuilder = UriBuilder.fromUri(new URI("http://123.fc.shanghai.aliyuncs.com:90/version/proxy/service/function/action/action?p=1&v=2#123"));

        assertEquals("http://123.fc.shanghai.aliyuncs.com:90/version/proxy/service/function/action/action?p=1&v=2#123", uriBuilder.build());

        uriBuilder = UriBuilder.fromUri(new URI("http://123.fc.shanghai.aliyuncs.com:90/version/proxy/service/function/action/action?p=1&v=2#123"))
                .queryParam("testParam1", "testValue1")
                .queryParam("testParam2", "testValue2");

        assertEquals("http://123.fc.shanghai.aliyuncs.com:90/version/proxy/service/function/action/action?p=1&v=2&testParam1=testValue1&testParam2=testValue2#123", uriBuilder.build());


        uriBuilder = UriBuilder.fromUri(new URI("http://123.fc.shanghai.aliyuncs.com/version/proxy/service/function/action/action#123"))
                .queryParam("testParam1", "testValue1")
                .queryParam("testParam2", "testValue2");

        assertEquals("http://123.fc.shanghai.aliyuncs.com/version/proxy/service/function/action/action?testParam1=testValue1&testParam2=testValue2#123", uriBuilder.build());

        uriBuilder = UriBuilder.fromUri(new URI("http://123.fc.shanghai.aliyuncs.com/version/proxy/service/function/action/action"));

        assertEquals("http://123.fc.shanghai.aliyuncs.com/version/proxy/service/function/action/action", uriBuilder.build());
    }
}