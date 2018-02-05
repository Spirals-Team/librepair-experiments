package io.descoped.server.http;

import io.descoped.info.JsonBuilder;
import io.undertow.io.Receiver;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoopbackRoute implements Route {

    private static final Logger log = LoggerFactory.getLogger(LoopbackRoute.class);

    @Override
    public void dispatch(HttpServerExchange exchange) {
        JsonBuilder jsonBuilder = JsonBuilder.builder();

        {
            jsonBuilder.key("request-headers");
            exchange.getRequestHeaders().getHeaderNames().forEach(h -> {
                exchange.getRequestHeaders().eachValue(h).forEach(v -> {
                    jsonBuilder.keyValue(h.toString(), v);
                });
            });
            jsonBuilder.up();
        }

        {
            jsonBuilder.key("request-info");
            jsonBuilder.keyValue("uri", exchange.getRequestURI());
            jsonBuilder.keyValue("method", exchange.getRequestMethod().toString());
            jsonBuilder.keyValue("statusCode", String.valueOf(exchange.getStatusCode()));
            jsonBuilder.keyValue("isSecure", Boolean.valueOf(exchange.isSecure()).toString());
            jsonBuilder.keyValue("sourceAddress", exchange.getSourceAddress().toString());
            jsonBuilder.keyValue("destinationAddress", exchange.getDestinationAddress().toString());
            jsonBuilder.key("cookies");
            exchange.getRequestCookies().forEach((k,v) -> {
                jsonBuilder.keyValue(k,v.getValue());
            });
            jsonBuilder.up();
            jsonBuilder.key("path-parameters");
            exchange.getPathParameters().entrySet().forEach((e) -> {
                jsonBuilder.keyValue(e.getKey(), e.getValue().element());
            });
            jsonBuilder.up();
            jsonBuilder.keyValue("queryString", exchange.getQueryString());
            jsonBuilder.key("query-parameters");
            exchange.getQueryParameters().entrySet().forEach((e) -> {
                jsonBuilder.keyValue(e.getKey(), e.getValue().element());
            });
            jsonBuilder.up();
            jsonBuilder.up();
        }

        {
            jsonBuilder.key("request-body");
            jsonBuilder.keyValue("contentLength", String.valueOf(exchange.getRequestContentLength()));
            exchange.getRequestReceiver().receiveFullBytes(new Receiver.FullBytesCallback() {
                @Override
                public void handle(HttpServerExchange httpServerExchange, byte[] bytes) {
                    jsonBuilder.keyValue("payload", new String(bytes));
                }
            });
            jsonBuilder.up();
        }

        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");

        {
            jsonBuilder.key("response-headers");
            exchange.getResponseHeaders().getHeaderNames().forEach(h -> {
                exchange.getResponseHeaders().eachValue(h).forEach(v -> {
                    jsonBuilder.keyValue(h.toString(), v);
                });
            });
            jsonBuilder.up();
        }

        {
            jsonBuilder.key("response-info");
            jsonBuilder.key("cookies");
            exchange.getResponseCookies().forEach((k,v) -> {
                jsonBuilder.keyValue(k,v.getValue());
            });
            jsonBuilder.up();
        }
        jsonBuilder.up();

        String payload = jsonBuilder.build() + "\n";

        exchange.getResponseSender().send(payload);
        exchange.getResponseSender().close();
    }
}
