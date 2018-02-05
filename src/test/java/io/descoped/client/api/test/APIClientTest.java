package io.descoped.client.api.test;

import io.descoped.client.api.builder.APIClient;
import io.descoped.client.api.test.impl.HttpBinOperation;
import io.descoped.client.api.test.impl.HttpBinOutcome;
import io.descoped.client.http.*;
import io.descoped.server.http.Route;
import io.descoped.server.http.TestWebServer;
import io.undertow.util.Headers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.stream.Stream;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 21/11/2017
 */
public class APIClientTest {

    private static final Logger log = LoggerFactory.getLogger(APIClientTest.class);

    /*
        todo - tracking key data for request/response -headers, -content

        1)
     */

    private TestWebServer server;

    @Before
    public void setUp() throws Exception {
        server = new TestWebServer();
        server.addRoute("/dump", Route.createLoopback());
        server.addRoute("/get", exchange -> {
            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
            exchange.getResponseSender().send("/FooBar");
        });
        server.addRoute("/post", exchange -> {
            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
            exchange.getResponseSender().send("/BarBaz");
        });
        server.start();
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }


//    @Test
    public void testBuilder() throws Exception {
        APIClient.builder()
                .worker("postHttpBin")
                    .operation(HttpBinOperation.class)
                    .outcome(HttpBinOutcome.class)
                    .done()
                .worker("getHttpBin")
                    .operation(HttpBinOperation.class)
                    .outcome(HttpBinOutcome.class)
                    .done()
                .execute()
        ;
    }

//    @Test
    public void testBuilder2() throws Exception {
        APIClient.builder()
                .worker("postHttpBin")
                    .consume(server.baseURL(URI.create("/GET/$1")), Stream.of("$handler"))
                    .consume((j,p) -> j) // new ConsumerJob("http://httpbin.org/get/$1", "$param")
                    .produce()
                    .header()
                    .options()
                    .delete()
                    .done()
                .execute()
        ;
    }

//    @Test
    public void testHttpConsumerGet() throws Exception {
        Client client = Client.create();
        Request consume = Request.builder(server.baseURL("/get?foo=bar")).GET().build();
        ResponseBodyHandler<byte[]> handler = ResponseBodyHandler.asBytes();
        Exchange<byte[]> exchange = Exchange.createHttpRequestExchange(consume, handler);
        Response<byte[]> response = exchange.response();
        byte[] body = response.body().get();
        log.trace("GET body [{}]: {}", response.statusCode(), new String(body));
    }

    @Test
    public void testHttpConsumer() throws Exception {
        Client client = Client.create();
        Request consume = Request.builder(server.baseURL("/dump?foo=bar")).POST(RequestBodyProcessor.fromString("foo=bar")).header("foo", "bar").build();
        ResponseBodyHandler<byte[]> handler = ResponseBodyHandler.asBytes();
        Exchange<byte[]> exchange = Exchange.createHttpRequestExchange(consume, handler);
        Response<byte[]> response = exchange.response();
        byte[] body = response.body().get();
        log.trace("POST body [{}]: {}", response.statusCode(), new String(body));

        /*
            Exchange.request(BodyHandler)
         */

//        HttpRequest req = HttpRequest.get("http://httpbin.org/get");
//        req.headers();

    }
}
