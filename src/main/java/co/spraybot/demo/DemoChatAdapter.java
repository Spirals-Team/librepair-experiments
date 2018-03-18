package co.spraybot.demo;

import co.spraybot.*;
import co.spraybot.messagecodecs.ChatMessageCodec;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.http.WebSocket;
import io.vertx.core.http.WebSocketFrame;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A ChatAdapter implementation that also acts as a functioning REST and WebSocket server responsible for powering the
 * developer chat client used for testing purposes.
 *
 * Please see the ChatAdapter interface for Verticle messages that this implementation is meant to publish or subscribe
 * to.
 *
 * ### Available routes
 *
 * ```
 * POST /api/users HTTP/1.1
 * Accept: application/json
 * Content-Type: application/json
 *
 * {
 *     "user": {
 *         "name": "XXXXXX"
 *     }
 * }
 *
 * < 201 Created
 * {
 *     "user": {
 *         "id": "XXXXX",
 *         "name": "XXXXX"
 *     }
 * }
 *
 * < 422 Unprocessable Entity
 * {
 *     "errors": {
 *         "name": "Error reason about this"
 *     }
 * }
 * ```
 *
 * ```
 * GET /api/users HTTP/1.1
 * Accept: application/json
 *
 * < 200 OK
 * {
 *     "users": [
 *          {
 *              "id": "XXXXX",
 *              "name": "XXXXX"
 *          }
 *     ]
 * }
 * ```
 *
 *
 * @author Charles Sprayberry
 * @since 0.1.0
 */
public class DemoChatAdapter extends AbstractVerticle implements ChatAdapter {

    private HttpServer server;
    private EventBus eventBus;
    private Clock clock;
    private String chatBotName;

    private Map<String, User> users = new LinkedHashMap<>();
    private Map<String, ServerWebSocket> usersWebSockets = new HashMap<>();

    public DemoChatAdapter(HttpServer server, EventBus eventBus, Clock clock, String chatBotName) {
        this.server = server;
        this.eventBus = eventBus;
        this.clock = clock;
        this.chatBotName = chatBotName;
    }

    @Override
    public Future<Boolean> sendChatBotResponse(ChatBotResponse chatBotResponse) {
        return null;
    }

    @Override
    public void start(Future<Void> future) throws Exception {
        super.start();
        Router router = Router.router(getVertx());

        router.route().handler(BodyHandler.create());

        router.get("/api/users").handler(this::fetchAllUsers).handler(this::sendResponse);
        router.post("/api/users").handler(this::validateUserPayload).handler(this::createUser).handler(this::sendResponse);

        router.route().handler(staticHandler());

        eventBus.consumer("spraybot.chatadapter", message -> {
            ChatBotResponse response = (ChatBotResponse) message.body();
            ServerWebSocket webSocket = usersWebSockets.get(response.getChatMessageRespondingTo().getUser().getId());
            JsonObject jsonChatMessage = new JsonObject();
            String formattedTimestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss").format(response.getTimestamp());
            jsonChatMessage.put("message", new JsonObject()
                .put("chatMessage", new JsonObject()
                    .put("body", response.getBody())
                    .put("timestamp", formattedTimestamp)
                    .put("user", new JsonObject()
                        .put("id", chatBotName)
                        .put("name", chatBotName)
                    )
                )
            ).put("command", "receivedChatMessage");
            webSocket.writeFinalTextFrame(jsonChatMessage.encode());
            message.reply(true);
        });

        server.requestHandler(router::accept).websocketHandler(this::webSocketHandler).listen(httpServerResult -> {
            if (httpServerResult.succeeded()) {
                future.complete();
            } else {
                future.fail(httpServerResult.cause());
            }
        });
    }

    private void validateUserPayload(RoutingContext routingContext) {
        JsonObject requestData = routingContext.getBodyAsJson();
        JsonObject user = requestData.getJsonObject("user");
        String userName = user.getString("name");
        if (userName.equals(chatBotName)) {
            JsonObject errorData = new JsonObject().put("errors", new JsonObject().put("name", "must not match the chat bot's name"));
            routingContext.put("responseStatus", HttpResponseStatus.UNPROCESSABLE_ENTITY.code());
            routingContext.put("responsePayload", errorData);
        }

        routingContext.next();
    }

    private void createUser(RoutingContext routingContext) {
        if (routingContext.get("responseStatus") == null) {
            JsonObject requestData = routingContext.getBodyAsJson();
            JsonObject user = requestData.getJsonObject("user");
            String userName = user.getString("name");
            user.put("id", userName);

            User chatUser = new DefaultUser(userName, userName);
            users.put(userName, chatUser);

            routingContext.put("responseStatus", HttpResponseStatus.CREATED.code());
            routingContext.put("responsePayload", requestData);
        }

        routingContext.next();
    }

    // REST API handlers

    private void fetchAllUsers(RoutingContext routingContext) {
        JsonArray responseUsers = new JsonArray();
        JsonObject responsePayload = new JsonObject().put("users", responseUsers);

        for (User user : users.values()) {
            responseUsers.add(new JsonObject().put("id", user.getId()).put("name", user.getName()));
        }

        routingContext.put("responseStatus", HttpResponseStatus.OK.code());
        routingContext.put("responsePayload", responsePayload);

        routingContext.next();
    }

    private void sendResponse(RoutingContext routingContext) {
        int responseStatus = routingContext.get("responseStatus");
        JsonObject responsePayload = routingContext.get("responsePayload");
        String responseData = responsePayload.encode();
        int length = responseData.length();

        routingContext.response().setStatusCode(responseStatus)
                .putHeader("content-type", "application/json")
                .putHeader("content-length", Integer.toString(length))
                .end(responseData);
    }

    private StaticHandler staticHandler() {
        StaticHandler staticHandler = StaticHandler.create();
        staticHandler.setWebRoot("src/main/java/co/spraybot/demo/webroot");
        staticHandler.setIncludeHidden(false);
        staticHandler.setAllowRootFileSystemAccess(false);
        staticHandler.setCachingEnabled(false);

        return staticHandler;
    }

    // WebSocket handlers

    private void webSocketHandler(ServerWebSocket webSocket) {
        User wsUser = getWebSocketUser(webSocket);
        if (wsUser == null) {
            webSocket.reject();
            return;
        }

        usersWebSockets.put(wsUser.getId(), webSocket);

        webSocket.frameHandler(webSocketFrame -> {
            if (webSocketFrame.isFinal() && webSocketFrame.isText()) {
                JsonObject responseData = handleWebSocketFrame(wsUser, webSocketFrame);
                webSocket.writeFinalTextFrame(responseData.encode());
            }
        });

        webSocket.writeFinalTextFrame("Connection established!");
    }

    private User getWebSocketUser(ServerWebSocket webSocket) {
        String query = webSocket.query();
        if (!webSocket.path().equals("/chat") || query == null) {
            return null;
        }

        Map<String, List<String>> parameters = new QueryStringDecoder(query, false).parameters();
        String id = parameters.get("id").get(0);
        return users.get(id);
    }

    private JsonObject handleWebSocketFrame(User user, WebSocketFrame webSocketFrame) {
        JsonObject requestData = new JsonObject(webSocketFrame.textData());
        JsonObject responseData = new JsonObject();
        String commandName = requestData.getString("command");

        if ("joinChat".equals(commandName)) {
            handleJoinChatFrame(responseData);
        }

        if ("chatMessage".equals(commandName)) {
            handleChatMessageFrame(user, requestData, responseData);
        }

        return responseData;
    }

    private void handleJoinChatFrame(JsonObject responseData) {
        if (users.containsKey(chatBotName)) {
            responseData.put("message", "already_joined");
            responseData.put("command", "joinChat");
        } else {
            users.put(chatBotName, new DefaultUser(chatBotName, chatBotName));
            responseData.put("message", "ok");
            responseData.put("command", "joinChat");
        }
    }

    private void handleChatMessageFrame(User user, JsonObject requestData, JsonObject responseData) {
        JsonObject chatMessagePayload = requestData.getJsonObject("payload");
        ChatMessage chatMessage = ChatMessage.builder()
            .poweredBy(getClass().getSimpleName())
            .sentFromRoom("#general", user)
            .sentAt(LocalDateTime.ofInstant(clock.instant(), ZoneId.systemDefault()))
            .body(chatMessagePayload.getString("body"))
            .build();

        DeliveryOptions options = new DeliveryOptions().addHeader("op", "chatMessageReceived");
        options.setCodecName(ChatMessageCodec.class.getSimpleName());
        eventBus.publish("spraybot.chatbot", chatMessage, options);
        String formattedTimestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss").format(chatMessage.getTimestamp());
        responseData.put("message", new JsonObject()
                .put("chatMessage", new JsonObject()
                        .put("body", chatMessage.getBody())
                        .put("timestamp", formattedTimestamp)
                        .put("user", new JsonObject()
                                .put("id", user.getId())
                                .put("name", user.getName())
                        )
                )
        );
        responseData.put("command", "receivedChatMessage");
    }

    @Override
    public void stop(Future future) throws Exception {
        server.close(ar -> {
            future.complete();
        });
        super.stop();
    }
}
