package io.descoped.server.http;

import io.descoped.info.JsonBuilder;
import io.undertow.Undertow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class TestWebServer {

    private static final Logger log = LoggerFactory.getLogger(TestWebServer.class);
    private static final long SLEEP_TIMEOUT = 20000L;

    private final WebServerHandler handler;
    private Undertow server;
    private String host = "localhost";
    private int port = -1;

    public static void sleep() {
        sleep(null);
    }

    public static void sleep(Long timeOut) {
        try {
            Thread.sleep(Optional.of(timeOut).orElse(SLEEP_TIMEOUT));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public TestWebServer() {
        handler = new WebServerHandler();
    }

    public TestWebServer(String host, int port) {
        this.host = host;
        this.port = port;
        handler = new WebServerHandler();
    }

    public void start() throws Exception {
        port = (port == -1 ? new Random().nextInt(500) + 9000 : port);
        server = Undertow.builder()
                .addHttpListener(port, host)
                .setHandler(handler).build();
        server.start();
        JsonBuilder jsonBuilder = JsonBuilder.builder();
        jsonBuilder.key("endpoints");
        int n = 0;
        for(Map.Entry<String, Route> e : handler.getRoutes().entrySet()) {
            jsonBuilder.keyValue("context"+n, e.getKey());
            n++;
        }
        jsonBuilder.up();
        log.info("TestWebServer is listening on {}\n{}", baseURL(), jsonBuilder.build());
    }

    public void stop() throws Exception {
        server.stop();
        port = -1;
    }

    public String baseURL() {
        return baseURL(URI.create(""));
    }

    public String baseURL(URI uri) {
        try {
            URL url = new URL("http", host, port, uri.toString());
            return url.toExternalForm();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public URI baseURL(String uri) {
        return URI.create(baseURL(URI.create(uri)));
    }

    public void addRoute(String contextPath, Route route) {
        handler.addRoute(contextPath, route);
    }

    public Undertow getServer() {
        return server;
    }

    public int getPort() {
        return port;
    }

}
