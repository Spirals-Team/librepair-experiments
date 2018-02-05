package io.descoped.server;

import com.github.kevinsawicki.http.HttpRequest;
import io.descoped.server.http.LoopbackRoute;
import io.descoped.server.http.TestWebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URI;

public class WebServerTest {

    private static final Logger log = LoggerFactory.getLogger(WebServerTest.class);

    private TestWebServer server;

    @Before
    public void setUp() throws Exception {
        server = new TestWebServer();
        server.addRoute("/dump", new LoopbackRoute());
        server.start();
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void should_get_request() throws UnsupportedEncodingException {
//        TestWebServer.sleep(20000L);
        HttpRequest req = HttpRequest.get(server.baseURL(URI.create("/dump?foo=bar")));
        req.header("foo", "bar");
        req.send("foo=bar");
        log.trace("{} [{}]: {}Received-Content-Length: {}", req.url(), req.code(), req.body(), req.contentLength());
    }

}
