package io.descoped.client.external.klass;

import io.descoped.client.http.Client;
import io.descoped.client.http.Request;
import io.descoped.client.http.Response;
import io.descoped.client.http.ResponseBodyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

public class KlassClient {

    private static final Logger log = LoggerFactory.getLogger(KlassClient.class);
    private static String KLASS_URL = "http://data.ssb.no/api/klass/v1/classifications/";

    // todo: look at PostnrProcessor to make a custom KlassProcessor

    public String getClassifications() {
        Request req = Request.builder(URI.create(KLASS_URL)).GET().build();
        ResponseBodyHandler<String> handler = ResponseBodyHandler.asString();
        Response<String> response = Client.create().send​(req, handler);
        return response.body().get();
    }
}
