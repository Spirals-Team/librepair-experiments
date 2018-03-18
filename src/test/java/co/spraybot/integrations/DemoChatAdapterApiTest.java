package co.spraybot.integrations;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.http.RequestOptions;
import io.vertx.core.http.WebSocket;
import io.vertx.core.http.WebSocketFrame;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;

import java.util.*;

public class DemoChatAdapterApiTest extends ChatAdapterIntegrationTest {

    @Test
    public void webSocketWithInvalidPathRejects(TestContext context) {
        Async async = context.async();
        requestOptions.setURI("/not-chat");

        client.websocket(requestOptions, webSocket -> {}, throwable -> {
            context.assertEquals("Websocket connection attempt returned HTTP status code 502", throwable.getMessage());
            async.complete();
        });
    }

    @Test
    public void webSocketWithInvalidUserId(TestContext context) {
        Async async = context.async();
        requestOptions.setURI("/chat?id=not-there");

        client.websocket(requestOptions, webSocket -> {}, throwable -> {
            context.assertEquals("Websocket connection attempt returned HTTP status code 502", throwable.getMessage());
            async.complete();
        });
    }

    @Test
    public void webSocketWithValidPathAndUser(TestContext context) {
        Async async = context.async();
        String userName = faker.name().firstName();
        RequestOptions createUserOptions = new RequestOptions(requestOptions).setURI("/api/users");
        RequestOptions wsOptions = new RequestOptions(requestOptions).setURI("/chat?id=" + userName);

        JsonObject requestData = new JsonObject().put("user", new JsonObject().put("name", userName));

        Future<HttpClientResponse> csprayFuture = makePostRequest(client.post(createUserOptions), requestData);

        csprayFuture.setHandler(ar -> {
            client.websocket(wsOptions, webSocket -> {
                webSocket.frameHandler(webSocketFrame -> {
                    context.assertEquals("Connection established!", webSocketFrame.textData());
                    async.complete();
                });
            });
        });
    }

    private void assertBufferMatchesJsonObject(TestContext context, Buffer buffer, JsonObject expected) {
        String responseData = buffer.getString(0, buffer.length());
        JsonObject actual = new JsonObject(responseData);
        context.assertEquals(expected, actual);
    }

    @Test
    public void webSocketChatBotJoinChat(TestContext context) {
        Async async = context.async(3);
        String userName = faker.name().firstName();
        readyForWebSocketRequest(userName).setHandler(webSocketResult -> {
            WebSocket webSocket = webSocketResult.result();
            webSocket.frameHandler(webSocketFrame -> {
                gatherFrameData(context, async, webSocketFrame, () -> {
                    List<String> expected = new ArrayList<>();
                    expected.add("Connection established!");
                    expected.add(new JsonObject().put("message", "ok").put("command", "joinChat").encode());
                    context.assertEquals(expected, context.get("frames"));

                    RequestOptions getUsersOptions = new RequestOptions(requestOptions).setURI("/api/users");
                    makeGetRequest(getUsersOptions).setHandler(userResults -> {
                        HttpClientResponse response = userResults.result();
                        context.assertEquals(HttpResponseStatus.OK.code(), response.statusCode());
                        response.bodyHandler(buffer -> {
                            JsonObject expectedUsers = new JsonObject().put(
                                    "users", new JsonArray()
                                            .add(new JsonObject().put("id", userName).put("name", userName))
                                            .add(new JsonObject().put("id", "testbot").put("name", "testbot"))
                            );
                            assertBufferMatchesJsonObject(context, buffer, expectedUsers);
                            async.complete();
                        });
                    });
                });
            });

            JsonObject jsRequest = new JsonObject();
            jsRequest.put("command", "joinChat");
            jsRequest.put("payload", new JsonObject());

            webSocket.writeFinalTextFrame(jsRequest.encode());
        });
    }

    @Test
    public void webSocketGracefullyHandlesMultipleJoinChatRequests(TestContext context) {
        Async async = context.async(4);
        String userName = faker.name().firstName();
        readyForWebSocketRequest(userName).setHandler(webSocketResult -> {
            WebSocket webSocket = webSocketResult.result();
            webSocket.frameHandler(webSocketFrame -> {
                gatherFrameData(context, async, webSocketFrame, () -> {
                    List<String> expected = new ArrayList<>();
                    expected.add("Connection established!");
                    expected.add(new JsonObject().put("message", "ok").put("command", "joinChat").encode());
                    expected.add(new JsonObject().put("message", "already_joined").put("command", "joinChat").encode());
                    context.assertEquals(expected, context.get("frames"));

                    RequestOptions getUsersOptions = new RequestOptions(requestOptions).setURI("/api/users");
                    makeGetRequest(getUsersOptions).setHandler(userResults -> {
                        HttpClientResponse response = userResults.result();
                        context.assertEquals(HttpResponseStatus.OK.code(), response.statusCode());
                        response.bodyHandler(buffer -> {
                            JsonObject expectedUsers = new JsonObject().put(
                                    "users", new JsonArray()
                                            .add(new JsonObject().put("id", userName).put("name", userName))
                                            .add(new JsonObject().put("id", "testbot").put("name", "testbot"))
                            );

                            assertBufferMatchesJsonObject(context, buffer, expectedUsers);
                            async.complete();
                        });
                    });
                });
            });

            JsonObject jsRequest = new JsonObject();
            jsRequest.put("command", "joinChat");
            jsRequest.put("payload", new JsonObject());

            webSocket.writeFinalTextFrame(jsRequest.encode());
            webSocket.writeFinalTextFrame(jsRequest.encode());
        });
    }

    @Test
    public void sendingAndReceivingChatMessages(TestContext context) {
        Async async = context.async(3);
        String userName = faker.name().firstName();
        readyForWebSocketRequest(userName).setHandler(webSocketResult -> {
            WebSocket webSocket = webSocketResult.result();
            webSocket.frameHandler(webSocketFrame -> {
                gatherFrameData(context, async, webSocketFrame, () -> {
                    List<String> expected = new ArrayList<>();
                    expected.add("Connection established!");

                    JsonObject jsonChatMessage = new JsonObject();
                    jsonChatMessage.put("chatMessage",
                        new JsonObject().put("body", "this is the chat message body")
                            .put("timestamp", "2015-03-02 09:00:00")
                            .put("user", new JsonObject().put("id", userName).put("name", userName))
                    );
                    expected.add(new JsonObject().put("message", jsonChatMessage).put("command", "receivedChatMessage").encode());
                    context.assertEquals(expected, context.get("frames"));
                    async.complete();
                });
            });

            JsonObject chatMessageRequest = new JsonObject();
            chatMessageRequest.put("command", "chatMessage");
            chatMessageRequest.put("payload", new JsonObject().put("userId", userName).put("body", "this is the chat message body"));
            webSocket.writeFinalTextFrame(chatMessageRequest.encode());
        });


    }

    @Test
    public void fetchingIndexHtml(TestContext context) {
        Async async = context.async();

        makeGetRequest(requestOptions).setHandler(ar -> {
            HttpClientResponse response = ar.result();
            context.assertEquals(200, response.statusCode());
            response.bodyHandler(buffer -> {
                String data = buffer.getString(0, buffer.length());
                context.assertTrue(data.contains("spraybot test chat"));
                async.complete();
            });
        });
    }

    @Test
    public void creatingUserIsValid(TestContext context) {
        Async async = context.async();
        requestOptions.setURI("/api/users");

        HttpClientRequest request = client.post(requestOptions);
        JsonObject requestData = new JsonObject().put("user", new JsonObject().put("name", "cspray"));

        makePostRequest(request, requestData).setHandler(ar -> {
            HttpClientResponse response = ar.result();
            context.assertEquals(HttpResponseStatus.CREATED.code(), response.statusCode());
            response.bodyHandler(buffer -> {
                String data = buffer.getString(0, buffer.length());
                JsonObject json = new JsonObject(data);
                JsonObject expected = new JsonObject().put("user", new JsonObject().put("id", "cspray").put("name", "cspray"));
                context.assertEquals(expected, json);
                async.complete();
            });
        });
    }

    @Test
    public void createUserIsInvalid(TestContext context) {
        Async async = context.async();
        requestOptions.setURI("/api/users");

        HttpClientRequest request = client.post(requestOptions);
        JsonObject requestData = new JsonObject().put("user", new JsonObject().put("name", "testbot"));

        makePostRequest(request, requestData).setHandler(ar -> {
            HttpClientResponse response = ar.result();
            context.assertEquals(HttpResponseStatus.UNPROCESSABLE_ENTITY.code(), response.statusCode());
            response.bodyHandler(buffer -> {
                String data = buffer.getString(0, buffer.length());
                JsonObject json = new JsonObject(data);
                JsonObject expected = new JsonObject().put("errors", new JsonObject().put("name", "must not match the chat bot's name"));
                context.assertEquals(expected, json);
                async.complete();
            });
        });
    }

    @Test
    public void fetchingUsers(TestContext context) {
        Async async = context.async();
        requestOptions.setURI("/api/users");

        HttpClientRequest charlesRequest = client.post(requestOptions);
        HttpClientRequest tedRequest = client.post(requestOptions);
        HttpClientRequest nickRequest = client.post(requestOptions);

        JsonObject charlesData = new JsonObject().put("user", new JsonObject().put("name", "charles"));
        JsonObject tedData = new JsonObject().put("user", new JsonObject().put("name", "ted"));
        JsonObject nickData = new JsonObject().put("user", new JsonObject().put("name", "nick"));

        Future<HttpClientResponse> charlesFuture = makePostRequest(charlesRequest, charlesData);
        Future<HttpClientResponse> tedFuture = makePostRequest(tedRequest, tedData);
        Future<HttpClientResponse> nickFuture = makePostRequest(nickRequest, nickData);

        CompositeFuture.join(charlesFuture, tedFuture, nickFuture).setHandler(_void -> {
            makeGetRequest(requestOptions).setHandler(ar -> {
                HttpClientResponse response = ar.result();
                context.assertEquals(HttpResponseStatus.OK.code(), response.statusCode());
                response.bodyHandler(buffer -> {
                    String data = buffer.getString(0, buffer.length());
                    JsonObject actual = new JsonObject(data);

                    JsonObject expected = new JsonObject().put(
                            "users", new JsonArray()
                                    .add(new JsonObject().put("id", "charles").put("name", "charles"))
                                    .add(new JsonObject().put("id", "ted").put("name", "ted"))
                                    .add(new JsonObject().put("id", "nick").put("name", "nick"))
                    );

                    context.assertEquals(expected, actual);
                    async.complete();
                });
            });
        });
    }

}
