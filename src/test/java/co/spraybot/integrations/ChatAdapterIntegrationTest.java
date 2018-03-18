package co.spraybot.integrations;

import co.spraybot.demo.DemoChatAdapter;
import com.github.javafaker.Faker;
import io.vertx.core.Future;
import io.vertx.core.Verticle;
import io.vertx.core.http.*;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import org.junit.After;
import org.junit.Before;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class ChatAdapterIntegrationTest extends IntegrationTest {

    protected Faker faker;
    protected HttpClient client;
    protected HttpServer server;
    protected RequestOptions requestOptions;

    @Before
    @Override
    public void setUp(TestContext context) {
        super.setUp(context);
        client = vertx.createHttpClient();
        requestOptions = new RequestOptions();
        requestOptions.setSsl(false).setPort(8081).setHost("127.0.0.1");
        faker = new Faker();
    }

    @Override
    protected List<Verticle> getVerticles() {
        List<Verticle> list = new ArrayList<>();
        HttpServerOptions serverOptions = new HttpServerOptions();
        serverOptions.setSsl(false).setPort(8081).setHost("127.0.0.1");
        server = vertx.createHttpServer(serverOptions);

        list.add(new DemoChatAdapter(server, eventBus, fixedClock(), "testbot"));
        return list;
    }

    protected Future<WebSocket> readyForWebSocketRequest(String userName) {
        Future<WebSocket> future = Future.future();
        RequestOptions createUserOptions = new RequestOptions(requestOptions).setURI("/api/users");
        JsonObject requestData = new JsonObject().put("user", new JsonObject().put("name", userName));
        RequestOptions wsOptions = new RequestOptions(requestOptions).setURI("/chat?id=" + userName);
        makePostRequest(client.post(createUserOptions), requestData).setHandler(usersResult -> {
            client.websocket(wsOptions, future::complete);
        });

        return future;
    }

    protected Future<HttpClientResponse> makeGetRequest(RequestOptions requestOptions) {
        Future<HttpClientResponse> future = Future.future();

        client.getNow(requestOptions, future::complete);

        return future;
    }

    protected Future<HttpClientResponse> makePostRequest(HttpClientRequest request, JsonObject requestData) {
        Future<HttpClientResponse> future = Future.future();
        String encodedData = requestData.encode();
        String length = Integer.toString(encodedData.length());

        request.putHeader("content-length", length)
                .putHeader("content-type", "application/json")
                .handler(future::complete)
                .end(encodedData);

        return future;
    }

    protected void gatherFrameData(TestContext context, Async async, WebSocketFrame frame, Runnable doneHandler) {
        List<String> frames = context.get("frames");
        if (frames == null) {
            frames = new LinkedList<>();
        }
        frames.add(frame.textData());
        context.put("frames", frames);
        async.countDown();

        if (async.count() == 1) {
            vertx.runOnContext(_v -> doneHandler.run());
        }
    }

    @After
    @Override
    public void tearDown(TestContext context) {
        client.close();
        server.close(ar -> {
            super.tearDown(context);
        });
    }

}
