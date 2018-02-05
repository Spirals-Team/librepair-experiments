package io.descoped.client.api.test;

import io.descoped.client.external.posten.PostalCode;
import io.descoped.client.http.Client;
import io.descoped.client.http.Request;
import io.descoped.client.http.Response;
import io.descoped.client.http.ResponseBodyHandler;
import io.descoped.info.JsonBuilder;
import io.descoped.server.http.TestWebServer;
import io.undertow.util.Headers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Deque;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_OK;

public class PostenDataTest {

    private static final Logger log = LoggerFactory.getLogger(PostenDataTest.class);

    public static Integer count = 0;

    private TestWebServer server;

    public static void inc() {
        count = count + 1;
    }

    private String getPort() {
        return Integer.valueOf(server.getPort()).toString();
    }

    @Before
    public void setUp() throws Exception {
        server = new TestWebServer();
        server.addRoute("/transform", exchange -> {
            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");

            Deque<String> code = exchange.getQueryParameters().get("code");
            JsonBuilder builder = JsonBuilder.builder();
            Integer val = Integer.valueOf(code.getFirst()) + 10000;
            builder.keyValue("ssbCode", val.toString());
            builder.keyValue("serverPort", getPort());
            builder.up();

            exchange.getResponseSender().send(builder.build());
        });
        server.start();
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
        server = null;
    }

    private <R> Response<R> fetchPostenDatabase(ResponseBodyHandler<R> handler) {
        Request request = Request.builder(URI.create("https://www.bring.no/postnummerregister-ansi.txt")).GET().build();
        Response<R> response = Client.create().send​(request, handler);
        return response;
    }

//    @Test
    public void testPostenAsByteArray() throws Exception {
        // Response data processor
        ResponseBodyHandler<byte[]> handler = ResponseBodyHandler.asBytes();

        // Fetch
        Response<byte[]> response = fetchPostenDatabase(handler);

        // Log
        String postenDatabaseRawdata = new String(response.body().get(), "Cp1252");
        log.trace("Rawdata:\n{}", postenDatabaseRawdata);
    }

//    @Test
    public void testPostenWithCustomHandler() throws Exception {
        // Response data processor
        PostnrProcessor handler = PostnrProcessor.create(server);


        // Fetch
        Response<Map<String, PostalCode>> response = fetchPostenDatabase(handler);

        // Log
        Map<String, PostalCode> postalCodeMap = response.body().get();
        for (PostalCode entry : postalCodeMap.values()) {
            log.trace("{}\t{}\t{}\t{}\t{}\t{}",
                    entry.getCode(),
                    entry.getPlace(),
                    entry.getCommuneCode(),
                    entry.getCommuneName(),
                    entry.getCounty(),
                    (true ? entry.getCategory().toString() : entry.getCategory().getDescription())
            );
        }

        log.trace("Count: {}", count);
    }

//    @Test
    public void testHystrixCommand() throws Exception {
        PostnrHystrixCommand command = new PostnrHystrixCommand();
        Map<String, PostalCode> outcome = command.run();

        // Log
        for (PostalCode entry : outcome.values()) {
            log.trace("{}\t{}\t{}\t{}\t{}\t{}",
                    entry.getCode(),
                    entry.getPlace(),
                    entry.getCommuneCode(),
                    entry.getCommuneName(),
                    entry.getCounty(),
                    (true ? entry.getCategory().toString() : entry.getCategory().getDescription())
            );
        }

        log.trace("Count: {}", count);
    }


    @Test
    public void testGetHystrixCommand() throws Exception {
        final URI uri = URI.create("https://www.bring.no/postnummerregister-ansi.txt2");
        final ResponseBodyHandler<Map<String, PostalCode>> handler = new PostnrProcessor();

        GetHystrixCommand<Map<String, PostalCode>> command = new GetHystrixCommand<>(uri, handler);
        command.getBuilder().header("foo", "bar");
        Response<Map<String, PostalCode>> outcome = command.execute();

        log.trace("Outcome Body present?: {}", outcome.body().isPresent());

        if (outcome.statusCode() != HTTP_OK || outcome.isError()) {
            log.trace("Hystrix StatusCode: {}", outcome.statusCode());
            return;
        }

        // Log
        for (PostalCode entry : outcome.body().get().values()) {
            log.trace("{}\t{}\t{}\t{}\t{}\t{}",
                    entry.getCode(),
                    entry.getPlace(),
                    entry.getCommuneCode(),
                    entry.getCommuneName(),
                    entry.getCounty(),
                    (true ? entry.getCategory().toString() : entry.getCategory().getDescription())
            );
        }

        log.trace("Count: {}", count);
    }

}
