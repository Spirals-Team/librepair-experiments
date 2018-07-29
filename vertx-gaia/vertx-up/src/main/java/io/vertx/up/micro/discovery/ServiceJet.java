package io.vertx.up.micro.discovery;

import io.vertx.circuitbreaker.CircuitBreaker;
import io.vertx.circuitbreaker.CircuitBreakerOptions;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.*;
import io.vertx.ext.web.RoutingContext;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.ServiceReference;
import io.vertx.servicediscovery.types.HttpEndpoint;
import io.vertx.up.atom.Envelop;
import io.vertx.up.exception.WebException;
import io.vertx.up.exception._404ServiceNotFoundException;
import io.vertx.up.exception._405MethodForbiddenException;
import io.vertx.up.log.Annal;
import io.vertx.up.micro.matcher.Arithmetic;
import io.vertx.up.micro.matcher.CommonArithmetic;
import io.vertx.up.rs.hunt.Answer;
import io.vertx.zero.eon.Strings;
import io.vertx.zero.marshal.Visitor;
import io.vertx.zero.micro.config.CircuitVisitor;
import io.zero.epic.Ut;
import io.zero.epic.fn.Fn;

import java.util.List;

public class ServiceJet {
    private static final Annal LOGGER = Annal.get(ServiceJet.class);
    private static final Visitor<CircuitBreakerOptions> VISITOR =
            Ut.singleton(CircuitVisitor.class);
    private static CircuitBreakerOptions OPTIONS;

    static {
        Fn.outUp(() -> {
            if (null == OPTIONS) {
                OPTIONS = VISITOR.visit();
            }
        }, LOGGER);
    }

    private final transient Arithmetic arithmetic = Ut.singleton(CommonArithmetic.class);
    private final transient HttpServerOptions options;
    private transient ServiceDiscovery discovery;
    private transient CircuitBreaker breaker;

    private ServiceJet(final HttpServerOptions options) {
        this.options = options;
    }

    public static ServiceJet create(final HttpServerOptions options) {
        return new ServiceJet(options);
    }

    public ServiceJet connect(final Vertx vertx) {
        this.discovery = ServiceDiscovery.create(vertx);
        final String name = this.options.getHost() + this.options.getPort();
        this.breaker = CircuitBreaker.create(name, vertx, OPTIONS);
        return this;
    }

    public Handler<RoutingContext> handle() {
        return context -> {
            // Run with circuit breaker
            this.breaker.execute(future -> this.getEndPoints().setHandler(res -> {
                if (res.succeeded()) {
                    final List<Record> records = res.result();
                    // Find the record hitted. ( Include Path variable such as /xx/yy/:zz/:xy )
                    final Record hitted = this.arithmetic.search(records, context);
                    // Complete actions.
                    if (null == hitted) {
                        // Service Not Found ( 404 )
                        this.reply404Error(context);
                    } else {
                        // Find record, dispatch request
                        final ServiceReference reference = this.discovery.getReference(hitted);
                        this.doRequest(context, reference);
                    }
                    future.complete();
                } else {
                    // Future failed
                    future.fail(res.cause());
                }
            }));
        };
    }

    private void doRequest(final RoutingContext context, final ServiceReference reference) {
        Fn.safeJvm(() -> {
            // HttpMethod:
            final HttpMethod method = context.request().method();
            final String targetUri = this.redirectUri(context);
            final HttpClient client = reference.getAs(HttpClient.class);
            // Final Response
            final HttpClientRequest request = client.request(method, targetUri,
                    response -> response.bodyHandler(handler -> {
                        // Client = 404 -> Transfer to 405
                        if (404 == response.statusCode()) {
                            this.reply405Error(context);
                        } else {
                            // Perfect Dispatching
                            this.replySuccess(context.response(), response, handler);
                        }
                    }));
            // Forward request -> Headers
            request.headers().setAll(context.request().headers());
            // Forward body
            if (null == context.getBody()) {
                request.end();
            } else {
                request.end(context.getBody());
            }
        }, LOGGER);
    }

    private void replySuccess(final HttpServerResponse response,
                              final HttpClientResponse clientResponse,
                              final Buffer buffer) {
        final String data = buffer.toString();
        // Copy header
        response.headers().setAll(clientResponse.headers());
        response.setStatusCode(clientResponse.statusCode());
        response.setStatusMessage(clientResponse.statusMessage());
        response.write(data);
        response.end();
    }

    private String redirectUri(final RoutingContext event) {
        final StringBuilder uri = new StringBuilder();
        uri.append(event.request().path());
        if (null != event.request().query()) {
            uri.append(String.valueOf(Strings.QUESTION)).append(event.request().query());
        }
        return uri.toString();
    }

    /**
     * Service Not Found ( 404 )
     *
     * @param context input router context
     */
    private void reply404Error(final RoutingContext context) {
        final HttpServerRequest request = context.request();
        final WebException exception = new _404ServiceNotFoundException(this.getClass(), request.uri(),
                request.method());
        Answer.reply(context, Envelop.failure(exception));
    }

    /**
     * Method not Allowed ( 405 )
     *
     * @param context input router context
     */
    private void reply405Error(final RoutingContext context) {
        final HttpServerRequest request = context.request();
        final WebException exception = new _405MethodForbiddenException(this.getClass(), request.method(),
                request.uri());
        Answer.reply(context, Envelop.failure(exception));
    }

    private Future<List<Record>> getEndPoints() {
        final Future<List<Record>> future = Future.future();
        this.discovery.getRecords(record -> record.getType().equals(HttpEndpoint.TYPE),
                future.completer());
        return future;
    }
}
