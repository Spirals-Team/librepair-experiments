package io.vertx.up.rs.hunt;

import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.http.HttpStatusCode;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;
import io.vertx.up.annotations.SessionData;
import io.vertx.up.atom.Envelop;
import io.vertx.up.atom.agent.Event;
import io.zero.epic.Ut;

import javax.ws.rs.core.MediaType;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Response process to normalize the response request.
 * 1. Media definition
 * 2. Operation based on event, envelop, context
 */
public final class Answer {

    public static void reply(
            final RoutingContext context,
            final Envelop envelop) {
        // 1. Get response reference
        final HttpServerResponse response
                = context.response();
        // 2. Set response status
        final HttpStatusCode code = envelop.status();
        response.setStatusCode(code.code());
        response.setStatusMessage(code.message());
        response.putHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        // 4. Response process
        if (!response.ended()) {
            response.end(envelop.response());
        }
        response.close();
    }

    public static void reply(
            final RoutingContext context,
            final Envelop envelop,
            final Event event
    ) {
        // 1. Get response reference
        final HttpServerResponse response
                = context.response();

        // 2. Set response status
        final HttpStatusCode code = envelop.status();
        response.setStatusCode(code.code());
        response.setStatusMessage(code.message());
        // 3. Media processing
        Normalizer.out(response, envelop, event);
        // 4. Store Session
        storeSession(context, envelop.data(), event.getAction());
        // 5. Response process
        if (!response.ended()) {
            response.end(envelop.response());
        }
        response.close();
    }

    private static <T> void storeSession(
            final RoutingContext context,
            final T data,
            final Method method
    ) {
        final Session session = context.session();
        if (null != session && null != data && method.isAnnotationPresent(SessionData.class)) {
            final Annotation annotation = method.getAnnotation(SessionData.class);
            final String key = Ut.invoke(annotation, "value");
            final String field = Ut.invoke(annotation, "field");
            // Data Storage
            Object reference = data;
            if (Ut.isJObject(data) && Ut.notNil(field)) {
                final JsonObject target = (JsonObject) data;
                reference = target.getValue(field);
            }
            // Session Put / Include Session ID
            session.put(key, reference);
        }
    }
}
