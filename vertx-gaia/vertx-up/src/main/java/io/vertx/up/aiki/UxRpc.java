package io.vertx.up.aiki;

import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.up.log.Annal;
import io.vertx.up.plugin.rpc.RpcClient;
import io.vertx.up.plugin.rpc.RpcInfix;

class UxRpc {

    private static final RpcClient CLIENT = RpcInfix.getClient();

    private static final Annal LOGGER = Annal.get(UxRpc.class);

    public static Future<JsonObject> thenRpc(final String name,
                                             final String address,
                                             final JsonObject params) {
        final Future<JsonObject> result = Future.future();
        CLIENT.connect(name, address, params, handler -> {
            if (handler.succeeded()) {
                result.complete(handler.result());
                LOGGER.info(Info.RPC_RESULT, name, address, params, handler.result(), String.valueOf(CLIENT.hashCode()));
            } else {
                LOGGER.jvm(handler.cause());
            }
        });
        return result;
    }

    public static Future<JsonObject> fnRpc(
            final JsonArray data) {
        return Future.succeededFuture(
                new JsonObject()
                        .put("data", data)
                        .put("count", data.size())
        );
    }

    public static Future<JsonArray> fnRpc(
            final JsonObject item
    ) {
        return Future.succeededFuture(
                null == item ?
                        new JsonArray() :
                        item.getJsonArray("data")
        );
    }
}
