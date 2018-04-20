package com.aliyuncs.fc.auth;

import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class FcSignatureComposerTest {

    @Test
    public void testComposeWithoutParameters() {
        Map<String, String> headers = new HashMap();
        headers.put("Content-MD5", "1bca714f406993b309bb87fabeb30a6b");
        headers.put("Content-Type", "application/json");
        headers.put("Date", "today");
        headers.put("X-FC-H6", "k6");
        headers.put("x-fc-h2", "k2");
        headers.put("X-Fc-h1", "k1");
        headers.put("x-fc-h4", "k4");
        headers.put("h3", "k3");
        headers.put("x-fc-h5", "k5");

        String composed = FcSignatureComposer.composeStringToSign("GET", "aa", headers, null);

        assertEquals("GET\n" +
                "1bca714f406993b309bb87fabeb30a6b\n" +
                "application/json\n" +
                "today\n" +
                "x-fc-h1:k1\n" +
                "x-fc-h2:k2\n" +
                "x-fc-h4:k4\n" +
                "x-fc-h5:k5\n" +
                "x-fc-h6:k6\n" +
                "aa", composed);

    }

    @Test
    public void testComposeWithParameters() {
        Map<String, String> headers = new HashMap();
        headers.put("Content-MD5", "1bca714f406993b309bb87fabeb30a6b");
        headers.put("Content-Type", "application/json");
        headers.put("Date", "today");
        headers.put("X-FC-H6", "k6");
        headers.put("x-fc-h2", "k2");
        headers.put("X-Fc-h1", "k1");
        headers.put("x-fc-h4", "k4");
        headers.put("h3", "k3");
        headers.put("x-fc-h5", "k5");

        Map<String, String> queries = new HashMap<String, String>();
        queries.put("h6", "k6");
        queries.put("h2", "k2");
        queries.put("h1", "k1");
        queries.put("h4", "k4");
        queries.put("h3", "k3");
        queries.put("h5", "k5");

        String composed = FcSignatureComposer.composeStringToSign("GET", "aa", headers, queries);

        assertEquals("GET\n" +
                "1bca714f406993b309bb87fabeb30a6b\n" +
                "application/json\n" +
                "today\n" +
                "x-fc-h1:k1\n" +
                "x-fc-h2:k2\n" +
                "x-fc-h4:k4\n" +
                "x-fc-h5:k5\n" +
                "x-fc-h6:k6\n" +
                "aa\n" +
                "h1=k1\n" +
                "h2=k2\n" +
                "h3=k3\n" +
                "h4=k4\n" +
                "h5=k5\n" +
                "h6=k6", composed);

    }
}