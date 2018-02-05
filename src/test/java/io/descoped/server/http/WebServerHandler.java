package io.descoped.server.http;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebServerHandler implements HttpHandler {

    private Map<String, Route> routes = new ConcurrentHashMap<>();

    public void addRoute(String contextPath, Route route) {
        routes.put(contextPath, route);
    }

    public Map<String, Route> getRoutes() {
        return routes;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        String contextPath = exchange.getRequestURI();
        Route route = routes.get(contextPath);
        if (route == null) throw new RuntimeException("Path: " + contextPath + " not found!");
        route.dispatch(exchange);
    }
}
